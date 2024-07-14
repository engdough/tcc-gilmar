package solunit.runner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.annotations.Print;
import solunit.internal.sorter.SafeMethodSorter;
import solunit.internal.utilities.AccountsInjector;
import solunit.internal.utilities.ContractInjector;
import solunit.internal.utilities.Web3jInjector;
import solunit.mapper.MapperTimeTest;
import solunit.model.TimeTest;
import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class SolUnitRunner extends BlockJUnit4ClassRunner {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//control variable from @Before, that needs to run once for @Safe methods
	boolean firstBeforeExecution;
	
	//control variable for first non @Safe, that can use the same deploy instance from safe executions
	boolean firstNonSafeExecuted;
	
	//controls a contract deploy or load
	ContractInjector contractInjector;
	
	//controls accounts that can be injected
	AccountsInjector accountsInjector;
	
	Web3jInjector web3jInjector;
	
	//parser that knows if a method is Safe
	SafeParser safeParser;

	MapperTimeTest mapperTimeTest;
	
	public SolUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		
		this.firstBeforeExecution = true;
		this.firstNonSafeExecuted = false;

		this.mapperTimeTest = MapperTimeTest.getMapper(klass.getName());
		
		try {
			this.contractInjector = new ContractInjector();
			this.accountsInjector = new AccountsInjector();
			this.web3jInjector = new Web3jInjector();
			
			//cria o parser
			this.safeParser = SafeParserFactory.createParser();
			
		} catch (IOException e) {
			 throw new InitializationError(new IOException("Error initializing injectors", e));
		}
	}
	
	/**
     * Creates a test to be excecuted
     * @param method method annotated with @Test 
     * @return Object that will be execute the test method
     * @throws Exception for some error
     */
    public Object createTest(FrameworkMethod method) throws Exception {
    	//standard creation from JUnit
        Object obj = super.createTest();
        
        //Inject contracts and accounts
        this.injectDependencies(obj, method);
        
        return obj;
    }
    
    /**
     * Inject accounts and contracts in the Test Object <br>
     * Contracts are identified by the annotation @Contract <br>
     * Accounts are identified by the annotation @Account
     * 
     * @param testObject object that will be execute the test method
     * @param actualMethod object representing the actual test method
     * @throws IllegalArgumentException for some error
     * @throws IllegalAccessException for some error
     */
    private void injectDependencies( Object testObject, FrameworkMethod actualMethod ) throws IllegalArgumentException, IllegalAccessException {
    	Field [] fields = testObject.getClass().getDeclaredFields();
    	
    	for (Field f: fields) {
    		
    		if ( f.isAnnotationPresent(Account.class) ) {
    			this.accountsInjector.inject(f, testObject);
    		}
    		
    		if ( f.isAnnotationPresent(Contract.class) ) {
    			this.contractInjector.deployOrLoadContract(f, testObject, actualMethod, this.firstNonSafeExecuted);
    		}
    		
    		if ( f.isAnnotationPresent(Inject.class) ) {
    			this.web3jInjector.inject(f, testObject);
    		}
    		
    	}
    }
    
    //***************************************************************
    //
    //  Fixture rules
    //
    //***************************************************************
    /**
     * Verify if can reuse a @Before fixture or not <br>
     * If the method is @Safe AND is NOT a first execution, then can reuse <br>
     * Otherwise, do a new @Before execution
     * @param actualMethod object representing the actual test method 
     * @return true if needs to run @Before, false if can skip 
     */
    private boolean needsToRunBeforeFixture(FrameworkMethod actualMethod) {
    	//regra 1: roda se for safe, mas nao rodou before nenhuma vez
    	if (isSafeAndNotFirstBeforeExecution(actualMethod)) {
    		return true;
    	}
    	
    	//regra 2: nao é safe E nao é primeira execução de nao-safe
    	if (fistNonSafeWasExecuted(actualMethod)) {
    		return true;
    	}
    	
    	//regra 3: se o before nunca foi executado (classe sem nenhum safe), executa
    	if ( this.firstBeforeExecution ) {
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isSafeAndNotFirstBeforeExecution(FrameworkMethod actualMethod) {
    	return (this.safeParser.isSafe(actualMethod) && this.firstBeforeExecution );
    }
    
    private boolean fistNonSafeWasExecuted(FrameworkMethod actualMethod) {
    	return (!this.safeParser.isSafe(actualMethod) && this.firstNonSafeExecuted );
    }
    
    //***************************************************************
    //
    //  JUnit overrides
    //
    //***************************************************************
    
    /**
     * Returns the methods that run tests. Default implementation returns all
     * methods annotated with {@code @Test} 
     */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {

    	List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);

    	//creates a new List (JUnit default is 'unmodifiable list')
    	List<FrameworkMethod> newList = new ArrayList<>();
    	methods.forEach(newList::add);

    	//Order methods to run "@Safe" first
    	Collections.sort(newList, new SafeMethodSorter());

        return newList;
    }
    
	@Override
    protected Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest( method );
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);

        //verify the need to run @Before fixture
        if ( this.needsToRunBeforeFixture(method) ) {
        	statement = withBefores(method, test, statement);
        	//if run once, check it
        	this.firstBeforeExecution = false;
        }



        //mark first non safe execution when happens
        if ( !this.safeParser.isSafe(method) ) {
        	this.firstNonSafeExecuted = true;
        }

		try {
			System.out.println(method.getAnnotation(Print.class).print());
		} catch (Throwable e) {
			System.out.println("sem notação print");
		}

        return statement;
    }

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);

		TimeTest timeTest = new TimeTest();

        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else {
			Statement statement = methodBlock(method);

			long timeStart = System.currentTimeMillis();
			runLeaf(statement, description, notifier);
			long timeFinish = System.currentTimeMillis();

			System.out.println();

			timeTest.setName(method.getName());
			timeTest.setDuration(timeFinish-timeStart);
			System.out.println(timeTest.getName()+"  "+timeTest.getDuration());
			this.mapperTimeTest.put(timeTest);
        }
    }
}

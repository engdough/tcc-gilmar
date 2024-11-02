package solunit.runner;


import java.lang.reflect.Method;
import java.util.Collections;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import solunit.internal.sorter.SafeMethodSorter;

import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class SolUnitRunner implements InvocationInterceptor, MethodOrderer {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//control variable from @Before, that needs to run once for @Safe methods
	boolean firstBeforeExecution;
	
	//control variable for first non @Safe, that can use the same deploy instance from safe executions
	boolean firstNonSafeExecuted;

	//parser that knows if a method is Safe
	SafeParser safeParser;
	
	public SolUnitRunner() throws Exception {

		this.firstBeforeExecution = true;
		this.firstNonSafeExecuted = false;
		this.safeParser = SafeParserFactory.createParser();
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
    private boolean needsToRunBeforeFixture(Method actualMethod) {
    	//regra 1: roda se for safe, mas nao rodou before nenhuma vez
    	if (isSafeAndNotFirstBeforeExecution(actualMethod)) {
			this.firstBeforeExecution = false;
    		return true;
    	}
    	
    	//regra 2: nao é safe E nao é primeira execução de nao-safe
    	if (fistNonSafeWasExecuted(actualMethod)) {
    		return true;
    	}
    	
    	//regra 3: Classe sem nenhum safe
    	if ( this.firstBeforeExecution ) {
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isSafeAndNotFirstBeforeExecution(Method actualMethod) {
    	return (this.safeParser.isSafe(actualMethod) && this.firstBeforeExecution );
    }
    
    private boolean fistNonSafeWasExecuted(Method actualMethod) {
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
	public void orderMethods(MethodOrdererContext context) {
		Collections.sort(context.getMethodDescriptors(), new SafeMethodSorter());
	}

	@Override
	public void interceptBeforeEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> reflectiveInvocationContext, ExtensionContext extensionContext) throws Throwable {
		Method method = extensionContext.getRequiredTestMethod();

		if (this.needsToRunBeforeFixture(method)) {
			invocation.proceed();
			return;
		}

		if (!safeParser.isSafe(method)) {
			this.firstNonSafeExecuted = true;
		}

		invocation.skip();
	}
}
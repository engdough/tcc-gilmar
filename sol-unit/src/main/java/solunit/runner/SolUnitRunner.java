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

	protected final Logger log = LoggerFactory.getLogger(getClass());

	boolean firstBeforeExecution;

	boolean firstNonSafeExecuted;

	SafeParser safeParser;
	
	public SolUnitRunner() throws Exception {
		this.firstBeforeExecution = true;
		this.firstNonSafeExecuted = false;
		this.safeParser = SafeParserFactory.createParser();
	}

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

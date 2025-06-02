package sunit.runner;

import java.lang.reflect.Method;
import java.util.Collections;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import sunit.internal.sorter.SafeMethodSorter;
import sunit.parser.SafeParser;

public class SunitRunner implements InvocationInterceptor, MethodOrderer {

	boolean firstExecution;
	boolean firstNonSafeExecuted;
	SafeParser safeParser;
	
	public SunitRunner() throws Exception {
		this.firstExecution = true;
		this.firstNonSafeExecuted = false;
		this.safeParser = new SafeParser();
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

    private boolean needsToRunBeforeFixture(Method actualMethod) {
    	//regra 1: roda se for safe, mas nao rodou before nenhuma vez
    	if (isSafeAndNotFirstBeforeExecution(actualMethod)) {
			this.firstExecution = false;
    		return true;
    	}
    	
    	//regra 2: nao é safe E nao é primeira execução de nao-safe
    	if (fistNonSafeWasExecuted(actualMethod)) {
    		return true;
    	}
    	
    	//regra 3: Classe sem nenhum safe
    	if ( this.firstExecution ) {
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isSafeAndNotFirstBeforeExecution(Method actualMethod) {
    	return (this.safeParser.isSafe(actualMethod) && this.firstExecution );
    }
    
    private boolean fistNonSafeWasExecuted(Method actualMethod) {
    	return (!this.safeParser.isSafe(actualMethod) && this.firstNonSafeExecuted );
    }
}

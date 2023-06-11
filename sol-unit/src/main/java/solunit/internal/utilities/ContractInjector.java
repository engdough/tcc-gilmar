package solunit.internal.utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import solunit.constants.Config;
import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class ContractInjector {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//web3j and web3admin instances
	Web3j web3j;
	Admin web3Admin;
	
	Credentials mainAccountCredentials;
	
	//deployed contracts (for a possible reuse)
	Map<Class<?>, String> contractsAddress;
	
	//control variable for first non @Safe, that can use the same deploy instance from safe executions
	boolean firstNonSafeExecuted;
	
	private SafeParser safeParser;
	
	public ContractInjector() throws IOException {
		this.contractsAddress = new HashMap<>();
		this.createWeb3InstanceFromProperties();
		
		//cria o parser
		this.safeParser = SafeParserFactory.createParser();
	}
	
	//***************************************************************
    //
    //  contracts
    //
    //***************************************************************
    /**
     * Inject a contract into a field, deploying a new ou loading an existing
     * @param f field on the test object that will be injected
     * @param testObject object that will be execute the test method
     * @param actualMethod object representing the actual test method
     */
    public void deployOrLoadContract( Field f, Object testObject, FrameworkMethod actualMethod, boolean firstNonSafeExecuted) {
    	this.firstNonSafeExecuted = firstNonSafeExecuted;
    	f.setAccessible(true);
		try {
			Class<?> contractClass = f.getType();
			String address = this.contractsAddress.get(contractClass);
			
			
			if ( this.needsToDeployNewContract(actualMethod, address) ) {
				this.deployNewContract(contractClass, testObject, f);
			}
			else {
				this.loadExistingContract(address, contractClass, testObject, f);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Error injecting Contract", e);
		}
    }
    
    /**
     * Verify if can reuse a contract or not <br>
     * If the method is @Safe AND a contract was already deployed, then load. <br>
     * Otherwise, do a new deploy
     * @param actualMethod object representing the actual test method 
     * @param address address for an existing contract (if there is one)
     * @return true if needs a new deploy, false if can reuse a existing contract
     */
    private boolean needsToDeployNewContract(FrameworkMethod actualMethod, String address ) {
    	if ( isSafeAndNoDeployHasBeenMade(actualMethod, address) ) {
    		return true;
    	}
    	if ( fistNonSafeWasExecuted(actualMethod) ) {
    		return true;
    	}
    	if ( address == null ) {
    		return true;
    	}
    	
    	return false;
    }
    
    private boolean isSafeAndNoDeployHasBeenMade(FrameworkMethod actualMethod, String address) {
    	return (this.safeParser.isSafe(actualMethod) && address == null );
    }
    
    private boolean fistNonSafeWasExecuted(FrameworkMethod actualMethod) {
    	return (!this.safeParser.isSafe(actualMethod) && this.firstNonSafeExecuted );
    }
    
    private void deployNewContract(Class contractClass, Object testObject, Field f) throws Exception {
    	//call the static method 'deploy' that prepares a new contract deploy
		Method m = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
		Object remoteCall = m.invoke(null, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
		
		//calls the 'send' method to create a contract instance
		if( remoteCall instanceof RemoteCall ) {
			Object instance = ((RemoteCall)remoteCall).send();
			f.set(testObject, instance);
			
			//keep the address for a possible future reuse, calling the method 'getContractAddress'
			Method mAddr = contractClass.getMethod("getContractAddress");
			String address = (String) mAddr.invoke(instance);
			this.contractsAddress.put(contractClass, address);
			
			log.info( String.format("Contract deployment: [%s]", contractClass.getSimpleName() ) );
		}
    }
    
    private void loadExistingContract(String address, Class contractClass, Object testObject, Field f) throws Exception {
    	//calls the static 'load' method that load a contract by it's address
		Method m = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
		Object instance = m.invoke(null, address, this.web3j, this.mainAccountCredentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
		f.set(testObject, instance);
		
		log.info( String.format("Contract loaded: [%s]", contractClass.getSimpleName() ) );
    }
    
    
    /**
     * Method that create instances for Web3j, Web3admin and main account credentials, <br>
     * based on properties file
     * @throws IOException 
     */
    private void createWeb3InstanceFromProperties() throws IOException {
    	Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
    	this.web3Admin = Admin.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST))); 
    	this.web3j = Web3j.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST)));
    	this.mainAccountCredentials = Credentials.create((testProperties.getProperty(Config.MAIN_ACCOUNT_PRIVATE_KEY)));
    }

}

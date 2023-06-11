package solunit.internal.utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;

import solunit.annotations.Account;
import solunit.constants.Config;

public class AccountsInjector {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	//web3j and web3admin instances
	Web3j web3j;
	Admin web3Admin;
	
	//accounts from properties file
	Map<String,Credentials> accounts;
	
	public AccountsInjector() throws IOException {
		this.accounts = new HashMap<>();
		this.readAccountsFromProperties();
	}
	
    public void inject(Field f, Object testObject ) throws IllegalArgumentException, IllegalAccessException {
    	f.setAccessible(true);
		
		Account account = f.getAnnotation(Account.class);
		String id = account.id();
		
		if( f.getType().equals(Credentials.class) ) { 
			f.set(testObject, this.accounts.get(id));
		}
    }
    
    /**
     * Reads all accounts listed on solidity-unit.properties file
     * @throws IOException 
     */
    private void readAccountsFromProperties() throws IOException {
    	Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
		for (Object o: testProperties.keySet()) {
			if ( o.toString().startsWith("account.") && o.toString().endsWith(".id") ) {
				String [] vet = o.toString().split("\\.");
				
				String account = vet[1];
				//String id = this.testProperties.getProperty( String.format("account.%s.id", account) );
				String pk = testProperties.getProperty( String.format("account.%s.privatekey", account) );
				accounts.put(account, Credentials.create(pk));
			}
		}
    }

}

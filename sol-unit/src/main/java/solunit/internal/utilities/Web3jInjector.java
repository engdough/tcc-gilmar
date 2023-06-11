package solunit.internal.utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import javax.inject.Inject;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import solunit.constants.Config;

public class Web3jInjector {
	
	//web3j and web3admin instances
	Web3j web3j;
	Admin web3Admin;
	Credentials mainAccountCredentials;
	
	public Web3jInjector() throws IOException {
		this.createWeb3InstanceFromProperties();
	}
	
	public void inject(Field f, Object testObject ) throws IllegalArgumentException, IllegalAccessException {
    	f.setAccessible(true);
		
		if( f.getAnnotation(Inject.class) != null && f.getType().equals(Web3j.class) ) { 
			f.set(testObject, this.web3j);
		}
    }
	
	private void createWeb3InstanceFromProperties() throws IOException {
    	Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
    	this.web3Admin = Admin.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST))); 
    	this.web3j = Web3j.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST)));
    	this.mainAccountCredentials = Credentials.create((testProperties.getProperty(Config.MAIN_ACCOUNT_PRIVATE_KEY)));
    }

}

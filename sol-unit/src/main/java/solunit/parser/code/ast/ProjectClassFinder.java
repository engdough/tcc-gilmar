package solunit.parser.code.ast;

import java.util.List;
import java.util.Properties;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;

public class ProjectClassFinder extends VoidVisitorAdapter<Object> {
	
	List<ClassOrInterfaceDeclaration> list;
	
	//debug purposes
	private String classOnProperties;
	
	public ProjectClassFinder( List<ClassOrInterfaceDeclaration> list ) {
		this.list = list;
		
		//le o properties
		try {
			Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
			classOnProperties = testProperties.getProperty("test.contract.class");
		}
		catch(Exception silent) {}
	}
	
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        super.visit(n, arg);

		if ( classOnProperties == null || classOnProperties.equals(n.getNameAsString()) ) {
			list.add(n);
		}
    }

}

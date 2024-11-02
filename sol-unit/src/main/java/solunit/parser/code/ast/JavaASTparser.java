package solunit.parser.code.ast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.utils.SourceRoot;

public class JavaASTparser {
	
	/** Logger instance */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	String sourceRootDir;
	String mainSourcDir;
	SourceRoot sourceRoot;

	List<ClassOrInterfaceDeclaration> projectClasses;
	List<ClassOrInterfaceDeclaration> testClasses;
	List<MethodDeclaration> projectNotSafeMethods;
	List<MethodDeclaration> testSafeMethods;

	public JavaASTparser( String sourceDir, String mainSourceDir ) {
		this.sourceRootDir = sourceDir;
		this.mainSourcDir = mainSourceDir;
		this.testClasses = new ArrayList<>();
		this.projectClasses = new ArrayList<>();
		this.projectNotSafeMethods = new ArrayList<>();
		this.testSafeMethods = new ArrayList<>();
		
		this.init();
	}
	
	public boolean isSafe(Method method) {
		
		//find method
		for (MethodDeclaration m: this.testSafeMethods) {
			if ( m.getName().asString().equals(method.getName()) ) {
				//find class
				if ( this.getClassFromTestMethodAsString(m).equals(method.getDeclaringClass().getSimpleName()) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	private void init() {

		log.info( "Initializing" );
		
		//find all contracts
		this.findProjectClasses();
		
		//finds all safe methods on contracts
		this.findProjectNotSafeMethods();
		
		this.printContractResults();
		
		//find test classes
		this.findTestClasses();
		
		//find all safe methods on tests
		this.findTestClassesSafeMethods();
		
		this.printTestClassResults();
	}
	
	private void findProjectClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
            	new ProjectClassFinder(this.projectClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.mainSourcDir) );
	}
	
	private void findProjectNotSafeMethods() {
		this.projectClasses.stream().forEach( n -> {
			new ProjectNotSafeMethodFinder( this.projectNotSafeMethods ).visit(n, null);
		});
	}
	
	private void findTestClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                new TestRunnerClassFinder(this.testClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.sourceRootDir) );
	}
	
	private void findTestClassesSafeMethods() {
		TestRunnerSafeMethodFinder finder = new TestRunnerSafeMethodFinder( this.testSafeMethods );
		finder.setContractClasses(this.projectClasses);
		finder.setContractSafeMethods(this.projectNotSafeMethods);
		
		this.testClasses.stream().forEach( n -> {
			finder.visit(n, null);
		});
	}
	
	private void printContractResults() {
		this.projectNotSafeMethods.stream().forEach( m -> log.info( String.format("Method not safe found: [%s].[%s]",
																			this.getClassFromContractMethodAsString(m),
																			m.getName()) ) );
	}
	
	private void printTestClassResults() {
		this.testSafeMethods.stream().forEach( m -> log.info( String.format("Safe test found: [%s].[%s]", 
																			this.getClassFromTestMethodAsString(m),
																			m.getName()) ) );
	}
	
	private String getClassFromContractMethodAsString( MethodDeclaration m ) {
		for ( ClassOrInterfaceDeclaration n: this.projectClasses ) {
			if ( n.getMethods().contains(m) ) {
				return n.getNameAsString();
			}
		}
		return null;
	}
	
	private String getClassFromTestMethodAsString( MethodDeclaration m ) {
		for ( ClassOrInterfaceDeclaration n: this.testClasses ) {
			if ( n.getMethods().contains(m) ) {
				return n.getNameAsString();
			}
		}
		return null;
	}
}


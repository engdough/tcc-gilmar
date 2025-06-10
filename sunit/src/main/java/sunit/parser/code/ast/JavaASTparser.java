package sunit.parser.code.ast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class JavaASTparser {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	String testSourceRootDir;
	String mainSourceDir;

	List<ClassOrInterfaceDeclaration> projectClasses;
	List<ClassOrInterfaceDeclaration> testClasses;
	List<MethodDeclaration> projectNotSafeMethods;
	List<MethodDeclaration> projectSafeMethods;
	List<MethodDeclaration> testSafeMethods;

	public JavaASTparser( String testSourceDir, String mainSourceDir ) {
		this.testSourceRootDir = testSourceDir;
		this.mainSourceDir = mainSourceDir;
		this.testClasses = new ArrayList<>();
		this.projectClasses = new ArrayList<>();
		this.projectNotSafeMethods = new ArrayList<>();
		this.projectSafeMethods = new ArrayList<>();
		this.testSafeMethods = new ArrayList<>();
		
		this.init();
	}
	
	public boolean isSafe(Method method) {
		for (MethodDeclaration m: this.testSafeMethods) {
			if ( m.getName().asString().equals(method.getName()) ) {
				if ( this.getClassFromTestMethodAsString(m).equals(method.getDeclaringClass().getSimpleName()) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	private void init() {
		log.info( "Initializing" );

		this.findProjectClasses();
		this.findProjectNotSafeMethods();
		this.printNotSafeResults();
		this.findTestClasses();
		this.findTestClassesSafeMethods();
		this.printTestSafeResults();
	}
	
	private void findProjectClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
            	new ProjectClassFinder(this.projectClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.mainSourceDir) );
	}
	
	private void findProjectNotSafeMethods() {
		this.projectClasses.stream().forEach( n -> {
			new ProjectNotSafeMethodFinder( this.projectNotSafeMethods, this.projectSafeMethods ).visit(n, null);
		});
	}
	
	private void findTestClasses() {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                new TestRunnerClassFinder(this.testClasses).visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
		}).explore( new File(this.testSourceRootDir) );
	}
	
	private void findTestClassesSafeMethods() {
		TestRunnerSafeMethodFinder finder = new TestRunnerSafeMethodFinder( this.testSafeMethods );
		finder.setProjectNotSafeMethods(this.projectNotSafeMethods);
		
		this.testClasses.stream().forEach( n -> {
			finder.visit(n, null);
		});
	}
	
	private void printNotSafeResults() {
		log.info("Metodos não seguro encontrados: " + this.projectNotSafeMethods.size());
		this.projectNotSafeMethods.stream().forEach( m -> log.info( String.format("Method not safe found: [%s].[%s]",
																			this.getClassFromProjectMethodAsString(m),
																			m.getName()) ) );
	}
	
	private void printTestSafeResults() {
		log.info("Metodos de teste seguro encontrados: " + this.testSafeMethods.size());
		this.testSafeMethods.stream().forEach( m -> log.info( String.format("Safe test found: [%s].[%s]",
																			this.getClassFromTestMethodAsString(m),
																			m.getName()) ) );
	}
	
	private String getClassFromProjectMethodAsString( MethodDeclaration m ) {
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

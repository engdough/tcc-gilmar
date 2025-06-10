package sunit.parser.code.ast;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestRunnerSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> list;
	List<ClassOrInterfaceDeclaration> projectClasses;
	List<MethodDeclaration> projectNotSafeMethods;
	
	public TestRunnerSafeMethodFinder(List<MethodDeclaration> list) {
		this.list = list;
	}

	public void setProjectClasses(List<ClassOrInterfaceDeclaration> projectClasses) {
		this.projectClasses = projectClasses;
	}

	public void setProjectNotSafeMethods(List<MethodDeclaration> projectNotSafeMethods) {
		this.projectNotSafeMethods = projectNotSafeMethods;
	}

	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		
		if ( md.getAnnotationByClass(Test.class).isPresent() ) {
			if ( this.isSafe(md) ){
				this.list.add(md);
			}
		}
	}
	
	private boolean isSafe(MethodDeclaration md) {
		FieldAccessFinder a = new FieldAccessFinder(md);
		a.setProjectClasses(this.projectClasses);
		a.setProjectNotSafeMethods(this.projectNotSafeMethods);
		a.visit(md, null);
		return a.isSafe();
	}
}

class FieldAccessFinder extends VoidVisitorAdapter<Void> {

	List<ClassOrInterfaceDeclaration> projectClasses;
	List<MethodDeclaration> projectNotSafeMethods;
	MethodDeclaration md;
	boolean projectAccess;
	boolean safe;
	
	public FieldAccessFinder(MethodDeclaration md) {
		this.md = md;
		this.safe = true;
	}

	public void setProjectClasses(List<ClassOrInterfaceDeclaration> projectClasses) {
		this.projectClasses = projectClasses;
	}

	public void setProjectNotSafeMethods(List<MethodDeclaration> projectNotSafeMethods) {
		this.projectNotSafeMethods = projectNotSafeMethods;
	}
	
	@Override
    public void visit(FieldAccessExpr field, Void arg) {
        super.visit(field, arg);

        field.findCompilationUnit().get().findAll(FieldDeclaration.class)
        	.stream().forEach( a -> {
				//todo: verficar uso do static se é necessario
	    		if ( a.isStatic() ) {
	    			String fieldName = a.getVariable(0).getNameAsString();
					String testBody = md.getBody().toString();
					//verifica se altera o valor do SUT através do = ou se o SUT utiliza algum metodo não seguro
					this.projectNotSafeMethods.stream().forEach(b -> {
						if (testBody.contains(fieldName + " =") || testBody.contains(fieldName + "." + b.getNameAsString())) {
							this.safe = false;
						};
					});
	    		}
	    	});
    }
	
	public boolean isSafe() {
		return safe;
	}
}

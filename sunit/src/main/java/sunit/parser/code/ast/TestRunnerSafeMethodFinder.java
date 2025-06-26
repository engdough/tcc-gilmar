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
	List<MethodDeclaration> listNotSafe;
	
	public TestRunnerSafeMethodFinder(List<MethodDeclaration> list) {
		this.list = list;
	}

	public void setProjectNotSafeMethods(List<MethodDeclaration> projectNotSafeMethods) {
		this.listNotSafe = projectNotSafeMethods;
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
		TestMethodVerfy a = new TestMethodVerfy(md);
		a.setProjectNotSafeMethods(this.listNotSafe);
		a.visit(md, null);
		return a.isSafe();
	}
}

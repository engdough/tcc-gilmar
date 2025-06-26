package sunit.parser.code.ast;

import java.util.List;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestMethodVerfy extends VoidVisitorAdapter<Void> {

	List<MethodDeclaration> projectNotSafeMethods;
	MethodDeclaration method;
	boolean safe;

	public TestMethodVerfy(MethodDeclaration md) {
		this.method = md;
		this.safe = true;
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
						String testBody = method.getBody().toString();
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

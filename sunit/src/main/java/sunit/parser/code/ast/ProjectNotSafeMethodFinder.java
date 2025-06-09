package sunit.parser.code.ast;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ProjectNotSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> listNotSafe;
	List<MethodDeclaration> listSafe;
	boolean safe;
	
	public ProjectNotSafeMethodFinder(List<MethodDeclaration> listNotSafe, List<MethodDeclaration> listSafe) {
		this.listNotSafe = listNotSafe;
		this.listSafe = listSafe;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration classe, Void arg) {
		super.visit(classe, arg);
		this.safe = true;

		//passa por todos os métodos da classe
		classe.getMethods().stream().forEach(method -> {
			//passa por todos os atributos da classe
			classe.getFields().stream().forEach(field -> {
				//verifica se tem alteração do atributo
				if (method.toString().contains(field.getChildNodes().get(0).toString() + " =")) {
					this.safe = false;
				}
			});

			//verifica se o metodo nao utiliza um metodo ja classificado como nao seguro
			this.listNotSafe.stream().forEach(methodNotSafe -> {
				if (method.toString().contains("." + methodNotSafe.getNameAsString())) {
					this.safe = false;
				}
			});

			if (!safe) {
				//se o metodo e nao seguro, verifica se entre os metodos ja classificados como seguros utilizam ele
				this.listSafe.stream().forEach(methodSafe -> {
					if (methodSafe.toString().contains("." + method.getNameAsString())) {
						this.listNotSafe.add(methodSafe);
					}
				});
				this.listNotSafe.add(method);
			} else {
				this.listSafe.add(method);
			}

			this.safe = true;
		});
	}
}

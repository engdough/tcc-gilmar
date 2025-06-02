package sunit.parser.code.ast;

import java.util.List;

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
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		this.safe = true;

		//verifica se os atributos de cada classe possui alguma alteração de valor
		md.getParentNode().get().getChildNodes().stream().forEach(a -> {
			//se for igual a 1 significa que é o atributo do objeto
			if (a.getChildNodes().size() == 1) {
				String var = a.getChildNodes().get(0).toString();
				//verifica se o atributo astá tendo alteração de valor através do símbolo =
				if (md.toString().contains(var + " =")) {
					this.safe = false;
				}
			}
		});

		//verifica se possui algum dos métodos já classificado como não seguro anteriormente
		this.listNotSafe.stream().forEach(b -> {
			if (md.getBody().toString().contains("." + b.getNameAsString())) {
				this.safe = false;
			}
		});

		//caso o metodo seja nao seguro, verifica se algum metodo da lista de seguro utiliza ele e adiciona esse metodo classifica como seguro na lista de nao seguro
		//e add o metodo classificado como nao seguro na lista d enao seguro
		if(!safe) {
			this.listSafe.stream().forEach(c -> {
				if (c.getBody().toString().contains("." + md.getNameAsString())) {
					this.listNotSafe.add(c);
				}
			});
			this.listNotSafe.add(md);
		//add na lista de metodos seguros caso seja um metodo seguro
		} else {
			this.listSafe.add(md);
		}
	}
}

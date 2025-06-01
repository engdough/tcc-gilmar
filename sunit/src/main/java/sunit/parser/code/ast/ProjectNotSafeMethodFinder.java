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

		md.getParentNode().get().getChildNodes().stream().forEach(a -> {
			if (a.getChildNodes().size() == 1) {
				String var = a.getChildNodes().get(0).toString();
				if (md.toString().contains(var + " =")) {
					this.safe = false;
				}
			}
		});

		this.listNotSafe.stream().forEach(b -> {
			if (md.getBody().toString().contains("." + b.getNameAsString())) {
				this.safe = false;
			}
		});

		if(!safe) {
			this.listSafe.stream().forEach(c -> {
				if (c.getBody().toString().contains("." + md.getNameAsString())) {
					this.listNotSafe.add(c);
				}
			});
			this.listNotSafe.add(md);
		} else {
			this.listSafe.add(md);
		}
	}
}

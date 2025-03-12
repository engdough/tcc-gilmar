package solunit.parser.code.ast;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ProjectNotSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> listNotSafe;

	List<MethodDeclaration> listSafe = new ArrayList<>();

	boolean safe;
	
	public ProjectNotSafeMethodFinder(List<MethodDeclaration> list) {
		this.listNotSafe = list;
	}

	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		safe = true;

		md.getParentNode().get().getChildNodes().stream().forEach(a -> {
			if (a.getChildNodes().size() == 1) {
				String var = a.getChildNodes().get(0).toString();
				if (md.toString().contains(var + " =")) {
					safe = false;
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
	
	private boolean returnTransactionReceipt(MethodDeclaration md) {
		
		if (md.getType().getChildNodes().size() == 2) {
			Node n1 = md.getType().getChildNodes().get(0);
			Node n2 = md.getType().getChildNodes().get(1);
			
			//child 1 must be RemoteCall AND child 2 must be TransactionReceipt
			return n1.toString().equals("RemoteCall") && n2.toString().equals("TransactionReceipt");
		}
		
		return false;
	}
}

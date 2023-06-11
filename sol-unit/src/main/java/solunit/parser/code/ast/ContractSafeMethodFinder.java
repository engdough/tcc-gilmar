package solunit.parser.code.ast;

import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ContractSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> list;
	
	public ContractSafeMethodFinder(List<MethodDeclaration> list) {
		this.list = list;
	}

	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		
		//safe methods are NOT static AND must NOT return a RemoteCall<TransactionReceipt>
		//call methods are not safe TOO
		if ( !md.isStatic() && !this.returnTransactionReceipt(md) && !md.getNameAsString().equals("call") ) {
			this.list.add(md);
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


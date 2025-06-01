package sunit.parser.code.ast;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestRunnerContractAttributesFinder extends VoidVisitorAdapter<Object> {
	
	List<ClassOrInterfaceDeclaration> list;
	
	public TestRunnerContractAttributesFinder( List<ClassOrInterfaceDeclaration> list ) {
		this.list = list;
	}
	
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        super.visit(n, arg);
    }

}

package sunit.parser.code.ast;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestRunnerClassFinder extends VoidVisitorAdapter<Object> {
	
	List<ClassOrInterfaceDeclaration> list;
	
	public TestRunnerClassFinder( List<ClassOrInterfaceDeclaration> list ) {
		this.list = list;
	}
	
	@Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        super.visit(n, arg);

		//adicona as classes marcadas com a extensao SunitRunner
        Optional<AnnotationExpr> a = n.getAnnotationByClass(ExtendWith.class);
        if ( a.isPresent() && a.get().getChildNodes().get(1).toString().equals("SunitRunner.class") ) {
        	this.list.add(n);
        }
    }

}

package solunit.parser.code.ast;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestRunnerSafeMethodFinder extends VoidVisitorAdapter<Void> {
	
	List<MethodDeclaration> list;

	List<ClassOrInterfaceDeclaration> projectClasses;

	List<MethodDeclaration> projectSafeMethods;
	
	public TestRunnerSafeMethodFinder(List<MethodDeclaration> list) {
		this.list = list;
	}

	public void setProjectClasses(List<ClassOrInterfaceDeclaration> projectClasses) {
		this.projectClasses = projectClasses;
	}

	public void setProjectSafeMethods(List<MethodDeclaration> projectSafeMethods) {
		this.projectSafeMethods = projectSafeMethods;
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
		a.setProjectSafeMethods(this.projectSafeMethods);
		a.visit(md, null);
		return a.isSafe();
	}
}

class FieldAccessFinder extends VoidVisitorAdapter<Void> {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	List<ClassOrInterfaceDeclaration> projectClasses;

	List<MethodDeclaration> projectSafeMethods;
	
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
	public void setProjectSafeMethods(List<MethodDeclaration> projectSafeMethods) {
		this.projectSafeMethods = projectSafeMethods;
	}
	
	@Override
    public void visit(FieldAccessExpr field, Void arg) {
        super.visit(field, arg);
        
        String fieldAccessName = field.getNameAsString();
		this.safe = true;

        field.findCompilationUnit().get().findAll(FieldDeclaration.class)
        	.stream().forEach( a -> {
	    		if ( a.isStatic() ) {
	    			String fieldName = a.getVariable(0).getNameAsString();
					String testBody = md.getBody().toString();
					this.projectSafeMethods.stream().forEach(b -> {
						if (testBody.contains(fieldName + " =") || testBody.contains(fieldName + "." + b.getNameAsString())) {
							safe = false;
						};
					});
	    		}
	    	});

        if ( projectAccess ) {
        	md.findAll(MethodCallExpr.class).stream().forEach( a -> {
        		if( a.getScope().isPresent() && a.getScope().get().containsWithin(field) ) {
        			if ( !a.getName().asString().equals("send") ) {
        				boolean found =
    						this.projectSafeMethods
        						.stream()
        						.filter( s -> s.getNameAsString().equals( a.getName().asString() ) )
        						.findFirst().isPresent();
        				if ( !found ) {
        					safe = false;
        				}
        			}
        		}
        	} );
        }
        
    }
	
	public boolean isSafe() {
		return safe;
	}
}

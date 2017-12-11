package br.uece.justsettings.testes;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TesteVisitor extends VoidVisitorAdapter<Void> {

	@Override
	public void visit(ClassOrInterfaceDeclaration cd, Void arg) {
		super.visit(cd, arg);

		NormalAnnotationExpr nae1 = new NormalAnnotationExpr();
		nae1.setName("Request");
		nae1.addPair("path", "http://192.168.25.7:8080/JBWebCrudApp/services/clientes/insert");
		nae1.addPair("\n\t\t\t\tmethod", "HttpMethod.PUT");
		nae1.addPair("\n\t\t\t\tconsumeType", "ContentType.TEXT_XML");

		NormalAnnotationExpr nae2 = new NormalAnnotationExpr();
		nae2.setName("WebEntity");
		nae2.addPair("\n\t\tinsert", nae1.toString());
		
		cd.addAnnotation(nae2);

	}

}
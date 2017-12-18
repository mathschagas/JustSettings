package br.uece.justsettings.persistence.web;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.uece.justsettings.settings.JBConfig;

public class WebActionConfig extends JBConfig {
	
	public WebActionConfig() {
		
		adicionarParametro("path", "String", true);
		adicionarParametro("method", "HttpMethod", true);
		adicionarParametro("produceType", "ContentType", false, "");
		adicionarParametro("consumeType", "ContentType", false, "");
		adicionarParametro("pathParameters", "String", false, "");
		adicionarParametro("queryParameters", "String", false, "");
		
	}

	
	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr requestAnnotationExpr = new NormalAnnotationExpr();
		requestAnnotationExpr.setName("Request");
		// path
		requestAnnotationExpr.addPair(getParametros().get(0).getNome(), "\""+getParametros().get(0).getValor().toString()+"\"");
		// method
		requestAnnotationExpr.addPair("\n\t\t\t\t"+getParametros().get(1).getNome(), getParametros().get(1).getValor().toString());
		// produceType
		if (!getParametros().get(2).getValor().toString().equals("")) { // Se estiver preenchido
			requestAnnotationExpr.addPair("\n\t\t\t\t"+getParametros().get(2).getNome(), getParametros().get(2).getValor().toString());
		}
		// consumeType
		if (!getParametros().get(3).getValor().toString().equals("")) { // Se estiver preenchido
			requestAnnotationExpr.addPair("\n\t\t\t\t"+getParametros().get(3).getNome(), getParametros().get(3).getValor().toString());
		}
		// pathParameters
		// TODO: Verificar com o Fabiano o porquê das {}
		
		// queryParameters
		
		
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName("WebAction");
		nae.addPair("\n\t\trequest", requestAnnotationExpr.toString(new PrettyPrinterConfiguration()));
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.WebAction"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.Request"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.enums.ContentType"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.enums.HttpMethod"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.PathParam"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.QueryParam"), false, false));
	}

}

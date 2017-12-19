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
		if (!getParametros().get(4).getValor().toString().equals("")) { // Se estiver preenchido
			String conteudoDoCampoPathParameters = getParametros().get(4).getValor().toString(); 
			String pathParametersJuntosNaString = conteudoDoCampoPathParameters.replace(" ", "");
			String[] pathParametersSeparados = pathParametersJuntosNaString.split(",");
			String pathParameters = "{";
			for (int i = 0; i < pathParametersSeparados.length; i++) {
				NormalAnnotationExpr pathParamExpr = new NormalAnnotationExpr();
				pathParamExpr.setName("PathParam");
				if (i != 0) pathParameters += "\t\t\t\t\t\t";
				String param = "";
				String jbTextValue = "";
				if (pathParametersSeparados[i].contains("(")) {
					param = pathParametersSeparados[i].substring(0, pathParametersSeparados[i].indexOf("("));
					jbTextValue = pathParametersSeparados[i].substring(pathParametersSeparados[i].indexOf("(")+1, pathParametersSeparados[i].indexOf(")"));
					pathParamExpr.addPair("param", "\""+param+"\"");
					pathParamExpr.addPair("jbTextValue", "\""+jbTextValue+"\"");
				} else {
					param = pathParametersSeparados[i];
					pathParamExpr.addPair("param", "\""+param+"\"");
				}
				pathParameters += pathParamExpr.toString(new PrettyPrinterConfiguration());
				if (i != (pathParametersSeparados.length-1)) pathParameters +=",\n";
			}
			pathParameters += "}";
			requestAnnotationExpr.addPair("\n\t\t\t\tpathParameters", pathParameters);
		}


		// queryParameters
		if (!getParametros().get(5).getValor().toString().equals("")) { // Se estiver preenchido
			
			String conteudoDoCampoQueryParameters = getParametros().get(5).getValor().toString(); 
			String queryParametersJuntosNaString = conteudoDoCampoQueryParameters.replace(" ", "");
			String[] queryParametersSeparados = queryParametersJuntosNaString.split(",");
			String queryParameters = "{";
			for (int i = 0; i < queryParametersSeparados.length; i++) {
				NormalAnnotationExpr queryParamExpr = new NormalAnnotationExpr();
				queryParamExpr.setName("QueryParam");
				if (i != 0) queryParameters += "\t\t\t\t\t\t";
				String param = "";
				String jbTextValue = "";
				if (queryParametersSeparados[i].contains("(")) {
					param = queryParametersSeparados[i].substring(0, queryParametersSeparados[i].indexOf("("));
					jbTextValue = queryParametersSeparados[i].substring(queryParametersSeparados[i].indexOf("(")+1, queryParametersSeparados[i].indexOf(")"));
					queryParamExpr.addPair("param", "\""+param+"\"");
					queryParamExpr.addPair("jbTextValue", "\""+jbTextValue+"\"");
				} else {
					param = queryParametersSeparados[i];
					queryParamExpr.addPair("param", "\""+param+"\"");
				}
				queryParameters += queryParamExpr.toString(new PrettyPrinterConfiguration());
				if (i != (queryParametersSeparados.length-1)) queryParameters +=",\n";
			}
			queryParameters += "}";
			requestAnnotationExpr.addPair("\n\t\t\t\tqueryParameters", queryParameters);
		}
		
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

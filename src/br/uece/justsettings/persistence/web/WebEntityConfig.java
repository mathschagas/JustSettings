package br.uece.justsettings.persistence.web;

import java.util.ArrayList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.uece.justsettings.settings.JBConfig;

public class WebEntityConfig extends JBConfig {

	ArrayList<String> grupoParametros = new ArrayList<>();

	public WebEntityConfig() {
		
		grupoParametros.add("insert");
		grupoParametros.add("update");
		grupoParametros.add("delete");
		grupoParametros.add("find");
		grupoParametros.add("findById");
		grupoParametros.add("findAll");
		
		for (int i = 0; i < grupoParametros.size(); i++) {
			adicionarParametro(grupoParametros.get(i)+"_"+"path", "String", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"method", "HttpMethod", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"produceType", "ContentType", false, "");
			adicionarParametro(grupoParametros.get(i)+"_"+"consumeType", "ContentType", false, "");
			adicionarParametro(grupoParametros.get(i)+"_"+"pathParameters", "String", false, "");
			adicionarParametro(grupoParametros.get(i)+"_"+"queryParameters", "String", false, "");
		}

	}

	@Override
	public AnnotationExpr gerarAnnotation() {

		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName("WebEntity");

		for (int indiceGrupo = 0; indiceGrupo < getParametros().size(); indiceGrupo += 6) {

			NormalAnnotationExpr requestAnnotationExpr = new NormalAnnotationExpr();
			requestAnnotationExpr.setName("Request");
			// path
			requestAnnotationExpr.addPair("path", "\""+getParametros().get(indiceGrupo+0).getValor().toString()+"\"");
			// method
			requestAnnotationExpr.addPair("\n\t\t\t\tmethod", getParametros().get(indiceGrupo+1).getValor().toString());
			// produceType
			if (!getParametros().get(indiceGrupo+2).getValor().toString().equals("")) { // Se estiver preenchido
				requestAnnotationExpr.addPair("\n\t\t\t\tproduceType", getParametros().get(indiceGrupo+2).getValor().toString());
			}
			// consumeType
			if (!getParametros().get(indiceGrupo+3).getValor().toString().equals("")) { // Se estiver preenchido
				requestAnnotationExpr.addPair("\n\t\t\t\tconsumeType", getParametros().get(indiceGrupo+3).getValor().toString());
			}
			// pathParameters
			// TODO: Verificar com o Fabiano o porquê das {}
			if (!getParametros().get(indiceGrupo+4).getValor().toString().equals("")) { // Se estiver preenchido
				String conteudoDoCampoPathParameters = getParametros().get(indiceGrupo+4).getValor().toString(); 
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
			if (!getParametros().get(indiceGrupo+5).getValor().toString().equals("")) { // Se estiver preenchido

				String conteudoDoCampoQueryParameters = getParametros().get(indiceGrupo+5).getValor().toString(); 
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

			nae.addPair("\n\t\t"+ grupoParametros.get(indiceGrupo/6), requestAnnotationExpr.toString(new PrettyPrinterConfiguration()));
		}

		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.WebEntity"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.Request"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.enums.ContentType"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.enums.HttpMethod"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.PathParam"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.persistence.web.annotation.QueryParam"), false, false));
	}
	
}

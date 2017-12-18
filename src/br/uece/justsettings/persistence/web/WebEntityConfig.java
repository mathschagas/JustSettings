package br.uece.justsettings.persistence.web;

import java.util.ArrayList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class WebEntityConfig extends JBConfig {

	public WebEntityConfig() {
		
		ArrayList<String> grupoParametros = new ArrayList<>();
		grupoParametros.add("insert");
		grupoParametros.add("update");
		grupoParametros.add("delete");
		grupoParametros.add("find");
		grupoParametros.add("findById");
		grupoParametros.add("findAll");
		
		for (int i = 0; i < grupoParametros.size(); i++) {
			adicionarParametro(grupoParametros.get(i)+"_"+"path", "String", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"method", "HttpMethod", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"produceType", "ContentType", false);
			adicionarParametro(grupoParametros.get(i)+"_"+"consumeType", "ContentType", false);
			adicionarParametro(grupoParametros.get(i)+"_"+"pathParameters", "String", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"queryParameters", "String", true);
		}

	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		// TODO Auto-generated method stub
		
	}
	
}

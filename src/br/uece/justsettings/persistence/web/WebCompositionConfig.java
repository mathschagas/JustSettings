package br.uece.justsettings.persistence.web;

import java.util.ArrayList;

import br.uece.justsettings.settings.JBConfig;

public class WebCompositionConfig extends JBConfig {
	
	public WebCompositionConfig() {
		
		ArrayList<String> grupoParametros = new ArrayList<>();
		grupoParametros.add("list");
		
		for (int i = 0; i < grupoParametros.size(); i++) {
			adicionarParametro(grupoParametros.get(i)+"_"+"path", "String", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"method", "HttpMethod", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"produceType", "ContentType", false);
			adicionarParametro(grupoParametros.get(i)+"_"+"consumeType", "ContentType", false);
			adicionarParametro(grupoParametros.get(i)+"_"+"pathParameters", "String", true);
			adicionarParametro(grupoParametros.get(i)+"_"+"queryParameters", "String", true);
		}

		
//		// LIST
//		adicionarParametro("list_path", "String", true);
//		adicionarParametro("list_method", "HttpMethod", true);
//		adicionarParametro("list_produceType", "ContentType", true);
//		adicionarParametro("list_queryParameters", "String", true);
		
		
	}
	
}

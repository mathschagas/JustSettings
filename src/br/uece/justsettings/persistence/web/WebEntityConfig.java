package br.uece.justsettings.persistence.web;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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

		
//		// INSERT
//		adicionarParametro("insert_path", "String", true);
//		adicionarParametro("insert_method", "HttpMethod", true);
//		adicionarParametro("insert_consumeType", "ContentType", true);
//		
//		// UPDATE 
//		adicionarParametro("update_path", "String", true);
//		adicionarParametro("update_method", "HttpMethod", true);
//		adicionarParametro("update_consumeType", "ContentType", true);
//		
//		// DELETE
//		adicionarParametro("delete_path", "String", true);
//		adicionarParametro("delete_method", "HttpMethod", true);
//		adicionarParametro("delete_pathParameters", "String", true);
//		
//		// FIND
//		adicionarParametro("find_path", "String", true);
//		adicionarParametro("find_method", "HttpMethod", true);
//		adicionarParametro("find_produceType", "ContentType", true);
//		adicionarParametro("find_queryParameters", "String", true);
//		
//		// FIND BY ID
//		adicionarParametro("findById_path", "String", true);
//		adicionarParametro("findById_method", "HttpMethod", true);
//		adicionarParametro("findById_produceType", "ContentType", true);
//		adicionarParametro("findById_pathParameters", "String", true);
//		
//		// FIND ALL
//		adicionarParametro("findAll_path", "String", true);
//		adicionarParametro("findAll_method", "HttpMethod", true);
//		adicionarParametro("findAll_produceType", "ContentType", true);
	
	}
	
}

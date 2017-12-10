package br.uece.justsettings.persistence.web;

import br.uece.justsettings.settings.JBConfig;

public class WebActionConfig extends JBConfig {
	
	public WebActionConfig() {
		
		adicionarParametro("path", "String", true);
		adicionarParametro("method", "HttpMethod", true);
		adicionarParametro("produceType", "ContentType", false);
		adicionarParametro("consumeType", "ContentType", false);
		adicionarParametro("pathParameters", "String", false);
		adicionarParametro("queryParameters", "String", false);
		
	}

}

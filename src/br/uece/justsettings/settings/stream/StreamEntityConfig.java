package br.uece.justsettings.settings.stream;

import br.uece.justsettings.settings.JBConfig;

public class StreamEntityConfig extends JBConfig {
	
	public StreamEntityConfig() {
		adicionarParametro("name", "String", true);
		adicionarParametro("collectionName", "String", true);
	}

}

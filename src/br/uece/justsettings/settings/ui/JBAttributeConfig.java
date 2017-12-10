package br.uece.justsettings.settings.ui;

import br.uece.justsettings.settings.JBConfig;

public class JBAttributeConfig extends JBConfig {
	
	public JBAttributeConfig() {
		adicionarParametro("order", "Integer", false);
		adicionarParametro("name", "String", false);
		adicionarParametro("id", "Boolean", false, "FALSE");
		adicionarParametro("views", "KindView", false, "{}");
	}
}

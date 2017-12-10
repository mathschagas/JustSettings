package br.uece.justsettings.settings.ui;

import br.uece.justsettings.settings.JBConfig;

public class JBActionConfig extends JBConfig {
	
	public JBActionConfig() {
		adicionarParametro("order", "Integer", false);
		adicionarParametro("name", "String", false);
	}

}

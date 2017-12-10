package br.uece.justsettings.settings.ui;

import br.uece.justsettings.settings.JBConfig;

public class JBTemporalConfig extends JBConfig {
	
	public JBTemporalConfig() {
		adicionarParametro("value", "TemporalType", false ,"TemporalType.DATE");
	}

}

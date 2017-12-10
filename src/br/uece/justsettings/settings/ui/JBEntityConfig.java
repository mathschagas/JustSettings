package br.uece.justsettings.settings.ui;

import br.uece.justsettings.settings.JBConfig;

public class JBEntityConfig  extends JBConfig {
	
	public JBEntityConfig() {
		adicionarParametro("label", "String", true);
		adicionarParametro("collectionLabel", "String", true);
		adicionarParametro("icon", "String", true, "ic_launcher");
	}

}

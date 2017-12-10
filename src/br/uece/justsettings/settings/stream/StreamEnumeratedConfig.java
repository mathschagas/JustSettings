package br.uece.justsettings.settings.stream;

import br.uece.justsettings.settings.JBConfig;

public class StreamEnumeratedConfig extends JBConfig {
	
	public StreamEnumeratedConfig() {
		adicionarParametro("value", "EnumType", false, "EnumType.STRING");
	}

}

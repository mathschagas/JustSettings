package br.uece.justsettings.settings.stream;

import br.uece.justsettings.settings.JBConfig;

public class StreamTemporalConfig extends JBConfig {
	
	public StreamTemporalConfig() {
		adicionarParametro("pattern", "String", false);
	}

}

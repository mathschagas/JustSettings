package br.uece.justsettings.settings;

import java.util.ArrayList;

public abstract class JBConfig {

	protected ArrayList<ParametroConfig> parametros = new ArrayList<>();
	
	protected void adicionarParametro(String nome, String tipo, Boolean usoObrigatorio) {
		parametros.add(new ParametroConfig(nome, tipo, usoObrigatorio));
	}

	protected void adicionarParametro(String nome, String tipo, Boolean usoObrigatorio, Object valorDefault) {
		parametros.add(new ParametroConfig(nome, tipo, usoObrigatorio, valorDefault));
	}

	public ArrayList<ParametroConfig> getParametros() {
		return parametros;
	}

}

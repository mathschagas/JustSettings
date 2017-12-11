package br.uece.justsettings.settings;

import java.util.ArrayList;

public abstract class JBConfig {

	protected ArrayList<ParametroConfig> parametros = new ArrayList<>();
	protected String nome;
	protected String alvo;
	protected String nomeArquivoAlvo;
	protected String tipoConfig;
	
	protected void adicionarParametro(String nome, String tipo, Boolean usoObrigatorio) {
		parametros.add(new ParametroConfig(nome, tipo, usoObrigatorio));
	}

	protected void adicionarParametro(String nome, String tipo, Boolean usoObrigatorio, Object valorDefault) {
		parametros.add(new ParametroConfig(nome, tipo, usoObrigatorio, valorDefault));
	}

	public ArrayList<ParametroConfig> getParametros() {
		return parametros;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAlvo() {
		return alvo;
	}

	public void setAlvo(String alvo) {
		this.alvo = alvo;
	}

	public String getNomeArquivoAlvo() {
		return nomeArquivoAlvo;
	}

	public void setNomeArquivoAlvo(String nomeArquivoAlvo) {
		this.nomeArquivoAlvo = nomeArquivoAlvo;
	}

	public String getTipoConfig() {
		return tipoConfig;
	}

	public void setTipoConfig(String tipoConfig) {
		this.tipoConfig = tipoConfig;
	}



}

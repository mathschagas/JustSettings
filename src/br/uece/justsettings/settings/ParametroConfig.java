package br.uece.justsettings.settings;

public class ParametroConfig {
	
	private String nome;
	private String tipo;
	private Boolean usoObrigatorio;
	private Object valor;
	
	ParametroConfig(String nome, String tipo, Boolean usoObrigatorio) {
		this.setNome(nome);
		this.setTipo(tipo);
		this.setUsoObrigatorio(usoObrigatorio);
	}
	
	ParametroConfig(String nome, String tipo, Boolean usoObrigatorio, Object valorDefault) {
		this.setNome(nome);
		this.setTipo(tipo);
		this.setUsoObrigatorio(usoObrigatorio);
		this.setValor(valorDefault);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getUsoObrigatorio() {
		return usoObrigatorio;
	}

	public void setUsoObrigatorio(Boolean usoObrigatorio) {
		this.usoObrigatorio = usoObrigatorio;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}

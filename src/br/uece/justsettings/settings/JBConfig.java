package br.uece.justsettings.settings;

import java.io.File;
import java.util.ArrayList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;

public abstract class JBConfig {

	protected ArrayList<ParametroConfig> parametros = new ArrayList<>();
	protected String nome;
	protected String alvo;
	protected String tipoConfig;
	protected File arquivoAlvo;
	
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
		return getArquivoAlvo().getName();
	}

	public String getTipoConfig() {
		return tipoConfig;
	}

	public void setTipoConfig(String tipoConfig) {
		this.tipoConfig = tipoConfig;
	}

	public File getArquivoAlvo() {
		return arquivoAlvo;
	}

	public void setArquivoAlvo(File arquivoAlvo) {
		this.arquivoAlvo = arquivoAlvo;
	}

	public abstract AnnotationExpr gerarAnnotation();

	public abstract void gerarImports(CompilationUnit cUnit);


}

package br.uece.justsettings.util;

import java.util.HashMap;

public class Sessao {

    private static Sessao instance;
    private HashMap<String, Object> dadosSessao;

    private Sessao() {
    	dadosSessao = new HashMap<>();
    }

    public static Sessao getInstance() {
        if (instance == null) {
            instance = new Sessao();
        }
        return instance;
    }

    public HashMap<String, Object> obterDadosSessao() {
    	return dadosSessao;
    }
    
    public void adicionarDadosSessao(HashMap<String, Object> dadosAdicionais) {
        for (String chave : dadosAdicionais.keySet()) {
        	Object valor = dadosAdicionais.get(chave);
        	dadosSessao.put(chave, valor);
        }
    }
    
    public void definirDadosSessao(HashMap<String, Object> novosDadosSessao) {
    	limparDadosSessao();
    	adicionarDadosSessao(novosDadosSessao);
    }

	public void limparDadosSessao() {
		dadosSessao.clear();
	}

    
}

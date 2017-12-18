package br.uece.justsettings.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class AtributosEMetodosVisitor extends VoidVisitorAdapter<Void> {

	Sessao sessao = Sessao.getInstance();
	
	@SuppressWarnings("unchecked")
	@Override
	public void visit(FieldDeclaration fd, Void arg) {
		super.visit(fd, arg);
		ArrayList<String> atributosArquivoSelecionado;
		if (sessao.obterDadosSessao().containsKey("ATRIBUTOS_ARQUIVO_SELECIONADO")) {
			atributosArquivoSelecionado = (ArrayList<String>) sessao.obterDadosSessao().get("ATRIBUTOS_ARQUIVO_SELECIONADO");
		} else {
			atributosArquivoSelecionado = new ArrayList<>();
		}
		String nomeAtributoAtual = fd.getVariable(0).getNameAsString();
		atributosArquivoSelecionado.add(nomeAtributoAtual + " (FIELD)");
		sessao.obterDadosSessao().put("ATRIBUTOS_ARQUIVO_SELECIONADO", atributosArquivoSelecionado);
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		ArrayList<String> metodosArquivoSelecionado;
		if (sessao.obterDadosSessao().containsKey("METODOS_ARQUIVO_SELECIONADO")) {
			metodosArquivoSelecionado = (ArrayList<String>) sessao.obterDadosSessao().get("METODOS_ARQUIVO_SELECIONADO");
		} else {
			metodosArquivoSelecionado = new ArrayList<>();
		}
		String nomeMetodoAtual = md.getNameAsString();
		metodosArquivoSelecionado.add(nomeMetodoAtual + " (METHOD)");
		sessao.obterDadosSessao().put("METODOS_ARQUIVO_SELECIONADO", metodosArquivoSelecionado);
		
		
		// Sessao de parametros
		NodeList<Parameter> parametros = md.getParameters();
		ArrayList<String> nomesParametros = new ArrayList<>();
		for (int i = 0; i < parametros.size(); i++) {
			nomesParametros.add(parametros.get(i).getNameAsString() + " ("+nomeMetodoAtual+")");
		}
		
		HashMap<String, ArrayList<String>> parametrosMetodosArquivoSelecionado;
		if (sessao.obterDadosSessao().containsKey("PARAMETROS_METODOS_ARQUIVO_SELECIONADO")) {
			parametrosMetodosArquivoSelecionado = (HashMap<String, ArrayList<String>>) sessao.obterDadosSessao().get("PARAMETROS_METODOS_ARQUIVO_SELECIONADO");
		} else {
			parametrosMetodosArquivoSelecionado = new HashMap<String, ArrayList<String>>();
		}
		
		parametrosMetodosArquivoSelecionado.put(nomeMetodoAtual + " (METHOD)", nomesParametros);
		sessao.obterDadosSessao().put("PARAMETROS_METODOS_ARQUIVO_SELECIONADO", parametrosMetodosArquivoSelecionado);
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void visit(ClassOrInterfaceDeclaration cid, Void arg) {
		super.visit(cid, arg);
		ArrayList<String> classesArquivoSelecionado;
		if (sessao.obterDadosSessao().containsKey("CLASSES_ARQUIVO_SELECIONADO")) {
			classesArquivoSelecionado = (ArrayList<String>) sessao.obterDadosSessao().get("CLASSES_ARQUIVO_SELECIONADO");
		} else {
			classesArquivoSelecionado = new ArrayList<>();
		}
		String nomeClasseAtual = cid.getNameAsString();
		classesArquivoSelecionado.add(nomeClasseAtual + " (CLASS)");
		sessao.obterDadosSessao().put("CLASSES_ARQUIVO_SELECIONADO", classesArquivoSelecionado);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void visit(EnumDeclaration ed, Void arg) {
		super.visit(ed, arg);
		ArrayList<String> enumsArquivoSelecionado;
		if (sessao.obterDadosSessao().containsKey("ENUMS_ARQUIVO_SELECIONADO")) {
			enumsArquivoSelecionado = (ArrayList<String>) sessao.obterDadosSessao().get("ENUMS_ARQUIVO_SELECIONADO");
		} else {
			enumsArquivoSelecionado = new ArrayList<>();
		}
		String nomeEnumAtual = ed.getNameAsString();
		enumsArquivoSelecionado.add(nomeEnumAtual + " (ENUM)");
		sessao.obterDadosSessao().put("ENUMS_ARQUIVO_SELECIONADO", enumsArquivoSelecionado);
	}

}

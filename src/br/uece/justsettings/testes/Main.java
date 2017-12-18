package br.uece.justsettings.testes;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Main {

	public static void main(String[] args) {

		Element agenda = new Element("Agenda");
		//Define Agenda como root
		Document documento = new Document(agenda);
		//Cria o elemento Contato
		Element contato = new Element("Contato");
		//Adiciona o atributo id ao Contato
		contato.setAttribute("id","123");
		//Criando os elementos de contato
		Element nome = new Element("nome");
		nome.setText("Glaucio Guerra");
		Element telefone = new Element("telefone");
		telefone.setText("123-456");
		Element endereco = new Element("endereco");
		endereco.setText("Av. Amaral Peixoto S/N");
		Element email = new Element("email");
		email.setText("glaucioguerra@gmail.com");
		//Adicionando elementos nome, telefone, endereco e email no Contato
		contato.addContent(nome);
		contato.addContent(telefone);
		contato.addContent(endereco);
		contato.addContent(email);
		//Adicionado o Contato a Agenda
		agenda.addContent(contato);
		//Classe responsável para imprimir / gerar o XML
		XMLOutputter xout = new XMLOutputter();
		try {
			//Criando o arquivo de saida
			FileWriter arquivo = new FileWriter(
					new File("c:\\teste\\arquivo.xml"));
			//Imprimindo o XML no arquivo
			xout.output(documento, arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
}
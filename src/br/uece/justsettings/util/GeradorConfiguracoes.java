package br.uece.justsettings.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.uece.justsettings.settings.JBConfig;
import br.uece.justsettings.testes.InjetorConfiguracoes;

public class GeradorConfiguracoes {
	
	private ArrayList<JBConfig> configs;
	private File destino;
	private HashMap<String, ArrayList<JBConfig>> configsPorArquivo;

	public GeradorConfiguracoes(ArrayList<JBConfig> configs, File destino) {
		this.setConfigs(configs);
		this.setDestino(destino);
		configsPorArquivo = new HashMap<>();
	}

	public void gerar() {
		
		for (JBConfig config : configs) {
			String caminhoAbsolutoArquivo = config.getArquivoAlvo().getAbsolutePath();
			if (configsPorArquivo.containsKey(caminhoAbsolutoArquivo)) {
				configsPorArquivo.get(caminhoAbsolutoArquivo).add(config);
			} else {
				ArrayList<JBConfig> novaListaDeConfigs = new ArrayList<>();
				novaListaDeConfigs.add(config);
				configsPorArquivo.put(caminhoAbsolutoArquivo, novaListaDeConfigs);
			}
		}
		
		for (String caminhoDeArquivoEspecifico : configsPorArquivo.keySet()) {
			try {
				
				// Injetando Annotations e Imports
				CompilationUnit cUnit = JavaParser.parse(new FileInputStream(caminhoDeArquivoEspecifico));
				
				VoidVisitor<ArrayList<JBConfig>> injetor = new InjetorConfiguracoes();
				injetor.visit(cUnit, configsPorArquivo.get(caminhoDeArquivoEspecifico));
				
				
				for (JBConfig config : configsPorArquivo.get(caminhoDeArquivoEspecifico)) {
					config.gerarImports(cUnit);
				}
				
				System.out.println(cUnit.toString(new PrettyPrinterConfiguration()));
				
				// Escrevendo arquivos
				File arquivoAtual = new File(destino + "\\" + new File(caminhoDeArquivoEspecifico).getName());
				arquivoAtual.createNewFile();
				FileWriter fw = new FileWriter(arquivoAtual);
				fw.write(cUnit.toString(new PrettyPrinterConfiguration()));
				fw.flush();
				fw.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	public ArrayList<JBConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(ArrayList<JBConfig> configs) {
		this.configs = configs;
	}

	public File getDestino() {
		return destino;
	}

	public void setDestino(File destino) {
		this.destino = destino;
	}

}

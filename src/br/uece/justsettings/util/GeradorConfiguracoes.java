package br.uece.justsettings.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.XMLOutputter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.uece.justsettings.settings.JBConfig;

public class GeradorConfiguracoes {

	private ArrayList<JBConfig> configsParaAnnotations;
	private ArrayList<JBConfig> configsParaXML;
	private File destino;
	private HashMap<String, ArrayList<JBConfig>> configsParaAnnotationPorArquivo;

	public GeradorConfiguracoes(ArrayList<JBConfig> configs, File destino) {

		HashMap<String, Object> dadosSessao = Sessao.getInstance().obterDadosSessao();
		configsParaAnnotations = new ArrayList<>();
		configsParaXML = new ArrayList<>();

		if (dadosSessao.containsKey("INTERFACE")) {
			if (dadosSessao.get("INTERFACE").equals("ANNOTATION")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("INTERFACE")) {
						configsParaAnnotations.add(config);
					}
				}
			} else if (dadosSessao.get("INTERFACE").equals("XML")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("INTERFACE")) {
						configsParaXML.add(config);
					}
				}
			} else {

			}
		}

		if (dadosSessao.containsKey("PERSISTENCIA")) {
			if (dadosSessao.get("PERSISTENCIA").equals("ANNOTATION")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("PERSISTENCIA")) {
						configsParaAnnotations.add(config);
					}
				}
			} else if (dadosSessao.get("PERSISTENCIA").equals("XML")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("PERSISTENCIA")) {
						configsParaXML.add(config);
					}
				}
			} else {

			}
		}

		if (dadosSessao.containsKey("STREAM")) {
			if (dadosSessao.get("STREAM").equals("ANNOTATION")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("STREAM")) {
						configsParaAnnotations.add(config);
					}
				}
			} else if (dadosSessao.get("STREAM").equals("XML")) {
				for (JBConfig config : configs) {
					if (config.getTipoConfig().equals("STREAM")) {
						configsParaXML.add(config);
					}
				}
			} else {

			}
		}
		configsParaAnnotationPorArquivo = new HashMap<>();
		this.destino = destino;

	}

	public void gerar() {
		gerarAnnotations();
		gerarXMLs();
	}

	public void gerarAnnotations() {

		for (JBConfig config : configsParaAnnotations) {
			String caminhoAbsolutoArquivo = config.getArquivoAlvo().getAbsolutePath();
			if (configsParaAnnotationPorArquivo.containsKey(caminhoAbsolutoArquivo)) {
				configsParaAnnotationPorArquivo.get(caminhoAbsolutoArquivo).add(config);
			} else {
				ArrayList<JBConfig> novaListaDeConfigs = new ArrayList<>();
				novaListaDeConfigs.add(config);
				configsParaAnnotationPorArquivo.put(caminhoAbsolutoArquivo, novaListaDeConfigs);
			}
		}

		for (String caminhoDeArquivoEspecifico : configsParaAnnotationPorArquivo.keySet()) {
			try {

				// Injetando Annotations e Imports
				CompilationUnit cUnit = JavaParser.parse(new FileInputStream(caminhoDeArquivoEspecifico));

				VoidVisitor<ArrayList<JBConfig>> injetor = new InjetorConfiguracoes();
				injetor.visit(cUnit, configsParaAnnotationPorArquivo.get(caminhoDeArquivoEspecifico));

				for (JBConfig config : configsParaAnnotationPorArquivo.get(caminhoDeArquivoEspecifico)) {
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

	private void gerarXMLs() {

		// Agrupando por tipo de configuração
		ArrayList<JBConfig> configsInterface = new ArrayList<>();
		ArrayList<JBConfig> configsPersistencia = new ArrayList<>();
		ArrayList<JBConfig> configsStream = new ArrayList<>();

		for (JBConfig config : configsParaXML) {
			switch (config.getTipoConfig()) {
			case "INTERFACE":
				configsInterface.add(config);
				break;
			case "PERSISTENCIA":
				configsPersistencia.add(config);
				break;
			case "STREAM":
				configsStream.add(config);
				break;
			default:
				// Nada
			}
		}

		// Agrupando por arquivos

		HashMap<String, ArrayList<JBConfig>> configsPersistenciaPorArquivo = new HashMap<>();
		HashMap<String, ArrayList<JBConfig>> configsStreamPorArquivo = new HashMap<>();

		gerarXMLInterface(configsInterface);

		for (JBConfig config : configsPersistencia) {
			String caminhoAbsolutoArquivo = config.getArquivoAlvo().getAbsolutePath();
			if (configsPersistenciaPorArquivo.containsKey(caminhoAbsolutoArquivo)) {
				configsPersistenciaPorArquivo.get(caminhoAbsolutoArquivo).add(config);
			} else {
				ArrayList<JBConfig> novaListaDeConfigs = new ArrayList<>();
				novaListaDeConfigs.add(config);
				configsPersistenciaPorArquivo.put(caminhoAbsolutoArquivo, novaListaDeConfigs);
			}
		}

		for (JBConfig config : configsStream) {
			String caminhoAbsolutoArquivo = config.getArquivoAlvo().getAbsolutePath();
			if (configsStreamPorArquivo.containsKey(caminhoAbsolutoArquivo)) {
				configsStreamPorArquivo.get(caminhoAbsolutoArquivo).add(config);
			} else {
				ArrayList<JBConfig> novaListaDeConfigs = new ArrayList<>();
				novaListaDeConfigs.add(config);
				configsStreamPorArquivo.put(caminhoAbsolutoArquivo, novaListaDeConfigs);
			}
		}

	}

	private void gerarXMLInterface(ArrayList<JBConfig> configsInterface) {
		HashMap<String, ArrayList<JBConfig>> configsInterfacePorArquivo = new HashMap<>();
		for (JBConfig config : configsInterface) {
			String caminhoAbsolutoArquivo = config.getArquivoAlvo().getAbsolutePath();
			if (configsInterfacePorArquivo.containsKey(caminhoAbsolutoArquivo)) {
				configsInterfacePorArquivo.get(caminhoAbsolutoArquivo).add(config);
			} else {
				ArrayList<JBConfig> novaListaDeConfigs = new ArrayList<>();
				novaListaDeConfigs.add(config);
				configsInterfacePorArquivo.put(caminhoAbsolutoArquivo, novaListaDeConfigs);
			}
		}

		if (configsInterfacePorArquivo.size() > 0) {

			// Cria documento e elemento raiz
			Element jbCodegenMapping = new Element("jb-codegen-mapping");
			Namespace namespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			Attribute schema = new Attribute("noNamespaceSchemaLocation", "../../schema/jb-codegen-mapping.xsd", namespace);
			jbCodegenMapping.addNamespaceDeclaration(namespace);
			jbCodegenMapping.setAttribute(schema);
			Document arquivoXMLInterface = new Document(jbCodegenMapping);

			for (String caminhoArquivoAtual : configsInterfacePorArquivo.keySet()) {
				try {
					CompilationUnit cUnit = JavaParser.parse(new File(caminhoArquivoAtual));
					VoidVisitorAdapter<Element> xmlVisitor = new XMLVisitor();
					xmlVisitor.visit(cUnit, jbCodegenMapping);

					for (JBConfig configAtual : configsInterfacePorArquivo.get(caminhoArquivoAtual)) {
						String alvoConfigAtual = configAtual.getAlvo();
						if (alvoConfigAtual.contains("CLASS") || alvoConfigAtual.contains("ENUM")) {
							List<Element> filhos = jbCodegenMapping.getChildren();
							if (filhos.size() > 0) {
								Element ultimoFilho = filhos.get(filhos.size()-1);
								Element configXML = configAtual.gerarXML();
								ultimoFilho.addContent(configXML);
							}
						} else if (alvoConfigAtual.contains("FIELD") || alvoConfigAtual.contains("METHOD")) {
							List<Element> filhos = jbCodegenMapping.getChildren();
							if (filhos.size() > 0) {
								Element ultimoFilho = filhos.get(filhos.size()-1);
								List<Element> netos = ultimoFilho.getChildren();
								if (netos.size() > 0) {
									for (int i = 0; i < netos.size(); i++) {
										Element netoAtual = netos.get(i);
										if (netoAtual.getName().equals("attribute") || netoAtual.getName().equals("method")) {
											if (alvoConfigAtual.contains(netoAtual.getAttributeValue("name"))) {
												Element configXML = configAtual.gerarXML();
												netoAtual.addContent(configXML);
											}
										}
									}
								}
							}
						} else { // Parametro de metodo
							List<Element> filhos = jbCodegenMapping.getChildren();
							if (filhos.size() > 0) {
								Element ultimoFilho = filhos.get(filhos.size()-1);
								List<Element> netos = ultimoFilho.getChildren();
								if (netos.size() > 0) {
									for (int i = 0; i < netos.size(); i++) {
										Element netoAtual = netos.get(i);
										if (netoAtual.getName().equals("method")) {
											if (configAtual.getAlvo().contains(netoAtual.getAttributeValue("name"))) {
												List<Element> parametros = netoAtual.getChildren();
												if (parametros.size() > 0) {
													for (Element parametroAtual : parametros) {
														if (parametroAtual.getName().equals("parameter") &&
																configAtual.getAlvo().contains(parametroAtual.getAttributeValue("name"))) {
															Element configXML = configAtual.gerarXML();
															parametroAtual.addContent(configXML);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}


				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < jbCodegenMapping.getChildren().size(); i++) {
				Element filho = jbCodegenMapping.getChildren().get(i);
				for (int j = 0; j < filho.getChildren().size(); j++) {
					Element neto = filho.getChildren().get(j);
					for (int k = 0; k < neto.getChildren().size(); k++) {
						Element bisneto = neto.getChildren().get(k);
						if (bisneto.getName().equals("parameter")) {
							if (bisneto.getChildren().size() == 0) {
								neto.removeContent(bisneto);
								k--;
							}
						}
					}
				}
			}

			for (int i = 0; i < jbCodegenMapping.getChildren().size(); i++) {
				Element filho = jbCodegenMapping.getChildren().get(i);
				for (int j = 0; j < filho.getChildren().size(); j++) {
					Element neto = filho.getChildren().get(j);
					if (neto.getChildren().size() == 0) {
						filho.removeContent(neto);
						j--;
					}
				}
			}

			for (int i = 0; i < jbCodegenMapping.getChildren().size(); i++) {
				Element filho = jbCodegenMapping.getChildren().get(i);
				if (filho.getChildren().size()==0) {
					jbCodegenMapping.removeContent(filho);
					i--;
				}
			}


			File arquivoMappingCodegen = new File(destino.getAbsolutePath()+"\\jb-codegen-mapping.xml");
			try {
				arquivoMappingCodegen.createNewFile();
				FileWriter fw = new FileWriter(arquivoMappingCodegen);
				XMLOutputter xout = new XMLOutputter();
				xout.output(arquivoXMLInterface, fw);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}



	}

	public File getDestino() {
		return destino;
	}

	public void setDestino(File destino) {
		this.destino = destino;
	}

}

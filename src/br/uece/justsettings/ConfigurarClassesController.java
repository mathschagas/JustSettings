package br.uece.justsettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.sun.prism.impl.Disposer.Record;
import br.uece.justsettings.settings.JBConfig;
import br.uece.justsettings.settings.ParametroConfig;
import br.uece.justsettings.util.AtributosEMetodosVisitor;
import br.uece.justsettings.util.ConfigButtonCell;
import br.uece.justsettings.util.ConfigFactory;
import br.uece.justsettings.util.EditingCell;
import br.uece.justsettings.util.GeradorConfiguracoes;
import br.uece.justsettings.util.Sessao;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConfigurarClassesController extends GeralController {

	private static Stage stage;
	private ArrayList<File> arquivosClassesDeNegocio = new ArrayList<File>();
	private ArrayList<JBConfig> configs = new ArrayList<JBConfig>();

	@FXML
	private ComboBox<String> arquivosComboBox;
	@FXML
	private ComboBox<String> atributosMetodosComboBox;
	@FXML
	private ComboBox<String> opcoesConfiguracaoComboBox;
	@FXML
	private ComboBox<String> tipoConfiguracaoComboBox;
	@FXML
	private TableView<ParametroConfig> entradasConfigTable;
	@FXML
	private TableView<JBConfig> progressoConfigTable;
	@FXML
	private Button voltarBtn;
	@FXML
	private Button adicionarBtn;
	@FXML
	private Button gerarBtn;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			stage = primaryStage;
			super.start(stage);
			Parent parent = FXMLLoader.load(getClass().getResource("view/configura_classes.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle("JustSettings - Configurar Classes de Negócio");
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		preencherTipoConfiguracaoComboBox();

		Sessao sessao = Sessao.getInstance();
		if (sessao.obterDadosSessao().containsKey("ARQUIVOS_CLASSES_NEGOCIO")) {
			preencherArquivosComboBox();
		}

		atualizarOpcoesConfiguracaoComboBox();

		tipoConfiguracaoComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				atualizarOpcoesConfiguracaoComboBox();
			}
		});

		arquivosComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				preencherAtributosMetodosComboBox();
				atualizarOpcoesConfiguracaoComboBox();
			}
		});

		atributosMetodosComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				atualizarOpcoesConfiguracaoComboBox();
			}
		});

		voltarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				voltarParaImportacaoArquivos();
			}
		});

		opcoesConfiguracaoComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				atualizarTabelaEntradas();
			}
		});

		adicionarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				adicionarConfiguracao();
			}
		});

		gerarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gerarConfiguracoes();
			}
		});


	}

	protected void voltarParaImportacaoArquivos() {
		try {
			new ImportarController().start(new Stage());
			ConfigurarClassesController.getStage().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void preencherTipoConfiguracaoComboBox() {
		tipoConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("INTERFACE", "PERSISTENCIA","STREAM"));
	}

	private static Stage getStage() {
		return stage;
	}

	@SuppressWarnings("unchecked")
	private void preencherArquivosComboBox() {
		arquivosClassesDeNegocio = (ArrayList<File>) Sessao.getInstance().obterDadosSessao().get("ARQUIVOS_CLASSES_NEGOCIO");
		ArrayList<String> nomesArquivosClassesNegocio = new ArrayList<String>();
		for (int i = 0; i < arquivosClassesDeNegocio.size(); i++) {
			nomesArquivosClassesNegocio.add(arquivosClassesDeNegocio.get(i).getName());
		}
		arquivosComboBox.getItems().addAll(FXCollections.observableArrayList(nomesArquivosClassesNegocio));
	}

	@SuppressWarnings("unchecked")
	private void preencherAtributosMetodosComboBox() {

		atributosMetodosComboBox.getItems().clear();

		// Coloca arquivo selecionado na sessao e remove dados antigos.
		Integer indiceSelecionado = arquivosComboBox.getSelectionModel().getSelectedIndex();
		Sessao.getInstance().obterDadosSessao().put("ARQUIVO_SELECIONADO", arquivosClassesDeNegocio.get(indiceSelecionado));
		Sessao.getInstance().obterDadosSessao().remove("CLASSES_ARQUIVO_SELECIONADO");
		Sessao.getInstance().obterDadosSessao().remove("ENUMS_ARQUIVO_SELECIONADO");
		Sessao.getInstance().obterDadosSessao().remove("ATRIBUTOS_ARQUIVO_SELECIONADO");
		Sessao.getInstance().obterDadosSessao().remove("METODOS_ARQUIVO_SELECIONADO");
		Sessao.getInstance().obterDadosSessao().remove("PARAMETROS_METODOS_ARQUIVO_SELECIONADO");

		// Visita os possíveis alvos de configuração
		try {
			CompilationUnit cUnit = JavaParser.parse(new FileInputStream(arquivosClassesDeNegocio.get(indiceSelecionado).getAbsolutePath()));
			VoidVisitor<?> atributosEMetodosVisitor = new AtributosEMetodosVisitor();
			atributosEMetodosVisitor.visit(cUnit, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		// Armazena possíveis alvos de configuração encontrados na sessão
		ArrayList<String> atributosMetodosArquivoSelecionado = new ArrayList<String>();

		if(Sessao.getInstance().obterDadosSessao().containsKey("CLASSES_ARQUIVO_SELECIONADO")) {
			ArrayList<String> classesArquivoSelecionado = (ArrayList<String>) Sessao.getInstance().obterDadosSessao().get("CLASSES_ARQUIVO_SELECIONADO");
			for (int i = 0; i < classesArquivoSelecionado.size(); i++) {
				atributosMetodosArquivoSelecionado.add(classesArquivoSelecionado.get(i));
			}
		}

		if (Sessao.getInstance().obterDadosSessao().containsKey("ENUMS_ARQUIVO_SELECIONADO")) {
			ArrayList<String> enumsArquivoSelecionado = (ArrayList<String>) Sessao.getInstance().obterDadosSessao().get("ENUMS_ARQUIVO_SELECIONADO");
			for (int i = 0; i < enumsArquivoSelecionado.size(); i++) {
				atributosMetodosArquivoSelecionado.add(enumsArquivoSelecionado.get(i));
			}
		}

		if (Sessao.getInstance().obterDadosSessao().containsKey("ATRIBUTOS_ARQUIVO_SELECIONADO")) {
			ArrayList<String> atributosArquivoSelecionado = (ArrayList<String>) Sessao.getInstance().obterDadosSessao().get("ATRIBUTOS_ARQUIVO_SELECIONADO");
			for (int i = 0; i < atributosArquivoSelecionado.size(); i++) {
				atributosMetodosArquivoSelecionado.add(atributosArquivoSelecionado.get(i));
			}
		}

		if(Sessao.getInstance().obterDadosSessao().containsKey("METODOS_ARQUIVO_SELECIONADO")) {
			ArrayList<String> metodosArquivoSelecionado = (ArrayList<String>) Sessao.getInstance().obterDadosSessao().get("METODOS_ARQUIVO_SELECIONADO");
			for (int i = 0; i < metodosArquivoSelecionado.size(); i++) {
				atributosMetodosArquivoSelecionado.add(metodosArquivoSelecionado.get(i));
			}
		}

		// Adiciona parametros de métodos mapeados com JBAction
		if(Sessao.getInstance().obterDadosSessao().containsKey("PARAMETROS_METODOS_ARQUIVO_SELECIONADO")) {
			HashMap<String, ArrayList<String>> parametrosMetodosArquivoSelecionado = (HashMap<String, ArrayList<String>>) Sessao.getInstance().obterDadosSessao().get("PARAMETROS_METODOS_ARQUIVO_SELECIONADO");
			for (int i = 0; i < configs.size(); i++) {
				if (configs.get(i).getNome().equals("JBAction")) {
					String alvoConfigAtual = configs.get(i).getAlvo();
					if (parametrosMetodosArquivoSelecionado.containsKey(alvoConfigAtual)) {
						for (int j = 0; j < parametrosMetodosArquivoSelecionado.get(alvoConfigAtual).size(); j++) {
							String parametroAtual = parametrosMetodosArquivoSelecionado.get(alvoConfigAtual).get(j);
							atributosMetodosArquivoSelecionado.add(parametroAtual);
						}
					}
				}	
			}
		}



		// Alimenta combobox com os itens encontrados
		atributosMetodosComboBox.getItems().addAll(FXCollections.observableArrayList(atributosMetodosArquivoSelecionado));

	}

	private void atualizarOpcoesConfiguracaoComboBox() {

		// Caso esteja faltando a seleção de algum campo 
		if(tipoConfiguracaoComboBox.getSelectionModel().getSelectedIndex() == -1 ||
				arquivosComboBox.getSelectionModel().getSelectedIndex() == -1 ||
				atributosMetodosComboBox.getSelectionModel().getSelectedIndex() == -1) {
			// Desativa ComboBox
			opcoesConfiguracaoComboBox.getSelectionModel().select(-1);
			opcoesConfiguracaoComboBox.setDisable(true);
		} else {
			// Ativa ComboBox
			opcoesConfiguracaoComboBox.getItems().clear();
			opcoesConfiguracaoComboBox.setDisable(false);

			// Recupera itens selecionados para filtrar opcoes de configuracao
			String tipoConfiguracaoSelecionada = tipoConfiguracaoComboBox.getSelectionModel().getSelectedItem();
			String tipoAlvoSelecionado = atributosMetodosComboBox.getSelectionModel().getSelectedItem();
			String selecaoOpcoes = "";

			// Concatena itens selecionados
			if (tipoAlvoSelecionado.endsWith("(CLASS)")) {
				selecaoOpcoes = tipoConfiguracaoSelecionada+"/CLASS";
			} else if ((tipoAlvoSelecionado.endsWith("(FIELD)"))) {
				selecaoOpcoes = tipoConfiguracaoSelecionada+"/FIELD";
			} else if ((tipoAlvoSelecionado.endsWith("(METHOD)"))) {
				selecaoOpcoes = tipoConfiguracaoSelecionada+"/METHOD";
			} else { // PARAMETROS DE METODOS MAPEADO COM JBACTION
				selecaoOpcoes = tipoConfiguracaoSelecionada+"/PARAMETER";
			}

			// Adiciona opcoes de configuracao de acordo com as entradas concatenadas
			switch (selecaoOpcoes) {
			case "INTERFACE/CLASS":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("JBEntity", "JBEnumeration"));
				break;
			case "INTERFACE/FIELD":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("JBAttribute", "JBLarge", "JBTemporal"));
				break;
			case "INTERFACE/METHOD":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("JBDescription", "JBAction"));
				break;
			case "INTERFACE/PARAMETER":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("JBParameter"));
				break;
			case "PERSISTENCIA/CLASS":

				/* Comentado, pois ainda não temos suporte para configurações em XML para persistencia em SQLite local. */
				//				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("Table", "Entity"));
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("WebEntity"));
				break;
			case "PERSISTENCIA/FIELD":

				/* Comentado, pois ainda não temos suporte para configurações em XML para persistencia em SQLite local. */
				//				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("Column", "Enumerated",
				//						"Id", "JoinColumn", "JoinTable", "Lob", "ManyToMany", "ManyToOne", "OneToMany", "OneToOne",
				//						"Temporal", "Transient", "WebAggregation", "WebComposition"));
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("WebAggregation", "WebComposition"));
				break;
			case "PERSISTENCIA/METHOD":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("WebAction"));
				break;
			case "STREAM/CLASS":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("StreamEntity"));
				break;
			case "STREAM/FIELD":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList("StreamAttribute",
						"StremElement", "StreamEnumerated", "StreamTemporal", "StreamTransient"));
				break;
			case "STREAM/METHOD":
				opcoesConfiguracaoComboBox.getItems().addAll(FXCollections.observableArrayList());
				break;
			default:
				break;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void atualizarTabelaEntradas() {

		entradasConfigTable.setEditable(true);
		entradasConfigTable.getColumns().clear();

		Callback<TableColumn<ParametroConfig, String>, TableCell<ParametroConfig, String>> cellFactory
		= (TableColumn<ParametroConfig, String> param) -> new EditingCell();

		TableColumn<ParametroConfig, String> nomeParametroColumn = new TableColumn("Parametro");
		nomeParametroColumn.setCellValueFactory(new PropertyValueFactory<ParametroConfig, String>("nome"));

		TableColumn<ParametroConfig, String> tipoParametroColumn = new TableColumn("Tipo");
		tipoParametroColumn.setCellValueFactory(new PropertyValueFactory<ParametroConfig, String>("tipo"));

		TableColumn<ParametroConfig, String> valorParametroColumn = new TableColumn("Conteúdo");
		valorParametroColumn.setMinWidth(100);
		valorParametroColumn.setCellValueFactory(new PropertyValueFactory<ParametroConfig, String>("valor"));
		valorParametroColumn.setCellFactory(cellFactory);

		String opcaoEscolhida = opcoesConfiguracaoComboBox.getSelectionModel().getSelectedItem();
		ConfigFactory fabricaConfig = ConfigFactory.getInstance();
		ArrayList<ParametroConfig> parametros = new ArrayList<>();
		if (opcaoEscolhida != null) {
			parametros = fabricaConfig.getParametrosConfig(opcaoEscolhida);
		}

		entradasConfigTable.setItems(FXCollections.observableArrayList(parametros));
		entradasConfigTable.getColumns().addAll(nomeParametroColumn, tipoParametroColumn, valorParametroColumn);

	}

	protected void adicionarConfiguracao() {

		Boolean validado = validarEntradas();
		if (validado) {

			ConfigFactory fabricaConfig = ConfigFactory.getInstance();
			String nomeConfigEscolhida = opcoesConfiguracaoComboBox.getSelectionModel().getSelectedItem();
			JBConfig configEscolhida = fabricaConfig.getConfig(nomeConfigEscolhida);
			configEscolhida.setNome(nomeConfigEscolhida);
			
			// Percorre a lista de arquivos para colocar na config o arquivo alvo selecionado.
			for (File arquivoAlvo : arquivosClassesDeNegocio) {
				if (arquivosComboBox.getSelectionModel().getSelectedItem().equals(arquivoAlvo.getName())) {
					configEscolhida.setArquivoAlvo(arquivoAlvo);
					break;
				}
			}
			
			configEscolhida.setAlvo(atributosMetodosComboBox.getSelectionModel().getSelectedItem());
			configEscolhida.setTipoConfig(tipoConfiguracaoComboBox.getSelectionModel().getSelectedItem());

			ObservableList<ParametroConfig> entradasConfig = entradasConfigTable.getItems();
			for (int i = 0; i < entradasConfig.size(); i++) {
				configEscolhida.getParametros().get(i).setValor(entradasConfig.get(i).getValor());
			}

			configs.add(configEscolhida);
			atualizarTabelaProgresso();
			atualizarOpcoesConfiguracaoComboBox();
			atributosMetodosComboBox.getSelectionModel().select("");
			opcoesConfiguracaoComboBox.getSelectionModel().select("");

		} else {
			// TODO
		}
	}

	private Boolean validarEntradas() {
		// TODO
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void atualizarTabelaProgresso() {

		progressoConfigTable.setEditable(true);
		progressoConfigTable.getColumns().clear();

		ObservableList<JBConfig> data = FXCollections.observableArrayList(configs);
		TableColumn<JBConfig, String> nomeConfigColumn = new TableColumn<JBConfig, String>("Configuração");
		nomeConfigColumn.setCellValueFactory(new PropertyValueFactory<JBConfig, String>("nome"));
		TableColumn<JBConfig, String> nomeArquivoColumn = new TableColumn<JBConfig, String>("Arquivo");
		nomeArquivoColumn.setCellValueFactory(new PropertyValueFactory<JBConfig, String>("nomeArquivoAlvo"));
		TableColumn<JBConfig, String> tipoConfigColumn = new TableColumn<JBConfig, String>("Tipo");
		tipoConfigColumn.setCellValueFactory(new PropertyValueFactory<JBConfig, String>("tipoConfig"));
		TableColumn<JBConfig, String> alvoConfigColumn = new TableColumn<JBConfig, String>("Alvo");
		alvoConfigColumn.setCellValueFactory(new PropertyValueFactory<JBConfig, String>("alvo"));
		TableColumn<JBConfig, String> parametrosConfigColumn = new TableColumn<JBConfig, String>("Parametros");
		parametrosConfigColumn.setCellValueFactory(new PropertyValueFactory<JBConfig, String>("parametros"));


		progressoConfigTable.getColumns().addAll(nomeConfigColumn, nomeArquivoColumn, tipoConfigColumn, alvoConfigColumn, parametrosConfigColumn);

		// Coluna de Ações
		TableColumn acoesConfigColumn = new TableColumn<>("Ações");
		progressoConfigTable.getColumns().add(acoesConfigColumn);
		acoesConfigColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
				ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adicionando o Botão de Remover na Celula da Coluna de Ações
		acoesConfigColumn.setCellFactory(
				new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
					@Override
					public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
						return new ConfigButtonCell(data, configs);
					}
				});

		progressoConfigTable.setItems(data);

	}

	protected void gerarConfiguracoes() {
		// TODO Auto-generated method stub

		JFileChooser fc = new JFileChooser("C:\\Users\\math_\\Documents\\workspace\\JustBusiness\\AndroidStudio\\JBEmptyProject\\app\\src\\main\\java\\org\\jb\\model");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Escolher Destino das Configurações");
		fc.setApproveButtonText("Selecionar");
		int opcao = fc.showOpenDialog(fc);
		if (opcao == JFileChooser.APPROVE_OPTION) {
			File destino = fc.getSelectedFile();
			
			GeradorConfiguracoes gc = new GeradorConfiguracoes(configs, destino);
			gc.gerar();
			
			// TODO: gerar XML/Annotations
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Encerrando Aplicação");
			alert.setContentText("Arquivos gerados com sucesso e armazenados em \""+destino.getAbsolutePath()+"\" . O programa será encerrado.");
			alert.showAndWait();
			System.exit(0);

		}


	}


}

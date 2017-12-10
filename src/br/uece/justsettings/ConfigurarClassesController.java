package br.uece.justsettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import br.uece.justsettings.settings.ParametroConfig;
import br.uece.justsettings.util.AtributosEMetodosVisitor;
import br.uece.justsettings.util.EditingCell;
import br.uece.justsettings.util.ParametrosConfigFactory;
import br.uece.justsettings.util.Sessao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConfigurarClassesController extends GeralController {

	private static Stage stage;
	private ArrayList<File> arquivosClassesDeNegocio = new ArrayList<File>();
	@FXML
	private ComboBox<String> arquivosComboBox;
	@FXML
	private ComboBox<String> atributosMetodosComboBox;
	@FXML
	private ComboBox<String> opcoesConfiguracaoComboBox;
	@FXML
	private ComboBox<String> tipoConfiguracaoComboBox;
	@FXML
	private Button voltarBtn;
	@FXML
	private TableView<ParametroConfig> EntradasConfigTable;
	@FXML
	private TableView<ParametroConfig> ProgressoConfigTable;

	
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

	protected void atualizarTabelaEntradas() {
		
		EntradasConfigTable.setEditable(true);
		EntradasConfigTable.getColumns().clear();
		
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
		ParametrosConfigFactory fabricaParamConfig = ParametrosConfigFactory.getInstance();
		ArrayList<ParametroConfig> parametros = new ArrayList<>();
		if (opcaoEscolhida != null) {
			parametros = fabricaParamConfig.getParametrosConfig(opcaoEscolhida);
		}

		EntradasConfigTable.setItems(FXCollections.observableArrayList(parametros));
		EntradasConfigTable.getColumns().addAll(nomeParametroColumn, tipoParametroColumn, valorParametroColumn);

	}


}

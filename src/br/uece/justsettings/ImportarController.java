package br.uece.justsettings;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.sun.prism.impl.Disposer.Record;
import br.uece.justsettings.util.ButtonCell;
import br.uece.justsettings.util.Sessao;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ImportarController extends GeralController {

	private static Stage stage;

	@FXML
	private Button importarBtn;

	@FXML
	private Button continuarBtn;

	@FXML
	private Button voltarBtn;

	@FXML
	private TableView<File> classesTable = new TableView<File>();

	private ArrayList<File> arquivosClassesDeNegocio = new ArrayList<File>();

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			super.start(stage);
			Parent parent = FXMLLoader.load(getClass().getResource("view/importa_classes.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle("JustSettings - Importar Classes de Negócio");
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);

		Sessao sessao = Sessao.getInstance();
		if (sessao.obterDadosSessao().containsKey("ARQUIVOS_CLASSES_NEGOCIO")) {
			arquivosClassesDeNegocio = getArquivosSessao();
		}
		preencherTabelaClassesDeNegocio(arquivosClassesDeNegocio);

		importarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				adicionarClassesDeNegocio();
			}
		});

		continuarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				salvarEntradasNaSessao();
				irParaConfigurarClasses();
			}
		});

		voltarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				salvarEntradasNaSessao();
				voltarParaDefinicaoSaida();
			}
		});



	}

	@SuppressWarnings("unchecked")
	private ArrayList<File> getArquivosSessao() {
		return (ArrayList<File>) Sessao.getInstance().obterDadosSessao().get("ARQUIVOS_CLASSES_NEGOCIO");
	}

	protected void salvarEntradasNaSessao() {
		Sessao sessao = Sessao.getInstance();
		HashMap<String, Object> dadosArquivosClassesNegocio = new HashMap<>();
		dadosArquivosClassesNegocio.put("ARQUIVOS_CLASSES_NEGOCIO", arquivosClassesDeNegocio);
		sessao.adicionarDadosSessao(dadosArquivosClassesNegocio);
	}

	public void voltarParaDefinicaoSaida() {
		try {
			new DefinirSaidaController().start(new Stage());
			ImportarController.getStage().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void adicionarClassesDeNegocio() {

		JFileChooser fc = new JFileChooser("C:\\Users\\math_\\Documents\\workspace\\JustBusiness\\AndroidStudio\\JBEmptyProject\\app\\src\\main\\java\\org\\jb\\model");
		fc.setMultiSelectionEnabled(true);
		fc.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("Arquivos JAVA", "java");
		fc.setFileFilter(filter);
		int opcao = fc.showOpenDialog(fc);
		if (opcao == JFileChooser.APPROVE_OPTION) {
			for (int i = 0; i < fc.getSelectedFiles().length; i++) {
				File nova = fc.getSelectedFiles()[i];
				// Se o arquivo já não foi incluido na lista de classes de negócio
				if (!arquivosClassesDeNegocio.contains(nova)) {
					arquivosClassesDeNegocio.add(nova);
				}
			}
		}
		preencherTabelaClassesDeNegocio(arquivosClassesDeNegocio);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preencherTabelaClassesDeNegocio(ArrayList<File> arquivosClassesDeNegocio) {
		ObservableList<File> data = FXCollections.observableArrayList(arquivosClassesDeNegocio);
		TableColumn<File, String> nomeClasseColumn = new TableColumn<File, String>("Classes de Negócio");
		nomeClasseColumn.setMinWidth(450);
		nomeClasseColumn.setCellValueFactory(new PropertyValueFactory<File, String>("name"));

		classesTable.getColumns().clear();
		classesTable.getColumns().add(nomeClasseColumn);

		// Coluna de Ações
		TableColumn acoesClasseColumn = new TableColumn<>("Ações");
		classesTable.getColumns().add(acoesClasseColumn);
		acoesClasseColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
				ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adicionando o Botão de Remover na Celula da Coluna de Ações
		acoesClasseColumn.setCellFactory(
				new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
					@Override
					public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
						return new ButtonCell(data, arquivosClassesDeNegocio);
					}
				});

		classesTable.setItems(data);

	}

	public void irParaConfigurarClasses() {
		try {
			new ConfigurarClassesController().start(new Stage());
			ImportarController.getStage().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Stage getStage() {
		return stage;
	}

}

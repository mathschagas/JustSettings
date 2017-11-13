package br.uece.justsettings;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.prism.impl.Disposer.Record;

import br.uece.justsettings.util.ButtonCell;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ImportarController extends Application implements Initializable {

	@FXML
	private MenuItem sairMenuItem;

	@FXML
	private MenuItem sobreMenuItem;

	@FXML
	private Button importarBtn;

	@FXML
	private TableView<File> classesTable = new TableView<File>();

	private ArrayList<File> arquivosClassesDeNegocio = new ArrayList<File>();

	@Override
	public void start(Stage primaryStage) {
		try {

			Parent parent = FXMLLoader.load(getClass().getResource("importa_classes.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle("JustSettings - Importar Classes de Negócio");
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		sobreMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				irParaSobre();
			}
		});

		sairMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sair();
			}
		});

		importarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				adicionarClassesDeNegocio();
			}
		});



	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void adicionarClassesDeNegocio() {

		JFileChooser fc = new JFileChooser();
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

		ObservableList<File> data = FXCollections.observableArrayList(arquivosClassesDeNegocio);
		TableColumn<File, String> nomeClasseColumn = new TableColumn<File, String>("Classes de Negócio");
		nomeClasseColumn.setMinWidth(510);
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

	private void irParaSobre() {
		// TODO Auto-generated method stub
	}

	private void sair() {
		System.exit(0);
	}
}

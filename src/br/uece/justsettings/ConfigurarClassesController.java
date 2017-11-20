package br.uece.justsettings;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.uece.justsettings.util.Sessao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ConfigurarClassesController extends GeralController {

	private static Stage stage;

	@FXML
	private ComboBox<String> arquivosComboBox;
	
	@FXML
	private ComboBox<String> atributosMetodosComboBox;
	
	@FXML
	private ComboBox<String> opcoesConfiguracaoComboBox;
	
	private ArrayList<File> arquivosClassesDeNegocio = new ArrayList<File>();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			stage = primaryStage;
			super.start(stage);
			Parent parent = FXMLLoader.load(getClass().getResource("configura_classes.fxml"));
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
		
		Sessao sessao = Sessao.getInstance();
		if (sessao.obterDadosSessao().containsKey("ARQUIVOS_CLASSES_NEGOCIO")) {
			arquivosClassesDeNegocio = (ArrayList<File>) sessao.obterDadosSessao().get("ARQUIVOS_CLASSES_NEGOCIO");
			ArrayList<String> nomesArquivosClassesNegocio = new ArrayList<String>();
			for (int i = 0; i < arquivosClassesDeNegocio.size(); i++) {
				nomesArquivosClassesNegocio.add(arquivosClassesDeNegocio.get(i).getName());
			}
			arquivosComboBox.getItems().addAll(FXCollections.observableArrayList(nomesArquivosClassesNegocio));
		}
	}
	
	
}

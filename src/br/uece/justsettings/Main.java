package br.uece.justsettings;
	
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Main extends Application implements Initializable {
	
	@FXML
	private MenuItem sairMenuItem;
	
	@FXML
	private MenuItem sobreMenuItem;
	
	@FXML
	private Button importarBtn;

	@FXML
	private TableView<String> classesTable;
	
	@FXML
	private TableColumn<String, String> nomeClasseColumn;

	@FXML
	private TableColumn<String, String> acoesClasseColumn;

	@Override
	public void start(Stage primaryStage) {
		try {

			Parent parent = FXMLLoader.load(getClass().getResource("importa_classes.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Importar Classes de Negócio");
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
		
		sairMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sair();
			}
		});
		
	}

	protected void sair() {
		System.exit(0);
	}
}

package br.uece.justsettings;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class GeralController extends Application implements Initializable {

	@FXML
	private MenuItem sairMenuItem;

	@FXML
	private MenuItem sobreMenuItem;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Insere o mesmo comportamento do item "Sair" do Menu "Opções" ao clicar no botão X (Fechar).
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				sair();
			}
		});
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		sobreMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				abrirPainelSobre();
			}
		});

		sairMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sair();
			}
		});

		
	}
	
	private void abrirPainelSobre() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sobre o JustSettings");
		alert.setHeaderText(null);
		alert.setContentText("JUSTSETTINGS: UMA FERRAMENTA PARA GERAÇÃO AUTOMÁTICA DE CONFIGURAÇÕES DO FRAMEWORK JUSTBUSINESS." +
				"\n\nTrabalho de Conclusão de Curso apresentado ao Curso de Graduação em Ciência da Computação " + 
				"do Centro de Ciências e Tecnologia da Universidade Estadual do Ceará, como requisito parcial " + 
				"à obtenção do grau de bacharel em Ciência da Computação. " + 
				"\n\n Autor: Matheus Lima Chagas" +
				"\n Orientador: Prof. PhD Paulo Henrique Mendes Maia");

		alert.showAndWait();
	}

	private void sair() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Encerrar Aplicação");
		alert.setHeaderText("Aviso");
		alert.setContentText(" O conteúdo não será salvo. Tem certeza que deseja encerrar o programa?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.exit(0);
		} else {
		    // Usuário cancelou ou fechou janela de confirmação.
		}
	}

}

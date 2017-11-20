package br.uece.justsettings;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import br.uece.justsettings.util.Sessao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DefinirSaidaController extends GeralController {

	private static Stage stage;

	@FXML
	private Button continuarBtn;
	
	@FXML
	private Text avisoNenhumSelecionadoText;

	@FXML
	private RadioButton interfaceAnnotationRadioBtn;
	@FXML
	private RadioButton persistenciaAnnotationRadioBtn;
	@FXML
	private RadioButton webSeviceAnnotationRadioBtn;
	@FXML
	private RadioButton interfaceXMLRadioBtn;
	@FXML
	private RadioButton persistenciaXMLRadioBtn;
	@FXML
	private RadioButton webServiceXMLRadioBtn;
	@FXML
	private RadioButton interfaceNenhumRadioBtn;
	@FXML
	private RadioButton persistenciaNenhumRadioBtn;
	@FXML
	private RadioButton webServiceNenhumRadioBtn;
	
	private ToggleGroup grupoInterface = new ToggleGroup();
	private ToggleGroup grupoPersistencia = new ToggleGroup();
	private ToggleGroup grupoWebService = new ToggleGroup();

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			stage = primaryStage;
			super.start(stage);
			Parent parent = FXMLLoader.load(getClass().getResource("view/define_saidas.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle("JustSettings - Definir Tipos da Saída de Configuração");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);

		agruparRadioButtons();
		setarConfiguracaoInicialTela();
		configurarMudancaRadioButtons();
		
		// Comportamento do clique do Botão Continuar
		continuarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				salvarEntradasNaSessao();
				irParaTelaDeImportarClasses();
			}
		});
	}
	
	private void irParaTelaDeImportarClasses() {
		new ImportarController().start(new Stage());
		DefinirSaidaController.getStage().close();
		
	}

	private void salvarEntradasNaSessao() {
		
		RadioButton selecionadoInterface = (RadioButton) grupoInterface.getSelectedToggle();
		RadioButton selecionadoPersistencia = (RadioButton) grupoPersistencia.getSelectedToggle();
		RadioButton selecionadoWebService = (RadioButton) grupoWebService.getSelectedToggle();
		
		Sessao sessao = Sessao.getInstance();
		
		HashMap<String, Object> dadosEntrada = new HashMap<>();
		if (selecionadoInterface.equals(interfaceAnnotationRadioBtn)) {
			dadosEntrada.put("INTERFACE", "ANNOTATION");
		} else if (selecionadoInterface.equals(interfaceXMLRadioBtn)){
			dadosEntrada.put("INTERFACE", "XML");
		} else {
			dadosEntrada.put("INTERFACE", "NENHUM");
		}
		
		if (selecionadoPersistencia.equals(persistenciaAnnotationRadioBtn)) {
			dadosEntrada.put("PERSISTENCIA", "ANNOTATION");
		} else if (selecionadoPersistencia.equals(persistenciaXMLRadioBtn)){
			dadosEntrada.put("PERSISTENCIA", "XML");
		} else {
			dadosEntrada.put("PERSISTENCIA", "NENHUM");
		}
		
		if (selecionadoWebService.equals(webSeviceAnnotationRadioBtn)) {
			dadosEntrada.put("WEBSERVICE", "ANNOTATION");
		} else if (selecionadoWebService.equals(webServiceXMLRadioBtn)){
			dadosEntrada.put("WEBSERVICE", "XML");
		} else {
			dadosEntrada.put("WEBSERVICE", "NENHUM");
		}				
		
		sessao.adicionarDadosSessao(dadosEntrada);
	}
	
	private void configurarMudancaRadioButtons() {

		EventHandler<ActionEvent> mudancaRadio = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Se todos estiverem "Nenhum" como opção marcada, não será possível continuar com a execução.
				if (grupoInterface.getSelectedToggle().equals(interfaceNenhumRadioBtn) &&
						grupoPersistencia.getSelectedToggle().equals(persistenciaNenhumRadioBtn) &&
						grupoWebService.getSelectedToggle().equals(webServiceNenhumRadioBtn)) {
					continuarBtn.setDisable(true);
					avisoNenhumSelecionadoText.setText("Aviso: Você deve selecionar pelo menos uma opção em Annotation ou XML.");
				} else { // caso contrario, ou seja, pelo menos um item marcado como Annotation ou XML, o botão de continuar é ativado
					continuarBtn.setDisable(false);
					avisoNenhumSelecionadoText.setText("");
				}
			}
		};
		
		// Inserindo comportamento em todos os botões.
		interfaceAnnotationRadioBtn.setOnAction(mudancaRadio);
		interfaceXMLRadioBtn.setOnAction(mudancaRadio);
		interfaceNenhumRadioBtn.setOnAction(mudancaRadio);
		persistenciaAnnotationRadioBtn.setOnAction(mudancaRadio);
		persistenciaXMLRadioBtn.setOnAction(mudancaRadio);
		persistenciaNenhumRadioBtn.setOnAction(mudancaRadio);
		webSeviceAnnotationRadioBtn.setOnAction(mudancaRadio);
		webServiceXMLRadioBtn.setOnAction(mudancaRadio);
		webServiceNenhumRadioBtn.setOnAction(mudancaRadio);
		
	}

	private void agruparRadioButtons() {

		interfaceAnnotationRadioBtn.setToggleGroup(grupoInterface);
		interfaceXMLRadioBtn.setToggleGroup(grupoInterface);
		interfaceNenhumRadioBtn.setToggleGroup(grupoInterface);
		
		persistenciaAnnotationRadioBtn.setToggleGroup(grupoPersistencia);
		persistenciaXMLRadioBtn.setToggleGroup(grupoPersistencia);
		persistenciaNenhumRadioBtn.setToggleGroup(grupoPersistencia);
		
		webSeviceAnnotationRadioBtn.setToggleGroup(grupoWebService);
		webServiceXMLRadioBtn.setToggleGroup(grupoWebService);
		webServiceNenhumRadioBtn.setToggleGroup(grupoWebService);
		
	}
	
	private void setarConfiguracaoInicialTela() {

		HashMap<String, Object> dadosSessao = Sessao.getInstance().obterDadosSessao();
		
		// Se houver dados de definição de saída presentes na sessão
		if (dadosSessao.containsKey("INTERFACE") && dadosSessao.containsKey("PERSISTENCIA") &&dadosSessao.containsKey("WEBSERVICE")) {
			restaurarDadosSessao();
		} else {
			// Configuração Inicial padrão
			interfaceNenhumRadioBtn.setSelected(true);
			persistenciaNenhumRadioBtn.setSelected(true);
			webServiceNenhumRadioBtn.setSelected(true);
			continuarBtn.setDisable(true);
		}
		
	}

	private void restaurarDadosSessao() {
		
		HashMap<String, Object> dadosSessao = Sessao.getInstance().obterDadosSessao();
		
		if (dadosSessao.get("INTERFACE").equals("ANNOTATION")) {
			interfaceAnnotationRadioBtn.setSelected(true);
		} if (dadosSessao.get("INTERFACE").equals("XML")) {
			interfaceXMLRadioBtn.setSelected(true);
		} else {
			interfaceNenhumRadioBtn.setSelected(true);
		}
		
		if (dadosSessao.get("PERSISTENCIA").equals("ANNOTATION")) {
			persistenciaAnnotationRadioBtn.setSelected(true);
		} if (dadosSessao.get("PERSISTENCIA").equals("XML")) {
			persistenciaXMLRadioBtn.setSelected(true);
		} else {
			persistenciaNenhumRadioBtn.setSelected(true);
		}
		
		if (dadosSessao.get("WEBSERVICE").equals("ANNOTATION")) {
			webSeviceAnnotationRadioBtn.setSelected(true);
		} if (dadosSessao.get("PERSISTENCIA").equals("XML")) {
			webServiceXMLRadioBtn.setSelected(true);
		} else {
			webServiceNenhumRadioBtn.setSelected(true);
		}

	}

	public static Stage getStage() {
		return stage;
	}
	
}

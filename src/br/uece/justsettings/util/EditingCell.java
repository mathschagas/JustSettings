package br.uece.justsettings.util;

import br.uece.justsettings.settings.ParametroConfig;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;


public class EditingCell extends TableCell<ParametroConfig, String> {

	private TextField textField;
	private ComboBox<String> comboBox;

	public EditingCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			String tipoAtual = EditingCell.this.getTableView().getItems().get(this.getIndex()).getTipo();
			if (tipoAtual == "Integer" || tipoAtual == "String") {
				createTextField();
				setText(null);
				setGraphic(textField);
				textField.selectAll();
			} else {
				createComboBox();
				setText(null);
				setGraphic(comboBox);
			}
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(item);
			setGraphic(null);
		} else {
			if (isEditing()) {
				String tipoAtual = EditingCell.this.getTableView().getItems().get(this.getIndex()).getTipo();
				if (tipoAtual == "Integer" || tipoAtual == "String") {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
					
				} else {
					if (comboBox != null) {
						comboBox.setValue(getString());
					}
					setText(getString());
					setGraphic(comboBox);
				
				}
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnAction((e) -> {
			commitEdit(textField.getText());
			EditingCell.this.getTableView().getItems().get(this.getIndex()).setValor(textField.getText());
		});
		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				System.out.println("Commiting " + textField.getText());
				commitEdit(textField.getText());
				EditingCell.this.getTableView().getItems().get(this.getIndex()).setValor(textField.getText());
			}
		});
	}

	private void createComboBox() {
		String tipoAtual = EditingCell.this.getTableView().getItems().get(this.getIndex()).getTipo();
		switch (tipoAtual) {
		case "Boolean":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("TRUE", "FALSE"));
			break;
		case "KindView":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("{KindView.ALL}", "{KindView.INSERT}", "{KindView.DETAIL}", "{KindView.EDIT}", "{KindView.FIND}"));
			break;
		case "TemporalType":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("TemporalType.DATE", "TemporalType.TIME", "TemporalType.TIMESTAMP"));
			break;
		case "DescriptionType":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("DescriptionType.PRIMARY", "DescriptionType.SECONDARY"));
			break;
		case "EnumType":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("EnumType.ORDINAL", "EnumType.STRING"));
			break;
		case "HttpMethod":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("HttpMethod.GET", "HttpMethod.POST", "HttpMethod.PUT", "HttpMethod.DELETE"));
			break;
		case "ContentType":
			comboBox = new ComboBox<>(FXCollections.observableArrayList("ContentType.TEXT_PLAIN", "ContentType.TEXT_XML",
					"ContentType.TEXT_JSON", "ContentType.APPLICATION_XML", "ContentType.APPLICATION_JSON"));
			break;
		default:
			comboBox = new ComboBox<>(FXCollections.observableArrayList("Opção 1", "Opção 2"));
			break;
		}
		comboBoxConverter(comboBox);
		comboBox.valueProperty().set(getString());
		comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		comboBox.setOnAction((e) -> {
			commitEdit(comboBox.getSelectionModel().getSelectedItem());
			EditingCell.this.getTableView().getItems().get(this.getIndex()).setValor(comboBox.getSelectionModel().getSelectedItem());
		});
	}

	private void comboBoxConverter(ComboBox<String> comboBox) {
		// Define rendering of the list of values in ComboBox drop down. 
		comboBox.setCellFactory((c) -> {
			return new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.toString());
					}
				}
			};
		});
	}

	
	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}

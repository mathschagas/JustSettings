package br.uece.justsettings.util;

import java.util.ArrayList;
import com.sun.prism.impl.Disposer.Record;
import br.uece.justsettings.settings.JBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ConfigButtonCell extends TableCell<Record, Boolean> {
	
	final Button cellButton = new Button("Remover");

	public ConfigButtonCell(ObservableList<JBConfig> data, ArrayList<JBConfig> configs){
		
		// Ação tomada quando o botão é pressionado
		cellButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent t) {
				// get Selected Item
				JBConfig configAtual = (JBConfig) ConfigButtonCell.this.getTableView().getItems().get(ConfigButtonCell.this.getIndex());
				//remove selected item from the table list
				data.remove(configAtual);
				configs.remove(configAtual);
				
				// TODO Remover JBParameters quando remover um JBAction
				
				data.clear();
				data.setAll(FXCollections.observableArrayList(configs));
			}
		});
	}

	// Exibe o botão se a linha não estiver vazia
	@Override
	protected void updateItem(Boolean t, boolean empty) {
		super.updateItem(t, empty);
		if(!empty){
			setGraphic(cellButton);
		} else {
			setGraphic(null);
		}
	}
}
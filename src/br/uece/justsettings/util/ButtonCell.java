package br.uece.justsettings.util;

import java.io.File;
import java.util.ArrayList;

import com.sun.prism.impl.Disposer.Record;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell extends TableCell<Record, Boolean> {
	
	final Button cellButton = new Button("Remover");

	public ButtonCell(ObservableList<File> data, ArrayList<File> arquivosClassesDeNegocio){

		// Ação tomada quando o botão é pressionado
		cellButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent t) {
				// get Selected Item
				File classeAtual = (File) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
				//remove selected item from the table list
				data.remove(classeAtual);

				System.out.println("excluindo: " + classeAtual.getName());
				arquivosClassesDeNegocio.remove(classeAtual);
				data.clear();
				data.setAll(FXCollections.observableArrayList(arquivosClassesDeNegocio));
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
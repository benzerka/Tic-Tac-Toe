package com.benzerka.gui.options;

import com.benzerka.logic.TileState;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class ElementsListCell extends ListCell<TileState> {
    @Override
    public void updateItem(TileState tileState, boolean empty) {
        super.updateItem(tileState, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            if(tileState!=null){
                URL resource = ElementsListCell.class.getResource(tileState.getIconPath());
                ImageView imageView = new ImageView(new Image(resource.toString()));
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                setGraphic(imageView);
            }
        }
    }
}

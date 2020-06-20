package com.benzerka.gui.options;

import com.benzerka.logic.TileState;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ElementsListCellFactory implements Callback<ListView<TileState>, ListCell<TileState>> {
    @Override
    public ListCell<TileState> call(ListView<TileState> param) {
        return new ElementsListCell();
    }
}

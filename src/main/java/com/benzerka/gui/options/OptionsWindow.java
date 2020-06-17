package com.benzerka.gui.options;

import com.benzerka.gui.components.tile.TileController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OptionsWindow extends GridPane {
    private GridPane mainScreen;
    private VBox mainScreenMenu;

    @FXML
    private ComboBox<String> comboBox;

    public OptionsWindow(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modifyComboBox();
    }

    private void modifyComboBox() {

    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }
}

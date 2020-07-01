package com.benzerka.gui.multiplayer;

import com.benzerka.gui.components.PlayableWindow;
import com.benzerka.logic.GameLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

@Getter
public class MultiplayerWindow extends GridPane implements PlayableWindow {
    private GridPane mainScreen;
    private VBox mainScreenMenu;

    public MultiplayerWindow(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiplayerRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    @Override
    public GameLogic getGameLogic() {
        return null;
    }

    @Override
    public GridPane getGameBoard() {
        return null;
    }
}

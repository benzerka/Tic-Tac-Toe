package com.benzerka.gui.options;

import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.logic.TileState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionsWindow extends GridPane {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private PlayerModelGetter playerModelGetter;
    private TileState possiblePlayerOneTileState;
    private TileState possiblePlayerTwoTileState;
    private boolean isPlayerOneFlaggedForChange;
    private boolean isPlayerTwoFlaggedForChange;

    @FXML
    private ComboBox<TileState> comboBoxPlayer1;

    @FXML
    private ComboBox<TileState> comboBoxPlayer2;

    @FXML
    private ComboBox<TileState> comboBoxMultiplayer;

    @FXML
    private TextField multiplayerTextField;

    @FXML
    private Label singleplayerErrorLabel;

    @FXML
    private Label multiplayerErrorLabel;

    public OptionsWindow(GridPane mainScreen, VBox mainScreenMenu, PlayerModelGetter playerModelGetter) {
        this.playerModelGetter = playerModelGetter;
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            modifyComboBoxes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        singleplayerErrorLabel.getStyleClass().add("error-label");
        multiplayerErrorLabel.getStyleClass().add("error-label");
        addListenerToMultiplayerTextField();
    }

    private void addListenerToMultiplayerTextField() {
        multiplayerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            multiplayerErrorLabel.setVisible(false);
            if (newValue.length() < 3) {
                Platform.runLater(() -> {
                    multiplayerErrorLabel.setText("Nickname length must be greater than 2!");
                    multiplayerErrorLabel.setVisible(true);
                });
            }
            if (newValue.length() > 16) {
                Platform.runLater(() -> {
                    if (!newValue.matches(".")) {
                        multiplayerTextField.replaceText(16, multiplayerTextField.getLength(), "");
                        multiplayerErrorLabel.setText("Nickname length cannot be greater than 16!");
                        multiplayerErrorLabel.setVisible(true);
                    }
                });
            }
        });
    }

    private void modifyComboBoxes() {
        ObservableList<TileState> comboBoxItems = getObservableListOfPictures();
        configureComboBoxes(comboBoxPlayer1, comboBoxItems, StartingElement.CIRCLE);
        configureComboBoxes(comboBoxMultiplayer, comboBoxItems, StartingElement.CIRCLE);
        configureComboBoxes(comboBoxPlayer2, comboBoxItems, StartingElement.CROSS);
        comboBoxPlayer1.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == comboBoxPlayer2.getValue()) {
                singleplayerErrorLabel.setVisible(true);
                possiblePlayerOneTileState = newValue;
                isPlayerOneFlaggedForChange = true;
            } else {
                isPlayerOneFlaggedForChange = false;
                singleplayerErrorLabel.setVisible(false);
                playerModelGetter.setTileStateForFirstPlayer(newValue);
                if (isPlayerTwoFlaggedForChange) {
                    playerModelGetter.setTileStateForSecondPlayer(possiblePlayerTwoTileState);
                }
            }
        });
        comboBoxPlayer2.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == comboBoxPlayer1.getValue()) {
                singleplayerErrorLabel.setVisible(true);
                possiblePlayerTwoTileState = newValue;
                isPlayerTwoFlaggedForChange = true;
            } else {
                isPlayerTwoFlaggedForChange = false;
                singleplayerErrorLabel.setVisible(false);
                playerModelGetter.setTileStateForSecondPlayer(newValue);
                if (isPlayerOneFlaggedForChange) {
                    playerModelGetter.setTileStateForFirstPlayer(possiblePlayerOneTileState);
                }
            }
        });
        comboBoxMultiplayer.valueProperty().addListener((observable, oldValue, newValue) -> {
            playerModelGetter.setTileStateMultiplayer(newValue);
        });
    }

    private ObservableList<TileState> getObservableListOfPictures() {
        List<TileState> comboBoxItemsList = new ArrayList<>();
        comboBoxItemsList.add(TileState.CIRCLE);
        comboBoxItemsList.add(TileState.CROSS);
        comboBoxItemsList.add(TileState.TRIANGLE);
        comboBoxItemsList.add(TileState.STAR);
        comboBoxItemsList.add(TileState.PENTAGON);
        comboBoxItemsList.add(TileState.DIAMOND);
        return FXCollections.observableList(comboBoxItemsList);
    }

    private void configureComboBoxes(ComboBox<TileState> comboBox, ObservableList<TileState> comboBoxItems, StartingElement startingElement) {
        int elementAndPlayerIndex = (startingElement == StartingElement.CIRCLE) ? 0 : 1;
        comboBox.setItems(comboBoxItems);
        comboBox.setValue(comboBoxItems.get(elementAndPlayerIndex));
        comboBox.setCellFactory(new ElementsListCellFactory());
        comboBox.setButtonCell(new ElementsListCell());
        setTileStateIfPlayerModelGetterFieldsAreNull(comboBox, elementAndPlayerIndex);
    }

    private void setTileStateIfPlayerModelGetterFieldsAreNull(ComboBox<TileState> comboBox, int player) {
        switch (player) {
            case 0:
                if (Objects.isNull(playerModelGetter.getFirstPlayer())) {
                    playerModelGetter.setTileStateForFirstPlayer(comboBox.getValue());
                }
                break;
            case 1:
                if (Objects.isNull(playerModelGetter.getSecondPlayer())) {
                    playerModelGetter.setTileStateForSecondPlayer(comboBox.getValue());
                }
                break;
        }
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public String getMultiplayerNickname() {
        return multiplayerTextField.getText();
    }

}

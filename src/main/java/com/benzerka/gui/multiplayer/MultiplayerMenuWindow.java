package com.benzerka.gui.multiplayer;

import com.benzerka.gui.components.GUIEventHandler;
import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.gui.components.alerts.AlertCreator;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.logic.MultiplayerClientThread;
import com.benzerka.logic.MultiplayerServerThread;
import com.benzerka.logic.ValidateCustomSettings;
import com.benzerka.logic.ValidateIPAddress;
import com.benzerka.logic.ValidateTextField;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MultiplayerMenuWindow extends GridPane implements Initializable {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private OptionsWindow optionsWindow;
    private MultiplayerWindow multiplayerWindow;
    private MultiplayerServerThread serverThread;
    private MultiplayerClientThread clientThread;
    private ValidateTextField validateTextField;
    private ValidateIPAddress validateIPAddress;
    private Thread mainServerThread;
    private Runnable onClientBoundAction;
    private ValidateCustomSettings validateCustomSettings;
    private GUIEventHandler guiEventHandler;

    @FXML
    private Label infoLabel;

    @FXML
    private Label portLabel;

    @FXML
    private Label ipLabel;

    @FXML
    private Button startGameButton;

    @FXML
    private Button connectButton;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private GridPane customSettings;

    @FXML
    private Label joinGameErrorLabel;

    @FXML
    private TextField boardXSizeTextField;

    @FXML
    private TextField boardYSizeTextField;

    @FXML
    private TextField winningConditionTextField;

    @FXML
    private Label errorLabel;

    public MultiplayerMenuWindow(GridPane mainScreen, VBox mainScreenMenu, GUIEventHandler guiEventHandler, MultiplayerWindow multiplayerWindow, OptionsWindow optionsWindow) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        this.guiEventHandler = guiEventHandler;
        this.multiplayerWindow = multiplayerWindow;
        this.optionsWindow = optionsWindow;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiplayerMenuRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            modifyChoiceBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyChoiceBox() {
        choiceBox.getItems().add(0, "Tic-Tac-Toe");
        choiceBox.getItems().add(1, "Gomoku");
        choiceBox.getItems().add(2, "Custom");
        choiceBox.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateCustomSettings = new ValidateCustomSettings(boardXSizeTextField, boardYSizeTextField, winningConditionTextField, errorLabel, choiceBox, true);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
            validateCustomSettings.toggleCustomSettings(customSettings, newValue);
        });
        boardXSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(boardXSizeTextField, newValue);
        });
        boardYSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(boardYSizeTextField, newValue);
        });
        winningConditionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(winningConditionTextField, newValue);
        });


        validateIPAddress = new ValidateIPAddress();
        infoLabel.getStyleClass().add("error-label");
        errorLabel.getStyleClass().add("error-label");
        joinGameErrorLabel.getStyleClass().add("error-label");
        connectButton.disableProperty().bind(checkRegex(ipField).or(getPortFieldRange().not()));
        onClientBoundAction = getRunnable();
        //serverThread = new MultiplayerServerThread(ipLabel, portLabel, onClientBoundAction);
        validateTextField = new ValidateTextField(portField);
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateTextField.replaceLettersToNumbers(newValue);
        });
    }

    private Runnable getRunnable() {
        return () -> {
            if (serverThread.getMultiplayerServer().getClientSocket().isBound()) {
                Platform.runLater(() -> {
                    infoLabel.setText("Someone has successfully connected!");
                    infoLabel.setTextFill(Color.valueOf("#329613"));
                    startGameButton.setDisable(false);
                });
            } else {
                Platform.runLater(() -> startGameButton.setDisable(true));
            }
        };
    }

    private BooleanBinding checkRegex(TextField textField) {
        return Bindings.createBooleanBinding(() -> !validateIPAddress.validate(textField.getText()), textField.textProperty());
    }

    private BooleanBinding getPortFieldRange() {
        // port must be greater than 1023 and less than or equal to 65535
        return portField.textProperty().greaterThan(String.valueOf(1023)).and(portField.textProperty().lessThanOrEqualTo(String.valueOf(65535)));
    }

    public void startGame(ActionEvent actionEvent) {
        startGameButton.setDisable(true);
        configureErrorLabel(infoLabel, false, "Waiting for someone to connect.", "#b90c0c");
        ipLabel.setText("");
        portLabel.setText("");
        // telling client that startGame button was clicked, so the window on client's side can change.
        serverThread.getMultiplayerServer().sendInstructionsToClient("startGame");
        // However, we also have to tell the Client the board size and winning condition, also host's name
        // and we initialize the game with given sizes
        if (validateCustomSettings.startGame(multiplayerWindow, choiceBox)) {
            // start game cancelled
        } else {
            // successfully started the game
            guiEventHandler.setPlayableWindow(multiplayerWindow, true);
            guiEventHandler.addPlayableListener(true);
            mainScreen.getChildren().setAll(multiplayerWindow);
            validateCustomSettings.clearTextFieldValues();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);

    }

    public void hostGame(ActionEvent actionEvent) {
        // check PortField, get the textfieldcheck into separate class and custom settings as well
        infoLabel.setVisible(true);
        // we have to close previous server if it somehow still exists
        if (Objects.nonNull(mainServerThread)) {
            serverThread.getMultiplayerServer().stopServer();
        }
        serverThread = new MultiplayerServerThread(ipLabel, portLabel, onClientBoundAction);
        mainServerThread = new Thread(serverThread);
        mainServerThread.start();
    }

    public void clientJoin(ActionEvent actionEvent) {
        joinGameErrorLabel.setVisible(false);
        Runnable didNotConnect = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    configureErrorLabel(joinGameErrorLabel, true, "Could not connect to the server.", "#b90c0c");
                });
            }
        };
        Runnable didConnect = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    configureErrorLabel(joinGameErrorLabel, true, "Successfully connected to the lobby!", "#329613");
                });
            }
        };
        Runnable changeClientWindow = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    configureErrorLabel(joinGameErrorLabel, false, "Could not connect to the server.", "#b90c0c");
                    // initialize the game according to server's sent sizes
                    multiplayerWindow.initializeGame(3, 3, 3);
                    mainScreen.getChildren().setAll(multiplayerWindow);
                });
            }
        };
        clientThread = new MultiplayerClientThread(ipField.getText(), Integer.valueOf(portField.getText()), didConnect, didNotConnect, changeClientWindow);
        Thread thread = new Thread(clientThread);
        thread.start();
    }

    private void configureErrorLabel(Label label, boolean visible, String msg, String colour) {
        label.setVisible(visible);
        label.setText(msg);
        label.setTextFill(Color.valueOf(colour));
    }
}

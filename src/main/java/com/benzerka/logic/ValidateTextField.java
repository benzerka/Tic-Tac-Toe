package com.benzerka.logic;

import javafx.scene.control.TextField;

public class ValidateTextField {
    private TextField textField;

    public ValidateTextField(TextField textField) {
        this.textField = textField;
    }

    public void replaceLettersToNumbers(String newValue) {
        // Check if user wrote any letter instead of a number:
        if (!newValue.matches("\\d*")) {
            // change that letter into empty space
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
}

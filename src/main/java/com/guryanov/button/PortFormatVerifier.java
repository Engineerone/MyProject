package com.guryanov.button;

import javax.swing.*;

import static com.guryanov.ui.AppFrame.userMessage;

public class PortFormatVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        int fieldValue;
        try {
            String port = ((JTextField) input).getText();
            fieldValue = Integer.parseInt(port);
            if (fieldValue > 65535 | fieldValue <= 0) {
                userMessage.error("Invalid Port format");
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            userMessage.error("Invalid Port format");
            return false;
        }
    }
}

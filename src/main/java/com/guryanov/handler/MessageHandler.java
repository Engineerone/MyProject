package com.guryanov.handler;

import javax.swing.*;

import static com.guryanov.ui.AppFrame.statusString;


public class MessageHandler implements UserMessage {


    void ErrorMessage(Exception ex) {
        JOptionPane.showMessageDialog(
                null,
                ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        statusString.setText("");
        statusString.append(ex.getMessage());
    }

    void infoMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Info", JOptionPane.INFORMATION_MESSAGE);
        statusString.setText("");
        statusString.append(message);
    }

    @Override
    public void ErrorExeption(Exception ex) {
        ErrorMessage(ex);
    }

    @Override
    public void InfoMessage(String message) {
        infoMessage(message);
    }
}
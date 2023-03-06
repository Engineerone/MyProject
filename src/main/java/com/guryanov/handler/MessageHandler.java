package com.guryanov.handler;

import com.guryanov.interf.UserMessage;

import javax.swing.*;

import static com.guryanov.ui.AppFrame.statusString;

public class MessageHandler implements UserMessage {
    void errorMessage(Exception ex) {
        JOptionPane.showMessageDialog(
                null,
                ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
     //   statusString.setText("");
        statusString.append("\n"+ex.getMessage());
    }

    void infoMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Info", JOptionPane.INFORMATION_MESSAGE);
        //statusString.setText("");
        statusString.append("\n"+message);
    }

    @Override
    public void error(Exception ex) {
        errorMessage(ex);
    }

    @Override
    public void info(String message) {
        infoMessage(message);
    }
}
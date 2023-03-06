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
        statusString.append("\n" + ex.getMessage());
    }

    void infoMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Info", JOptionPane.INFORMATION_MESSAGE);
        statusString.append("\n" + message);
    }

    void aboutMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Project-SPAMMER", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void error(Exception ex) {
        errorMessage(ex);
    }

    @Override
    public void info(String message) {
        infoMessage(message);
    }

    @Override
    public void about(String message) {
        aboutMessage(message);
    }
}
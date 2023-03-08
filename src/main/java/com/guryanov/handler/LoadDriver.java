package com.guryanov.handler;

import static com.guryanov.ui.AppFrame.userMessage;

public class LoadDriver {
    public LoadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            userMessage.error(ex);
        }
    }

}

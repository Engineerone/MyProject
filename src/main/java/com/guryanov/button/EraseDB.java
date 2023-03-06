package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.*;

public class EraseDB {
public static void erase() {
        try {
            new DatabaseHandler().eraseDB();
          //  statusString.setText("");
            statusString.append("\nerase completed");
        } catch (SQLException ex) {
            userMessage.error(ex);
        }
    }
}

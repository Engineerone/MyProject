package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.*;

public class EraseDB {
    public EraseDB() {
        try {
            new DatabaseHandler().eraseDB();
            tableModel.setRowCount(0);
        statusString.setText("");
        statusString.append("Erase completed");
        } catch (SQLException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}

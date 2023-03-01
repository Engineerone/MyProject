package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.userMessage;

public class CreateDB {
    public CreateDB() {
        try {
            new DatabaseHandler().createDB();
            userMessage.InfoMessage("Create DB completed");
        } catch (SQLException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}
package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.userMessage;

public class CreateDB {
    public static void create() {
        try {
            new DatabaseHandler().createDB();
            userMessage.info("Create DB completed");
        } catch (SQLException ex) {
            userMessage.error(ex);
        }
    }
}
package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.*;

public class DeleteDB {
    public DeleteDB() {
        try {
            new DatabaseHandler().dropDB();
            userMessage.InfoMessage("DB deleted");
        } catch (SQLException ex) {
            userMessage.ErrorExeption(ex);

        }
    }
}

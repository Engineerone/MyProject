package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.*;

public class DeleteDB {
    public static void delete() {
        try {
            new DatabaseHandler().dropDB();
            userMessage.info("DB deleted");
        } catch (SQLException ex) {
            userMessage.error(ex);
        }
    }
}

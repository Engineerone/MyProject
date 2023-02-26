package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;

import static com.guryanov.ui.AppFrame.status;
import static com.guryanov.ui.AppFrame.statusString;

public class DeleteDB {
    public DeleteDB() {
        try {
            new DatabaseHandler().dropDB();
            new NoticeHandler().dbDrop();
        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);
        } finally {
            statusString.append("\n" + "DB delete " + status);
        }
    }
}

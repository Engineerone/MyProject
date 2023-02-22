package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;
import com.guryanov.handler.NoticeHandler;
import java.sql.SQLException;
import static com.guryanov.ui.AppFrame.*;

public class EraseDB {
    public EraseDB() {
        try {
            new DatabaseHandler().eraseDB();
            tableModel.setRowCount(0);
        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);
        } catch (ClassNotFoundException ex) {
            new NoticeHandler().getClassNotFoundException(ex);
        } finally {
            statusString.append("\n"+"Erase DB " + status);
        }
    }
}

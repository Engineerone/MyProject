package com.guryanov.button;

import com.guryanov.handler.*;
import static com.guryanov.ui.AppFrame.*;

import java.sql.SQLException;


public class CreateDB {
    public CreateDB() {
        try {
            new DatabaseHandler().createDB();
            new NoticeHandler().createDB();
        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);
        } catch (ClassNotFoundException ex) {
            new NoticeHandler().getClassNotFoundException(ex);
        } finally {
            statusString.append("\n"+"DB create " + status);
        }
    }
}

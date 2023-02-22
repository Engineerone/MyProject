package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;
import java.util.List;

import static com.guryanov.ui.AppFrame.*;

public class LoadFromDB {
    public LoadFromDB() {
        try {
            List<String[]> result;
            result = new DatabaseHandler().LoadFromDB();
            tableModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                String[] value = result.get(i);
                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2], value[3]});
            }
        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);
        } catch (ClassNotFoundException ex) {
            new NoticeHandler().getClassNotFoundException(ex);
        } finally {
            statusString.append("\n" + "Load from DB " + status);
        }
    }
}

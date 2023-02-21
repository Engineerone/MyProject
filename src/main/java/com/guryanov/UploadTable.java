package com.guryanov;

import java.sql.SQLException;
import java.util.List;
import static com.guryanov.AppFrame.*;

public class UploadTable {
    UploadTable() {
        NoticeHandler noticeHandler = new NoticeHandler();
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            List<String[]> result;
            result = dbHandler.LoadFromDB();
            tableModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                String[] value = result.get(i);
                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2], value[3]});
            }
        } catch (SQLException ex) {
            noticeHandler.getSQLExceptionNotice(ex);
        } catch (ClassNotFoundException ex) {
            noticeHandler.getClassNotFoundException(ex);
        } finally {
            statusString.setText("Load " + status);
        }
    }
}

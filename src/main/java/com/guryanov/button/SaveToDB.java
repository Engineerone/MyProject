package com.guryanov.button;

import com.guryanov.handler.*;

import javax.xml.crypto.Data;

import static com.guryanov.ui.AppFrame.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class SaveToDB {
    public SaveToDB(boolean useDB) {
        if (areaFileContain.getText().length() == 0 | areaFileContain.getText().startsWith("File format")) {
            statusString.append("\n" + "File not load or text area is empty.");
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\n" + "Save start " + timestamp);
        try {
            String areaFileContainString = areaFileContain.getText();
            String name, email;
            String tempString = "";
            List<String[]> result = new ArrayList<>();
            for (int i = 0; i < areaFileContainString.length(); i++) {
                if (areaFileContainString.charAt(i) != '\n') {
                    tempString += areaFileContainString.charAt(i);
                } else {
                    String[] resultString = new String[2];
                    int tabStatement = tempString.indexOf('\t');
                    name = tempString.substring(0, tabStatement);
                    email = tempString.substring(tabStatement + 1);
                    tempString = "";
                    Pattern VALID_EMAIL_ADDRESS_REGEX =
                            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                    if (!matcher.matches()) {
                        statusString.append("\n" + "The string does not match the format -> " + email);
                        continue;
                    }
                    resultString[0] = name;
                    resultString[1] = email;
                    result.add(resultString);
                }
            }
            if (!useDB) {
                tableModel.setRowCount(0);
                for (int i = 0; i < result.size(); i++) {
                    String[] value = result.get(i);
                    tableModel.insertRow(i, new Object[]{i + 1, value[0], value[1], ""});
                }
            } else
                new DatabaseHandler().insertRow(result);

        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);
        } finally {
            statusString.append("\n" + "Save to DB " + status);
            timestamp = new Timestamp(System.currentTimeMillis());
            statusString.append("\n" + "Save end " + timestamp);
        }
    }
}


package com.guryanov.button;

import com.guryanov.handler.*;

import javax.xml.crypto.Data;

import static com.guryanov.ui.AppFrame.*;

import java.sql.SQLException;
import java.util.regex.*;

public class SaveToDB {
    public SaveToDB() {
        if (areaFileContain.getText().length() == 0) {
            statusString.append("\n" + "File not load or text area is empty.");
            return;
        }
        try {
            String areaFileContainString = areaFileContain.getText();
            String name, email;
            String tempString = "";
            for (int i = 0; i < areaFileContainString.length(); i++) {
                if (areaFileContainString.charAt(i) != '\n') {
                    tempString += areaFileContainString.charAt(i);
                } else {
                    int tabStatement = tempString.indexOf('\t');
                    name = tempString.substring(0, tabStatement);
                    email = tempString.substring(tabStatement + 1);
                    tempString = "";
                    Pattern VALID_EMAIL_ADDRESS_REGEX =
                            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                    if (!matcher.matches()) {
                        statusString.append("\n" + "Строка не соответствует формату - " + email);
                        continue;
                    }
                    new DatabaseHandler().insertRow(name, email);
                }
            }
        } catch (SQLException ex) {
            new NoticeHandler().getSQLExceptionNotice(ex);

        } finally {
            statusString.append("\n" + "Save to DB " + status);
        }
    }
}

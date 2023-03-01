package com.guryanov.handler;


import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.guryanov.ui.AppFrame.*;

public class FileContainCheck {

    public Set<List<String>> result;

  public   FileContainCheck(JTextArea areaFileContain) {
        result = new HashSet<>();
        if (areaFileContain.getText().length() == 0 | areaFileContain.getText().startsWith("File format")) {
            statusString.append("\n" + "File area is empty");
            result.clear();
            return;
        }
        String areaFileContainString = areaFileContain.getText();
        String name, email;
        String tempString = "";
        boolean emailConfirmed;

        Set<List<String>> emailDuplicatedList = new HashSet<>();
        for (int i = 0; i < areaFileContainString.length(); i++) {
            if (areaFileContainString.charAt(i) != '\n') {
                tempString += areaFileContainString.charAt(i);
            } else {
                List<String> resultString;
                int tabStatement = tempString.indexOf('\t');
                name = tempString.substring(0, tabStatement);
                email = tempString.substring(tabStatement + 1).toLowerCase();
                tempString = "";
                emailConfirmed = new EmailCheck(name, email).emailConfirmed;
                if (!emailConfirmed) {
                    statusString.append("\nEmail does not match the format: " + name + " " + email);
                    continue;
                }
                resultString = Arrays.asList(name, email);
                if (emailDuplicatedList.add(Arrays.asList(email))) {
                    result.add(resultString);
                    statusString.append("\n" + "Line load: " + resultString.get(0) + " " + resultString.get(1));
                } else {
                    statusString.append("\n" + "Email exist: " + resultString.get(0) + " " + resultString.get(1) + " :skip");
                }
            }
        }
    }
}

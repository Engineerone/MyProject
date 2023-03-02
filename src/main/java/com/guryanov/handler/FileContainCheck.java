package com.guryanov.handler;


import javax.swing.*;
import java.util.*;

import static com.guryanov.ui.AppFrame.*;

public class FileContainCheck {

    public Set<List<String>> result;
    public List<String> exept;

    public FileContainCheck(JTextArea areaFileContain) {
        result = new HashSet<>();
        Set<List<String>> emailDuplicatedList = new HashSet<>();
        Scanner scanner = new Scanner(areaFileContain.getText());
        String name, email, tempString;
        boolean emailConfirmed;
        while (scanner.hasNext()) {
            tempString = scanner.nextLine().trim();
            if (tempString.startsWith("\n") ||
                    tempString.length() == 0 ||
                    !tempString.contains("\t") ||
                    !tempString.contains("@")) {
                statusString.append("\nThe string does not contain name and email data");
                continue;
            }
            List<String> resultString;
            int tabStatement = tempString.indexOf('\t');
            name = tempString.substring(0, tabStatement);
            email = tempString.substring(tabStatement + 1).toLowerCase();
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
                statusString.append("\n" + "Email exist, SKIP: " + resultString.get(0) + " " + resultString.get(1));
            }
        }
        scanner.close();
    }
}

package com.guryanov.handler;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.*;

import static com.guryanov.ui.AppFrame.*;

public class FileCheck {

    public static Set<List<String>> checkFileLoadTable(JTextArea areaFileContain) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //  statusString.setText("");
        statusString.append("\nload start: " + timestamp);
        Set<List<String>> result = new HashSet<>();
        Set<List<String>> emailDuplicatedList = new HashSet<>();
        Scanner scanner = new Scanner(areaFileContain.getText());
        String name, email, tempString;
        boolean emailConfirmed;
        int count = 0;
        while (scanner.hasNext()) {
            tempString = scanner.nextLine().trim();
            if (tempString.startsWith("\n") ||
                    tempString.length() == 0 ||
                    !tempString.contains("\t") ||
                    !tempString.contains("@")) {
                statusString.append("\nstring error -> " + tempString);
                continue;
            }
            List<String> resultString;
            int tabStatement = tempString.indexOf('\t');
            name = tempString.substring(0, tabStatement);
            email = tempString.substring(tabStatement + 1).toLowerCase();
            emailConfirmed = EmailCheck.check(email);
            if (!emailConfirmed) {
                statusString.append("\nemail check error -> " + email);
                continue;
            }
            resultString = Arrays.asList(name, email);
            if (emailDuplicatedList.add(Arrays.asList(email))) {
                result.add(resultString);
                count++;
                //statusString.append("\n" + "string load -> " + resultString.get(0) + " " + resultString.get(1));
            } else {
                statusString.append("\n" + "email duplicate -> " + resultString.get(0) + " " + resultString.get(1));

            }
        }
        statusString.append("\nload completed (" + count + " rows)");
        scanner.close();
        timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\n" + "load end -> " + timestamp);
        return result;
    }

    public static StringBuffer checkFileAddName(StringBuffer stringBuffer) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //  statusString.setText("");
        statusString.append("\nsave start -> " + timestamp);
        StringBuffer result = new StringBuffer();
        Set<List<String>> emailDuplicatedList = new HashSet<>();
        Scanner scanner = new Scanner(String.valueOf(stringBuffer));
        String name, email, tempString;
        boolean emailConfirmed;
        int count = 0;
        while (scanner.hasNext()) {
            tempString = scanner.nextLine().trim();
            if (tempString.startsWith("\n") ||
                    tempString.length() == 0 ||
                    !tempString.contains("@")) {
                statusString.append("\nstring error -> " + tempString);
                continue;
            }
            List<String> resultString;
            int AtStatement = tempString.indexOf('@');
            name = tempString.substring(0, AtStatement);
            email = tempString.toLowerCase();
            emailConfirmed = EmailCheck.check(email);
            if (!emailConfirmed) {
                statusString.append("\nemail check error -> " + email);
                continue;
            }
            resultString = Arrays.asList(name, email);
            if (emailDuplicatedList.add(Arrays.asList(email))) {
                result.append(name + "\t" + email + "\n");
                count++;
                //statusString.append("\n" + "String load -> " + resultString.get(0) + " " + resultString.get(1));
            } else {
                statusString.append("\n" + "email duplicate -> " + resultString.get(0) + " " + resultString.get(1));
            }
        }
        scanner.close();
        statusString.append("\nsave completed (" + count + " rows)");
        timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\n" + "save end -> " + timestamp);
        return result;
    }
}

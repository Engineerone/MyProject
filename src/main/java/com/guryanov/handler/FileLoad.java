package com.guryanov.handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.guryanov.ui.AppFrame.*;

public class FileLoad {
    public StringBuffer fileContain = new StringBuffer();

    public FileLoad() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
            while (reader.ready()) {
                String tempString = reader.readLine().trim();
                if (tempString.length() == 0 | !tempString.contains("\t") | !tempString.contains("@")) continue;
                if (tempString.startsWith("\n")) continue;
                fileContain.append(tempString + "\n");
            }
        } catch (IOException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}

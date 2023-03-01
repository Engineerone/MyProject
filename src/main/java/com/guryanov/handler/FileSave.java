package com.guryanov.handler;

import java.io.*;
import java.util.Scanner;

import static com.guryanov.ui.AppFrame.*;

public class FileSave {
    public FileSave(FileLoad fileLoad) {
        String currentDirectory = fileChooser.getCurrentDirectory().getAbsolutePath();
        String outputFilePath = currentDirectory + "\\outputfile.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            BufferedReader reader = new BufferedReader(new StringReader(String.valueOf(fileLoad.fileContain)));
            while (reader.ready()) {
                String tempString = reader.readLine() + "\n";
                int tabStatement = tempString.indexOf("@");
                String name = tempString.substring(0, tabStatement);
                writer.write(name + "\t" + tempString);
            }
            statusString.setText("");
            statusString.append("Output file write completed: " + outputFilePath);
        } catch (IOException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}
package com.guryanov.handler;

import com.guryanov.interf.FileAction;

import java.io.*;

import static com.guryanov.ui.AppFrame.*;

public class FileHandler implements FileAction {
    @Override
    public StringBuffer load(File file) {
        StringBuffer fileContain = new StringBuffer();
        try (FileReader reader = new FileReader(file)) {
            while (reader.ready()) {
                char ch = (char) reader.read();
                fileContain.append(ch);
            }
        } catch (IOException ex) {
            userMessage.error(ex);
        }
        return fileContain;
    }

    @Override
    public void save(StringBuffer stringBuffer, String fileName, String fileCurrentDirectory) {
        fileName = fileName.substring(0, fileName.indexOf('.'));
        String outputFilePath = fileCurrentDirectory + "\\" + fileName + "-out.txt";
        try (FileWriter fileWriter = new FileWriter((outputFilePath))) {
            fileWriter.write(String.valueOf(stringBuffer));
            userMessage.info("\nsave file completed -> " + outputFilePath);
        } catch (IOException ex) {
            userMessage.error(ex);
        }
    }
}

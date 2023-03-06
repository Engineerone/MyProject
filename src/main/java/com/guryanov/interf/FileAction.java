package com.guryanov.interf;

import java.io.File;

public interface FileAction {
    StringBuffer load(File file);

    void save(StringBuffer stringBuffer, String fileName, String currentDirectory);
}

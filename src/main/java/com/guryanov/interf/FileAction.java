package com.guryanov.interf;

import java.io.File;

public interface FileAction {
    StringBuffer Load(File file);

    void Save(StringBuffer stringBuffer, String fileName, String currentDirectory);
}

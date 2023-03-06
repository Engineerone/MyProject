package com.guryanov.interf;

public interface UserMessage {
    void error(Exception ex);

    void info(String message);

    void about(String message);
}

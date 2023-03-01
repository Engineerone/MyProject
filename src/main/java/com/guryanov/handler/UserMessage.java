package com.guryanov.handler;

public interface UserMessage {
    void ErrorExeption(Exception ex);
    void InfoMessage(String message);
}

package com.guryanov;

class ClearString {
    public static String getClearString(String string) {
        int i = 0;
        while (string.charAt(i) == ' ') {
            string = string.substring(1, string.length());
        }
        while (string.charAt(string.length() - 1) == ' ') {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }
}

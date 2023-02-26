package com.guryanov.config;

public class ConfigSetting {
    public static String db_schema = "projectdb";
    public static String db_table_name = "users";
    public static String db_table_columnName = "name";
    public static String db_table_columnEmail = "email";
    public static String db_table_columnSend = "send";
    public static String db_host = "localhost";
    public static String db_port = "3306";
    public static String db_user = "root";
    public static String db_secr = "12345";
    public static String email_smtp_server = "smtp.yandex.ru";
    public static String email_smtp_port = "465";
    public static String email_smtp_user = "notify@zst.ru";
    public static String email_smtp_secr = "KlGpvO";
    public static String email_fieldFrom = "notify@zst.ru";
    public static boolean realSend = false;
    public static boolean useWithDB = true;
}

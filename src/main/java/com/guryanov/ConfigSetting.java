package com.guryanov;

public class ConfigSetting {
    protected static String db_schema = "projectdb";
    protected static String db_table_name = "users";
    protected static String db_table_columnName = "name";
    protected static String db_table_columnEmail = "email";
    protected static String db_table_columnSend = "send";
    protected static String db_host = "localhost";
    protected static String db_port = "3306";
    protected static String db_user = "root";
    protected static String db_secr = "12345";
    protected static String email_smtp_server = "smtp.yandex.ru";
    protected static String email_smtp_port = "465";
    protected static String email_smtp_user = "notify@zst.ru";
    protected static String email_smtp_secr = "KlGpvO";
    protected static String email_fieldFrom = "notify@zst.ru";
    protected static boolean realSend = false;
}

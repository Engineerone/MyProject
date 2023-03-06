package com.guryanov.button;

import com.guryanov.config.DBType;
import com.guryanov.interf.SQLQuery;

import static com.guryanov.config.ConfigSetting.*;

public class Query implements SQLQuery {
    private String result;

    @Override
    public String getConnection(DBType dbType, String db_schema) {
        switch (dbType) {
            case MySQL:
                result = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_schema;
                break;
            case PostgreSQL:
                result = "jdbc:postgresql://" + db_host + ":" + db_port + "/" + db_schema;
                break;
        }
        return result;
    }

    public String getConnection(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "jdbc:mysql://" + db_host + ":" + db_port + "/";
                break;
            case PostgreSQL:
                result = "jdbc:postgresql://" + db_host + ":" + db_port + "/";
                break;
        }
        return result;
    }

    public String getConnection(String db_host, String db_port, DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "jdbc:mysql://" + db_host + ":" + db_port + "/";
                break;
            case PostgreSQL:
                result = "jdbc:postgresql://" + db_host + ":" + db_port + "/";
                break;
        }
        return result;
    }


    @Override
    public String getInsert(DBType dbType) {
        switch (dbType) {
            case MySQL: {
                result = "INSERT INTO "
                        + db_table_name + "("
                        + db_table_columnName + ","
                        + db_table_columnEmail + ","
                        + db_table_columnSend + ")" +
                        "VALUES(?,?,?)";
                break;
            }
            case PostgreSQL: {
                result = "INSERT INTO "
                        + db_table_name + "("
                        + db_table_columnName + ","
                        + db_table_columnEmail + ","
                        + db_table_columnSend + ")" +
                        "VALUES(?,?,?)"+
                        "ON CONFLICT DO NOTHING";
                break;
            }
        }

        return result;
    }


    @Override
    public String getUpdate(DBType dbType) {
        switch (dbType) {
            case MySQL: {
                result = "UPDATE "
                        + db_table_name
                        + " SET "
                        + db_table_columnSend + "=?"
                        + " WHERE "
                        + db_table_columnName + "=?"
                        + " AND "
                        + db_table_columnEmail + "=?";
                break;
            }
            case PostgreSQL: {
                result = "UPDATE "
                        + db_table_name
                        + " SET "
                        + db_table_columnSend + "=?"
                        + " WHERE "
                        + db_table_columnName + "=?"
                        + " AND "
                        + db_table_columnEmail + "=?";
                break;
            }
        }

        return result;
    }

    @Override
    public String getErase(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "DELETE FROM " + db_table_name;
                break;
            case PostgreSQL:
                result = "DELETE FROM " + db_table_name;
                break;
        }
        return result;
    }

    @Override
    public String getLoadFromDB(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "SELECT * FROM " + db_table_name + " ORDER BY id";
                break;
            case PostgreSQL:
                result = "SELECT * FROM " + db_table_name + " ORDER BY id";
                break;
        }
        return result;
    }

    @Override
    public String getTableDB(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "CREATE TABLE " + db_table_name +
                        "(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        db_table_columnName + " varchar (100) NOT NULL, " +
                        db_table_columnEmail + " varchar(100) NOT NULL UNIQUE, " +
                        db_table_columnSend + " varchar(45) NOT NULL, " +
                        "PRIMARY KEY (`id`)" +
                        ")";
                break;
            case PostgreSQL:
                result = "CREATE TABLE " + db_table_name +
                        "(" +
                        "ID    SERIAL PRIMARY  KEY, " +
                        db_table_columnName + " VARCHAR(100) NOT NULL, " +
                        db_table_columnEmail + " VARCHAR(100) NOT NULL UNIQUE, " +
                        db_table_columnSend + " VARCHAR(45)  NOT NULL" +
                        ")";
                break;
        }
        return result;
    }


    @Override
    public String getCreateDB(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "CREATE SCHEMA " + db_schema;
                break;
            case PostgreSQL:
                result = "CREATE DATABASE " + db_schema;
                break;
        }
        return result;
    }


    @Override
    public String getDropDB(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "DROP DATABASE " + db_schema;
                break;
            case PostgreSQL:
                result = "DROP DATABASE " + db_schema;
                break;
        }
        return result;
    }

    @Override
    public String getVersionDB(DBType dbType) {
        switch (dbType) {
            case MySQL:
                result = "SELECT VERSION()";
                break;
            case PostgreSQL:
                result = "SELECT VERSION()";
                break;
        }
        return result;
    }
}

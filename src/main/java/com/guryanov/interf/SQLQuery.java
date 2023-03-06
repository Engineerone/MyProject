package com.guryanov.interf;

import com.guryanov.config.DBType;

public interface SQLQuery {
    String getConnection(DBType dbType, String db_schema);

    String getConnection(String db_host, String db_port, DBType dbType);

    String getConnection(DBType dbType);

    String getInsert(DBType dbType);

    String getUpdate(DBType dbType);

    String getErase(DBType dbType);

    String getLoadFromDB(DBType dbType);

    String getCreateDB(DBType dbType);

    String getTableDB(DBType dbType);

    String getDropDB(DBType dbType);

    String getVersionDB(DBType dbType);

}

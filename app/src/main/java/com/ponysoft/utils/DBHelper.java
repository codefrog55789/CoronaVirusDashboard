package com.ponysoft.utils;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

public class DBHelper {

    private DbManager.DaoConfig daoConfig = null;
    private static DbManager dbManager = null;
    private final String DB_NAME = "cvdashboard.db";
    private final int VERSION = 1;

    private DBHelper() {

        daoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                .setDbVersion(VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) throws DbException {

                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) throws DbException {

                    }
                });

        try {

            dbManager = x.getDb(daoConfig);
        } catch (DbException ex) {

            Log.d("x.getDb", ex.toString());
        }
    }

    public static DbManager shareInstance() {

        if (null == dbManager) {

            DBHelper dbHelper = new DBHelper();
        }

        return dbManager;
    }
}

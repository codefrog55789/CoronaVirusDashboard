package com.ponysoft.utils.dbs;

import com.ponysoft.models.dbmodels.Saved;
import com.ponysoft.utils.DBHelper;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.List;

public class SavedHelper implements IDBHelper {
    private DbManager dbManager = null;
    private static SavedHelper savedHelper = null;

    public static SavedHelper shareInstace() {
        if (null == savedHelper) {
            savedHelper = new SavedHelper();
        }
        return savedHelper;
    }

    public SavedHelper() {
        dbManager = DBHelper.shareInstance();
    }

    public void saveOrUpdate(Saved saved) {

        try {
            dbManager.saveOrUpdate(saved);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(Saved saved) {
        boolean success = false;
        try {
            dbManager.delete(saved);
            success = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<Saved> all() {
        List<Saved> allSaveds = null;

        try {
            allSaveds = dbManager.selector(Saved.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return allSaveds;
    }

    @Override
    public void close() {
        try {
            dbManager.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

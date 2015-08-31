package com.lukrodrigotrejo.autocor.db.manager;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.lukrodrigotrejo.autocor.db.greendao.DaoMaster;
import com.lukrodrigotrejo.autocor.db.greendao.DaoSession;

/**
 * Created by Luca Rodrigo Trejo on 29/08/2015.
 */
public class Global extends Application{
    private static DaoSession daoSession;
    private static SQLiteDatabase db;
    private static Properties soap;
    InputStream input = null;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dbautocor_android", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public static void setDaoSession(DaoSession daoSession) { Global.daoSession = daoSession; }

    public static DaoSession getDaoSession() { return daoSession; }

    public static void setDb(SQLiteDatabase db) { Global.db = db; }

    public static SQLiteDatabase getDb() { return db; }

    public static void setSoap(Properties soap) { Global.soap = soap; }

    public static Properties getSoap() { return soap; }
}



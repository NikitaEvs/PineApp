package dev.orlyata.pinebobr;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate(){
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "values-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}

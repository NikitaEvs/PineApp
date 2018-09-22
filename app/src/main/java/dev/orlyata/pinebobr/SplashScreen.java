package dev.orlyata.pinebobr;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    SocketThread socketThread = new SocketThread(getApplication(), this);
    List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        final DaoSession daoSession = ((App) getApplication()).getDaoSession();

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();;
                String status = bundle.getString("Status");
                Log.d("Socket", "Status auth: "+ status);
                try{
                    if(status.equals("success")){
                        socketThread.kill();
                        toDay();
                    } else  {
                        socketThread.kill();
                        daoSession.delete(users.get(users.size()-1));
                        toSign();
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        };

        /**
         * Для теста
         */
        DaoMaster.dropAllTables(daoSession.getDatabase(), true);
        DaoMaster.createAllTables(daoSession.getDatabase(), true);
        users = daoSession.getUserDao().loadAll();
        if(users.size() == 0){
            toSign();
        } else {
            String login = users.get(users.size()-1).getLogin();
            String password = users.get(users.size()-1).getPassword();
            Authentication authentication = new Authentication(login, password, getApplication());
            Log.d("Socket", "Start connection");
            socketThread.setHandler(handler);
            socketThread.setAuthentication(authentication);
            socketThread.setConnect(true);
            socketThread.execute();
        }
    }

    public void toDay(){
        Intent intent = new Intent(this, DayActivity.class);
        startActivity(intent);
        finish();
    }

    public void toSign(){
        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
        finish();
    }
}

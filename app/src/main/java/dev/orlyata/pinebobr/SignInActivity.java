package dev.orlyata.pinebobr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    /* USED FONTS */
    Typeface SF_regular;
    Typeface SF_light;

    /* USED UI ELEMENTS */
    Button btnBack;
    TextView logText;
    EditText inputLogin;
    EditText inputPass;
    Button btnForgot;
    Button btnLogin;

    /* Server option */
    SocketThread socketThread;
    private final String server = "ws://51.15.97.72:8080";
    private final int timeout = 50000;

    String login;
    String password;

    Handler handler;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = this;

        /* SET FONTS */
        SF_regular = Typeface.createFromAsset(getAssets(),  "fonts/SFUIText-Regular.ttf");
        SF_light = Typeface.createFromAsset(getAssets(), "fonts/SFUIText-Light.ttf");

        configUI();

        /* SET LISTENERS */
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = inputLogin.getText().toString();
                password = inputPass.getText().toString();
                if((!login.equals(""))&&(!password.equals(""))){
                    Authentication authentication = new Authentication(login, password, getApplication());
                    Log.d("Socket", "Start connection");
                    socketThread = new SocketThread(server, timeout, handler, getApplication(), context);
                    socketThread.setAuthentication(authentication);
                    socketThread.setConnect(true);
                    socketThread.execute();
                }

            }
        });
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();;
                String status = bundle.getString("Status");
                try{
                    if(status.equals("success")){
                        updateDatabase();
                        toDayActivity();
                    } else {
                        // TODO auth failed

                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        };
    }

    public void configUI(){
        btnBack = findViewById(R.id.btnCancel_signIn);
        logText = findViewById(R.id.logText_signIn);
        inputLogin = findViewById(R.id.inputLogin_signIn);
        inputPass = findViewById(R.id.inputPass_signIn);
        btnForgot = findViewById(R.id.btnForgot_signIn);
        btnLogin = findViewById(R.id.btnLogIn_signIn);

        btnBack.setTypeface(SF_regular);
        logText.setTypeface(SF_regular);
        inputLogin.setTypeface(SF_light);
        inputPass.setTypeface(SF_light);
        btnForgot.setTypeface(SF_light);
        btnLogin.setTypeface(SF_light);
    }

    public void toDayActivity(){
        Intent intent = new Intent(this, DayActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateDatabase(){
        DaoSession daoSession = ((App) context.getApplicationContext()).getDaoSession();
        User user = new User(login, password);
        daoSession.insert(user);
    }
}

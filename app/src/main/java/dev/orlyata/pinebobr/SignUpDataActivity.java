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

public class SignUpDataActivity extends AppCompatActivity {

    /* USED FONTS */
    Typeface SF_regular;
    Typeface SF_light;

    /* USED UI ELEMENTS */
    Button btnCreate;
    TextView createYourAccount;
    EditText inputEmail;
    EditText inputLogin;
    EditText inputPassword;

    /* Server option */
    SocketThread socketThread;
    private final String server = "ws://104.248.27.91:8080";
    private final int timeout = 50000;

    Context context;
    Handler handler;

    String email;
    String name;
    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_data);
        context = this;

        /* SET FONTS */
        SF_regular = Typeface.createFromAsset(getAssets(),  "fonts/SFUIText-Regular.ttf");
        SF_light = Typeface.createFromAsset(getAssets(), "fonts/SFUIText-Light.ttf");

        configUI();

        Intent intent = getIntent();
        name = intent.getStringExtra("ID");

        /* SET LISTENERS */
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString();
                login = inputLogin.getText().toString();
                password = inputPassword.getText().toString();
                if((!email.equals(""))&&(!login.equals(""))&&(!password.equals(""))){
                    Log.d("Socket", "Sign Up");
                    socketThread = new SocketThread(server, timeout, handler, getApplication());
                    Registration registration = new Registration(name, email, login, password, password);
                    if(registration.generateMsg()){
                        Log.d("Socket", "Generate msg success");
                        Log.d("Socket", "Reg msg: "+registration.getMsgOut());
                        socketThread.setCustomMsgOut(registration.getMsgOut());
                        socketThread.execute();
                    } else {
                        Log.d("Socket", "Generate msg failed");
                        Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    // TODO warning about incorrect data
                }
            }
        });

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();;
                String status = bundle.getString("Status");
                Log.d("Socket", "Reg status: "+status);
                try{
                    if(status.equals("success")){
                        toLogIn();
                    } else {
                        // TODO reg failed
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        };
    }

    public void configUI(){
        btnCreate = findViewById(R.id.btnCreate_signUpData);
        createYourAccount = findViewById(R.id.createYourAccount);
        inputEmail  =findViewById(R.id.inputEmail_signUpData);
        inputLogin = findViewById(R.id.inputLogin_signUpData);
        inputPassword = findViewById(R.id.inputPass_signUpData);

        btnCreate.setTypeface(SF_regular);
        createYourAccount.setTypeface(SF_regular);
        inputEmail.setTypeface(SF_light);
        inputLogin.setTypeface(SF_light);
        inputPassword.setTypeface(SF_light);
    }

    public void toLogIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}

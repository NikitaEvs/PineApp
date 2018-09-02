package dev.orlyata.pinebobr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignActivity extends AppCompatActivity {

    /* USED FONTS */
    Typeface SF_regular;

    /* USED UI ELEMENTS */
    TextView textSignMain;
    Button signUp;
    TextView haveAlready;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        /* SET FONTS */
        SF_regular = Typeface.createFromAsset(getAssets(),  "fonts/SFUIText-Regular.ttf");

        configUI();

        /* SET LISTENERS */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignUp();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogIn();
            }
        });
    }

    public void configUI() {
        textSignMain = findViewById(R.id.textSignMain);
        signUp = findViewById(R.id.btnSignIn_signActivity);
        haveAlready = findViewById(R.id.textHaveAlready);
        logIn = findViewById(R.id.btnLogIn_signActivity);
        textSignMain.setTypeface(SF_regular);
        signUp.setTypeface(SF_regular);
        haveAlready.setTypeface(SF_regular);
        logIn.setTypeface(SF_regular);
    }

    public void toSignUp() {
        Intent intent = new Intent(this, SignUpIdActivity.class);
        startActivity(intent);
    }

    public void toLogIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}

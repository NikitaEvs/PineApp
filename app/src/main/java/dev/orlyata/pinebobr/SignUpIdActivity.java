package dev.orlyata.pinebobr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpIdActivity extends AppCompatActivity {

    /* USED FONTS */
    Typeface SF_regular;
    Typeface SF_light;

    /* USED UI ELEMENTS */
    Button btnNext;
    Button btnCancel;
    TextView pineID;
    TextView enterID;
    EditText inputID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_id);


        /* SET FONTS */
        SF_regular = Typeface.createFromAsset(getAssets(),  "fonts/SFUIText-Regular.ttf");
        SF_light = Typeface.createFromAsset(getAssets(), "fonts/SFUIText-Light.ttf");

        configUI();

        /* SET LISTENERS */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = inputID.getText().toString();
                Log.d("UI", "In: "+in);
                if(!in.equals("")){
                    toSignUpData(in);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void configUI(){
        btnNext = findViewById(R.id.btnNext_signUpId);
        btnCancel = findViewById(R.id.btnCancel_signUpId);
        pineID = findViewById(R.id.pineID);
        enterID = findViewById(R.id.enterID);
        inputID = findViewById(R.id.inputID);
        btnNext.setTypeface(SF_regular);
        btnCancel.setTypeface(SF_regular);
        pineID.setTypeface(SF_regular);
        enterID.setTypeface(SF_light);
        inputID.setTypeface(SF_light);
    }

    public void toSignUpData(String id){
        Intent intent = new Intent(this, SignUpDataActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}

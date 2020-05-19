package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText emaillogin, passwordlogin;
    Button loginactivity, signupactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");
        emaillogin = findViewById(R.id.usernamelogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        loginactivity = findViewById(R.id.loginactivity);
        signupactivity = findViewById(R.id.signupactivity);

        loginactivity.setOnClickListener(Login.this);
        signupactivity.setOnClickListener(Login.this);

        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginactivity:
                if (emaillogin.getText().toString().equals("") || passwordlogin.getText().toString().equals("")) {
                    FancyToast.makeText(Login.this, "Email, Password required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                } else {
                    ParseUser.logInInBackground(emaillogin.getText().toString(), passwordlogin.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(Login.this, user.get("username") + " " + "Signed In", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(Login.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                    passwordlogin.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                                onClick(loginactivity);
                            }
                            return false;
                        }
                    });
                }
                    break;
                    case R.id.signupactivity:
                        break;



        }
    }
    public void layouttapped(View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity(){
        Intent intentSocialActivity = new Intent(Login.this, Social_Media_Activity.class);
        startActivity(intentSocialActivity);
        finish();
    }
}

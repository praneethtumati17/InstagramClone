package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, username, password;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SignUp");
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(MainActivity.this);
        signup.setOnClickListener(MainActivity.this);

        if(ParseUser.getCurrentUser() !=null){
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                if(email.getText().toString().equals("")||username.getText().toString().equals("")||password.getText().toString().equals("")){
                    FancyToast.makeText(MainActivity.this, "Email , Username , Password required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }
                else{
                    final ParseUser appuser = new ParseUser();
                    appuser.setEmail(email.getText().toString());
                    appuser.setUsername(username.getText().toString());
                    appuser.setPassword(password.getText().toString());


                    password.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                                onClick(signup);
                            }
                            return false;
                        }
                    });
                    final ProgressDialog progressDialog=new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up"+username.getText().toString());
                    progressDialog.show();
                    appuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appuser.get("username") + " Registered Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

                break;
            case  R.id.login:
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;

        }
    }
    public void rootlayouttapped(View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity(){
        Intent intentSocialActivity = new Intent(MainActivity.this, Social_Media_Activity.class);
        startActivity(intentSocialActivity);
        finish();
    }
}

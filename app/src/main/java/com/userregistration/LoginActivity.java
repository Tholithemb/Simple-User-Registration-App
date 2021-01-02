package com.userregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity";
    Button signUp,createAccount;
    String shareEmail,sharePassword;
    TextInputLayout email,password;
    SharedPreferences sharedPreferences;

    // regular expression for password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //
        sharedPreferences =getSharedPreferences("myPref",0);

        //getting the shared preference strings and storing to String

        shareEmail =sharedPreferences.getString("Email",null);
        sharePassword =sharedPreferences.getString("Password",null);

        email = findViewById(R.id.email);
        password =findViewById(R.id.password);
        signUp =findViewById(R.id.signup);
        createAccount =findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!validateEmail() | !validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }catch (Exception exception){
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //password and email validation

    private boolean validateEmail() {
        String textEmail = email.getEditText().getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            email.setError("Enter a valid email!");
            return false;
        }
        else if (!textEmail.equalsIgnoreCase(shareEmail)){
            email.setError("Email does not match");
            return false;
        }
        else if (textEmail.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else {
            email.setErrorEnabled(false);
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String textPassword = password.getEditText().getText().toString().trim();

        if (textPassword.isEmpty()){
            password.setError("Field cannot be empty ");
            return false;
        }
        else if (!textPassword.equals(sharePassword)){
            password.setError("Password does not match");
            return false;
        }
        else {
            password.setErrorEnabled(false);
            password.setError(null);
            return true;
        }
    }


    //if the user has already signed let start MainActivity

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (sharedPreferences.getBoolean("hasRegistered",false)) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
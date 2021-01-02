package com.userregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG ="register" ;
    Button register,login;
    private TextInputLayout username,email,password,confirmPassword;
    SharedPreferences sharedPreferences;
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register);
        login =findViewById(R.id.login);
        username =findViewById(R.id.username);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        confirmPassword =findViewById(R.id.confirm_password);

        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validateEmail()| !validatePassword()){
                    Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(RegisterActivity.this, "You have Successfully log in", Toast.LENGTH_SHORT).show();
                saveData();
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateUsername(){
        String textUsername =username.getEditText().getText().toString().trim();
        if (textUsername.isEmpty()){
            username.setError("Field cannot be empty!");
            return false;
        }
        else if (textUsername.length()>20 || textUsername.length()<8){
            username.setError("username should be between 8-20 characters");
            return false;
        }else {
            username.setErrorEnabled(false);
            username.setError(null);
            return true;
        }

    }
    private boolean validateEmail() {
        String textEmail = email.getEditText().getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            email.setError("Enter a valid email!");
            return false;
        } else if (textEmail.isEmpty()) {
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
        String cpassword = confirmPassword.getEditText().getText().toString().trim();

        if (textPassword.isEmpty() && cpassword.isEmpty()){
            password.setError("Field cannot be empty ");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(textPassword).matches()){
            password.setError("Password too weak");
            return false;
        }
        else if (!textPassword.equals(cpassword)){
            confirmPassword.setError("Password does not match");
            return false;
        }
        else {
            password.setErrorEnabled(false);
            password.setError(null);
            return true;
        }
    }
    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username",username.getEditText().getText().toString());
        editor.putString("Email",email.getEditText().getText().toString());
        editor.putString("Password",password.getEditText().getText().toString());
        editor.putBoolean("hasRegistered",true);
        editor.apply();
    }
}
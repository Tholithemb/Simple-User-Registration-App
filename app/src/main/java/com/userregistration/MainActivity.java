package com.userregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView username,email;
    Button isRegistered,removeAccount;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =findViewById(R.id.username);
        email =findViewById(R.id.email);
        isRegistered =findViewById(R.id.isRegistered);
        removeAccount =findViewById(R.id.removeAccount);


        sharedPreferences =getSharedPreferences("myPref",0);
        username.setText(sharedPreferences.getString("Username",null));
        email.setText(sharedPreferences.getString("Email",null));
        removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
    }
    public void clearData(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    // close app if user back press, to prevent user from going back to register or login activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    //Visibility of button if the user has registered it will not be visible
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences =getSharedPreferences("myPref",0);
        if (sharedPreferences.getBoolean("hasRegistered",false)){
            isRegistered.setVisibility(View.GONE);
        }
    }
}
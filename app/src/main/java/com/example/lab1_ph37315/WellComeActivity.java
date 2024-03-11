package com.example.lab1_ph37315;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WellComeActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },3000);
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){ // chưa login
            Intent intent = new Intent(this,LoginActivity.class);

            startActivity(intent);
        }else {
            // đã login
            Intent intent = new Intent(this,MainActivity.class);
            String emai = user.getPhoneNumber();
            intent.putExtra("nameUser",user.getEmail().isEmpty()?user.getPhoneNumber():user.getEmail());
            startActivity(intent);
        }
    }
}
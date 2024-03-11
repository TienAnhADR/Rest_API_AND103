package com.example.lab1_ph37315;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginNumberPhoneActivity extends AppCompatActivity {
    private TextView edtPhoneNum,edtOTP;
    private Button btnGetOTP,btnLoginPhone;
    private FirebaseAuth mAuth;
    private String mVerificationID;
    PhoneAuthCredential credential;
    String code ;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_number_phone);
        edtOTP = findViewById(R.id.edtOTP);
        edtPhoneNum = findViewById(R.id.edtNumberPhone);
        btnLoginPhone = findViewById(R.id.btnLOGIN);
        btnGetOTP = findViewById(R.id.btnGetOTP);
        mAuth = FirebaseAuth.getInstance();

        btnGetOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String numberPhone = edtPhoneNum.getText().toString().trim();
                if(numberPhone.isEmpty()){
                    Toast.makeText(LoginNumberPhoneActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                getOTP(numberPhone);
            }
        });
        btnLoginPhone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                code = edtOTP.getText().toString().trim();
                credential = PhoneAuthProvider.getCredential(mVerificationID, code);
               signInWithPhoneAuthCredential(credential);


            }
        });
        // hàm tu dong duoc goi khi chung ta gui yeu cau lay OTP

    }
    private void getOTP(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w(TAG, "onVerificationFailed", e);
                                Toast.makeText(LoginNumberPhoneActivity.this, "Dang nhap fail", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                mVerificationID = s;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = task.getResult().getUser();
                            Intent  intent =new Intent(LoginNumberPhoneActivity.this,MainActivity.class);
                            intent.putExtra("nameUser",user.getPhoneNumber());
                            startActivity(intent);
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(LoginNumberPhoneActivity.this, "Ma xac minh khong hop le", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
}
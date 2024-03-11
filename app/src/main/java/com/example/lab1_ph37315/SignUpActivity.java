package com.example.lab1_ph37315;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUP;
    private EditText edtEmail,edtPass,edtPass2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUP = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPass = findViewById(R.id.edtPassSignUp);
        edtPass2 = findViewById(R.id.edtPassSignUp2);
        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                String pass = edtPass2.getText().toString().trim();
                if(email.isEmpty()||pass.isEmpty()||password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<6){
                    Toast.makeText(SignUpActivity.this, "Mat khau phai it nhat 6 ky tu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass.equals(password)){
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(email,password);

            }
        });
    }

    private void signUp(String email,String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent  intent = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Đăng ký fall", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
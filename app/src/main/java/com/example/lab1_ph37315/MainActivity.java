package com.example.lab1_ph37315;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab1_ph37315.Adapter.TinNhanAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private String userName ="";
    private Button btnLogout,btnGuiTN;
    private EditText edtTinNhan;
    private FirebaseFirestore db;
    private RecyclerView rcvListMess;
    private ArrayList<TinNhan> list;
    private TinNhanAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tvKQ = findViewById(R.id.tvKQ);
        Intent intent = getIntent();
        btnGuiTN = findViewById(R.id.btnGuiTN);
        edtTinNhan = findViewById(R.id.edtTinNhan);

        userName = intent.getStringExtra("nameUser");
        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();
        rcvListMess = findViewById(R.id.recv_TinNhan);
        rcvListMess.setLayoutManager(new LinearLayoutManager(this));
        list = getListMess();
        adapter = new TinNhanAdapter(this,list);
        rcvListMess.setAdapter(adapter);
        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();

            }
        });
        btnGuiTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String user = intent.getStringExtra("nameUser");
                String tinNhan = edtTinNhan.getText().toString().trim();
                if(tinNhan.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui long nhap tin nhan", Toast.LENGTH_SHORT).show();
                    return;
                }
                insert(tinNhan,user);
            }
        });
//        insert();
    }
    void insert(String content,String user){
        String id = UUID.randomUUID().toString();
        TinNhan tn = new TinNhan(user,content,id);

        db.collection("MESS")
                .add(tn.convertTinNhan())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId());



                        Toast.makeText(MainActivity.this, "Gui thanh cong", Toast.LENGTH_SHORT).show();
                        edtTinNhan.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error adding document", e);
                    }
                });

        list = getListMess();
        adapter.notifyDataSetChanged();
    }

    ArrayList<TinNhan> getListMess(){
        ArrayList<TinNhan> list1 = new ArrayList<>();
        db.collection("MESS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TinNhan tinNhan = document.toObject(TinNhan.class);

                                list1.add(tinNhan);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return list1;
    }
}
package com.daihgph36944.and103_lab1;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    TextView tvKQ;
    FirebaseFirestore database;
    Context context= this;
    String strKQ="";
    ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvKQ = findViewById(R.id.tvKQ);
        database = FirebaseFirestore.getInstance(); // Khoi tao
//        insert();
//        update();
//        delete();
        select();
    }

    void insert(){
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title 12", "conten 12"); // tao doi tuong moi de insert
        database.collection("TODO")
                .document(id)
                .set(toDo.convertToHashMap()) //dua du lieu vao dong
                .addOnSuccessListener(new OnSuccessListener<Void>() { // thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Inssert thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Inssert that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void update(){
        String id="5e5ccc7c-4652-464c-af0b-e719271822aa"; // copy id vao day
        toDo = new ToDo(id, "Title 11 Update", "Content 11 Update"); // noi dung can update
        database.collection("TODO")//lay bang du lieu
                .document(id)//lay id
                .update(toDo.convertToHashMap())//thuc hien update
                .addOnSuccessListener(new OnSuccessListener<Void>() {// update thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {// update that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete() {
        String id = "5e5ccc7c-4652-464c-af0b-e719271822aa";
        database.collection("TODO")//truy cap vao bang du lieu
                .document(id)//truy cap vao id
                .delete()//thuc hien xoa
                .addOnSuccessListener(new OnSuccessListener<Void>() {// xoa thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {// xoa that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")// truy cap bang du lieu
                .get()// lay ve du lieu
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            strKQ = "";
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                ToDo t = doc.toObject(ToDo.class); // chuyen du lieu doc duoc sang ToDo
                                list.add(t);
                                strKQ += "id: "+t.getId()+"\n";
                                strKQ += "title: "+t.getTitle()+"\n";
                                strKQ += "content: "+t.getContent()+"\n";

                            }
                            tvKQ.setText(strKQ);
                        } else {
                            Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return list;
    }
}
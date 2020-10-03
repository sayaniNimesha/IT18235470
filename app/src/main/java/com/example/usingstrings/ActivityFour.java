package com.example.usingstrings;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityFour extends AppCompatActivity {

    Button button;
    TextView question1,answer1;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        button = findViewById(R.id.update);
        question1 = (TextView)findViewById(R.id.updateq);
        answer1 = (TextView)findViewById(R.id.updatea);

        reference =FirebaseDatabase.getInstance().getReference().child("latestnews");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String q1 = dataSnapshot.child("q1").getValue().toString();
                String a1 = dataSnapshot.child("a1").getValue().toString();

                question1.setText(q1);
                answer1.setText(a1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference().child("latestnews");
               String q1,a1;



               q1 = question1.getText().toString();
               a1 = answer1.getText().toString();

                addNews addnews = new addNews(q1,a1);

                reference.setValue(addnews);
                Toast.makeText(ActivityFour.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityFour.this,ActivityTwo.class);
                ActivityFour.this.startActivity(intent);
                ActivityFour.this.finish();
            }
        });


    }
}
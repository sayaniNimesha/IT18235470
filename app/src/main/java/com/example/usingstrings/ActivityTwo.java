package com.example.usingstrings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityTwo extends AppCompatActivity {

    TextView question1,answer1,t11;
    Button btn,rating;
    RatingBar ratingBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        question1 = (TextView) findViewById(R.id.question1);
        answer1 = (TextView) findViewById(R.id.answer1);

        ratingBar = findViewById(R.id.rating_bar);
        rating = findViewById(R.id.rate);

        reference = FirebaseDatabase.getInstance().getReference().child("latestnews");
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
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(),"rating " + s+" Star",Toast.LENGTH_SHORT).show();
            }
        });

    }
}







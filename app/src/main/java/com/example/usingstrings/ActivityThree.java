package com.example.usingstrings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityThree extends AppCompatActivity {

    Button button2,button3;
    TextView question1,answer1;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);


        button2 = findViewById(R.id.addNews);
        button3 = findViewById(R.id.update);
        question1 = findViewById(R.id.q1);
        answer1 = findViewById(R.id.a2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("latestnews");

                //get values

                String q1 = question1.getText().toString();
                String a1 = answer1.getText().toString();



                if (TextUtils.isEmpty(question1.getText().toString())){
                    Toast.makeText(ActivityThree.this,"no empty details allowed",Toast.LENGTH_SHORT).show();
                }else{


                    addNews addnews = new addNews(q1,a1);


                    reference.setValue(addnews);
                    Toast.makeText(ActivityThree.this,"your question is successfully added",Toast.LENGTH_SHORT).show();

                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityThree.this,ActivityFour.class);
                startActivity(intent);
            }
        });
    }
}
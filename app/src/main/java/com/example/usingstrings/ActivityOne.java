package com.example.usingstrings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

public class ActivityOne extends AppCompatActivity implements View.OnClickListener{

    private TextView question,qCount,timer;
    Button option1,option2,option3,option4;
    private List<Question> questionList;
    ArrayAdapter <Question> adapter;
   private int quesNum;
   private CountDownTimer countDown ;
   private int score;

   Question que;
   FirebaseDatabase database;
   DatabaseReference ref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        
        question = findViewById(R.id.text_view_question);
        qCount = findViewById(R.id.text_view_countdown);
        timer = findViewById(R.id.countdown);
        
        option1 = findViewById(R.id.button2);
        option2 = findViewById(R.id.button3);
        option3 = findViewById(R.id.button4);
        option4 = findViewById(R.id.button5);
        
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        que = new Question();



        getQuestionList();
        score = 0;
        
    }

    private void getQuestionList() {
        questionList = new ArrayList<>();

        questionList.add(new Question("Which keyword keeps the main method from returning any value to the caller?","exclusive","concealed","restricted","void",4));
        questionList.add(new Question("Which of those allows duplicate elements?","Set","List","All","None of the above",2));
        questionList.add(new Question("Which of the following individuals is credited for first designing Java?","Jim LeValley","Tim Ritchey","James Gosling","Ian Sheeler",3));
        questionList.add(new Question("At this point in time, which of the following is the officially supported version of Java?","Java 6","Java 8","Java 6.5","Java 7",2));
        questionList.add(new Question("Which company now owns Java?","Apache Software Foundation","SAP","Oracle Corporation","PeopleSoft Inc.",3));

        setQuestion();
    }

    private void setQuestion() {
        timer.setText(String.valueOf(10));
        
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        
        qCount.setText(String.valueOf(1)+"/"+String.valueOf(questionList.size()));
        
        startTimer();
        
        quesNum = 0;
    }

    private void startTimer() {

         countDown = new CountDownTimer(12000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <10000)
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                    changeQuestion();
            }
        };
        countDown.start();
    }

    @Override
    public void onClick(View v) {
        
        int selectedOption =0;
        
        switch (v.getId())
        {
            case R.id.button2:
                selectedOption =1;
                break;

            case R.id.button3:
                selectedOption =2;
                break;

            case R.id.button4:
                selectedOption =3;
                break;
                
            case R.id.button5:
                selectedOption =4;
                break;
                
            default:
                
            
        }

        countDown.cancel();
        checkAnswer(selectedOption,v);
    }
    



    private void checkAnswer(int selectedOption, View view){

        if (selectedOption == questionList.get(quesNum).getCorrectAns())
        {
            //right answer
            Toast.makeText(ActivityOne.this, "Answer is correct", Toast.LENGTH_SHORT).show();
            score++;

        }else{
            //view answer
            Toast.makeText(ActivityOne.this, "Answer is incorrect", Toast.LENGTH_SHORT).show();

        }
        changeQuestion();

    }

    private void changeQuestion() {
        if (quesNum < questionList.size()-1){


            quesNum++;
            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            qCount.setText(String.valueOf(quesNum+1)+"/"+ String.valueOf(questionList.size()));

            timer.setText(String.valueOf(10));

            startTimer();


        }else{
            //go to score activity
            Intent intent = new Intent(ActivityOne.this,ScoreActivity.class);
            intent.putExtra("SCORE",String.valueOf(score)+"/" + String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            ActivityOne.this.finish();
        }
    }

    private void playAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).
                setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            @SuppressLint("NewApi")
            public void onAnimationEnd(Animator animator) {
                        if (value==0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionD());
                                    break;

                            }

                            playAnim(view,1,viewNum);
                        }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();
    }
}
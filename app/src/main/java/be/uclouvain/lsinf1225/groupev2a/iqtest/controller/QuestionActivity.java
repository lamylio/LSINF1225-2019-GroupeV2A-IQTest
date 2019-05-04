package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;

public class QuestionActivity extends AppCompatActivity {
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        suivant = findViewById(R.id.pg_suivante);
        choice1=findViewById(R.id.rep1);
        choice2=findViewById(R.id.rep2);
        choice3=findViewById(R.id.rep3);
        choice4=findViewById(R.id.rep4);

    }

    public void checkAnswer(View view){

    }
}
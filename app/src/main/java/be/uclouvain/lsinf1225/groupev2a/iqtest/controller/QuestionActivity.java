package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class QuestionActivity extends AppCompatActivity {
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button suivant;
    int index = 0;
    TextView time = findViewById(R.id.quesion_timer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

    }

    CountDownTimer Timer = new CountDownTimer(600000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            time.setText("Temps restant : " + millisUntilFinished/1000);
        }
        @Override
        public void onFinish() {

        }
    }.start();

    private void checkAnswer(){
        String s_id = getIntent().getStringExtra("button");
        if(s_id == null) {
            Utils.changeActivity(getApplicationContext(), GameActivity.class);
            Utils.gimmeToast(getApplicationContext(), "Erreur - Aucun bouton choisi */");
            finish();
            return;
        }
        suivant=findViewById(R.id.pg_suivante);
        choice1=findViewById(R.id.question_rep1);
        choice2=findViewById(R.id.question_rep2);
        choice3=findViewById(R.id.question_rep3);
        choice4=findViewById(R.id.question_rep4);


    }

    private void nextQuestion(){
        index ++;
    }

    @Override
    public void onBackPressed() {
        Utils.gimmeToast(getApplicationContext(), getText(R.string.CANNOT_BACK).toString());
    }
}
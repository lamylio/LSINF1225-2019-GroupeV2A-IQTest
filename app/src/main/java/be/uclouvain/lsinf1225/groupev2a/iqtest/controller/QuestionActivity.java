package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
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
    TextView time = findViewById(R.id.question_timer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        checkAnswer();

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
        choice1=findViewById(R.id.rep1);
        choice2=findViewById(R.id.rep2);
        choice3=findViewById(R.id.rep3);
        choice4=findViewById(R.id.rep4);


    }

    @Override
    public void onBackPressed() {
        View v = findViewById(R.id.activity_choosemode);
        /* Don't ask me why its unequals but its working lol */
        if(getCurrentFocus() != v) {
            Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
            finish();
        }else{
            setContentView(R.layout.activity_choosemode);
        }
    }
}
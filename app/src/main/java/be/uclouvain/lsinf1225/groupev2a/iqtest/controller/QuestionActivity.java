package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Answer;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class QuestionActivity extends AppCompatActivity {
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button next;

    TextView time;
    TextView questionStatement;


    Iterator<Question> iterator;
    Question question;
    Answer[] answers;

    CountDownTimer timer;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        time  = findViewById(R.id.question_timer);
        questionStatement = findViewById(R.id.question_statement);

        choice1 = findViewById(R.id.question_ans_1);
        choice2 = findViewById(R.id.question_ans_2);
        choice3 = findViewById(R.id.question_ans_4);
        choice4 = findViewById(R.id.question_ans_3);

        if(User.loggedUser == null || GameActivity.answersTable == null) Utils.changeActivity(getApplicationContext(), MainActivity.class);
    }

    @Override
    protected void onStart(){
        super.onStart();
        iterator = GameActivity.answersTable.keySet().iterator();
        showQuestion();
    }

    /* ----- RUNNING GAME ----- */

    private void showQuestion(){
        if(!iterator.hasNext()) throw new Error("showQuestion called but iterator has not more element.");

        question = iterator.next();
        answers = GameActivity.answersTable.get(question);
        Utils.sendLog(this.getClass(), question.getStatement() + "(" + question.getQuest_id() + ")");

        if(answers.length != 4) throw new Error("Erreur récupération des réponses");

        questionStatement.setText(question.getStatement());
        choice1.setText(answers[0].getAnswer());
        choice2.setText(answers[1].getAnswer());
        choice3.setText(answers[2].getAnswer());
        choice4.setText(answers[3].getAnswer());

        resetCountdown();
    }

    public void checkAnswers(View view){
        int i = -1;
        if(view != null){
            if(view.getId() == R.id.question_ans_1) i = 0;
            if(view.getId() == R.id.question_ans_2) i = 1;
            if(view.getId() == R.id.question_ans_3) i = 2;
            if(view.getId() == R.id.question_ans_4) i = 3;
        }
        int ans_id = i != -1 ? answers[i].getAns_id() : i;
        saveAnswer(ans_id);

        if(!iterator.hasNext()) {
            showEndingResults();
            return;
        }else {
            Utils.sendLog(this.getClass(), "----- Next question -----");
            showQuestion();
            return;
        }

    }

    /* ----- ENDING GAME ----- */

    private void showEndingResults(){
        /* TODO */
        Utils.sendLog(this.getClass(), "----- No more elements - show results (TODO) -----");
        Utils.gimmeToast(getApplicationContext(), "Test terminé. Afficher les résultats");
        finishGame();
        return;
    }

    private void finishGame(){
        timer.cancel();
        GameActivity.answersTable = null;
        GameActivity.game = null;
        Utils.changeActivity(getApplicationContext(), UserActivity.class);
        finish();
    }

    /* ----- UTILS ----- */

    private void resetCountdown(){
        if(timer != null) timer.cancel();
        timer =  new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                Utils.gimmeToast(getApplicationContext(), getText(R.string.TIMEOUT).toString());
                checkAnswers(null);
            }
        }.start();
    }

    private void saveAnswer(final int ans_id){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper.INSTANCE.resultDao().updateResult(GameActivity.game.getGame_id(), question.getQuest_id(), ans_id);
                Utils.sendLog(this.getClass(), "- quest_id : " + question.getQuest_id() + " / ans_id : " + ans_id);
            }


        });
        t.start(); try {t.join();}catch (InterruptedException e){Log.e("IQW/QuestionActivity",  e.getMessage());}
    }

    /* ----- ----- */

    boolean pressed = false;
    @Override
    public void onBackPressed() {
        /* Ask for confirmation, in case of missclick */
        if(!pressed){
            Utils.gimmeToast(getApplicationContext(), getText(R.string.CANNOT_BACK).toString());
            pressed = true;
        }else{
            /*  Give a penality (-1) */
            saveAnswer(-1);
            finishGame();
        }
    }
}
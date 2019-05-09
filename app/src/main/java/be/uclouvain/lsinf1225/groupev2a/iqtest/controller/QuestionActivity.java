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

    Iterator<Question> iterator;
    Question question;
    Answer[] answers;

    Button[] choices = new Button[4];
    Button next;

    TextView time;
    TextView questionStatement;

    TextView score;

    CountDownTimer timer;
    int index = 0;

    boolean finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        time  = findViewById(R.id.question_timer);
        questionStatement = findViewById(R.id.question_statement);

        choices[0] = findViewById(R.id.question_ans_1);
        choices[1] = findViewById(R.id.question_ans_2);
        choices[2] = findViewById(R.id.question_ans_3);
        choices[3] = findViewById(R.id.question_ans_4);

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
        choices[0].setText(answers[0].getAnswer());
        choices[1].setText(answers[1].getAnswer());
        choices[2].setText(answers[2].getAnswer());
        choices[3].setText(answers[3].getAnswer());

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
            timer.cancel();
            finished = true;
            Utils.sendLog(this.getClass(), "----- QUIZZ END -----");
            Utils.gimmeToast(getApplicationContext(), "Test terminé.");
            showEndingResults();
            return;
        }else {
            Utils.sendLog(this.getClass(), "----- NEXT QUESTION -----");
            showQuestion();
            return;
        }

    }

    /* ----- ENDING GAME ----- */

    private void showEndingResults(){
        /* TODO */
        setContentView(R.layout.activity_showresults);
        score = findViewById(R.id.results_score);

        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Si on veut avoir les réponses détaillées une par une :)

                Answer[] answers = DatabaseHelper.INSTANCE.answerDao().getAnswersFromGame(GameActivity.game.getGame_id());
                if(answers == null) throw new Error("Erreur récupération des answers d'une game");
                int i = 0;
                for (Answer answer : answers) {
                    if(answer.isCorrect()) i++;
                    Utils.sendLog(this.getClass(), answer.getQuest_id() + " / " + answer.getAns_id() + " | " + answer.getAnswer() + " : " + answer.isCorrect());
                }
                */

                DatabaseHelper.INSTANCE.gameDao().updateGameEndTime(GameActivity.game.getGame_id(), Utils.dateFormated());
                int correct = DatabaseHelper.INSTANCE.answerDao().howManyCorrectAnswersFromGame(GameActivity.game.getGame_id());
                score = findViewById(R.id.results_score);
                score.setText("Vous avez " + correct + " bonne(s) réponse(s)");
                score.refreshDrawableState();
            }
        }).start();

        return;
    }

    private void finishGame(){
        GameActivity.answersTable = null;
        GameActivity.game = null;
        Utils.changeActivity(getApplicationContext(), UserActivity.class);
        finish();
    }

    /* ----- UTILS ----- */

    private void resetCountdown(){
        if(timer != null) timer.cancel();
        timer =  new CountDownTimer(60*1000, 1000) {
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
                Utils.sendLog(this.getClass(), "quest_id : " + question.getQuest_id() + " / ans_id : " + ans_id);
            }


        });
        t.start(); try {t.join();}catch (InterruptedException e){Log.e("IQW/QuestionActivity",  e.getMessage());}
    }

    /* ----- ----- */

    boolean pressed = false;
    @Override
    public void onBackPressed() {
        if (!finished) {
            /* Ask for confirmation, in case of missclick */
            if (!pressed) {
                Utils.gimmeToast(getApplicationContext(), getText(R.string.CANNOT_BACK).toString());
                pressed = true;
            } else {
                /*  Give a penality (-1) */
                saveAnswer(-1);
                finishGame();
            }
        }else {finishGame();}
    }
}
package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
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
    TextView questionNumber;

    TextView score;

    CountDownTimer timer;
    int index = 0;
    int nbQuestions = 0;

    boolean finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        time  = findViewById(R.id.question_timer);
        questionStatement = findViewById(R.id.question_statement);
        questionNumber = findViewById(R.id.question_number);

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

        index++;
        question = iterator.next();
        answers = GameActivity.answersTable.get(question);
        Utils.sendLog(this.getClass(), question.getStatement() + "(" + question.getQuest_id() + ")");

        if(answers.length != 4) throw new Error("Erreur récupération des réponses");

        questionStatement.setText(question.getStatement());
        choices[0].setText(answers[0].getAnswer());
        choices[1].setText(answers[1].getAnswer());
        choices[2].setText(answers[2].getAnswer());
        choices[3].setText(answers[3].getAnswer());

        questionNumber.setText(index + " / " + GameActivity.answersTable.size());

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
        timer.cancel();
        setContentView(R.layout.activity_showresults);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Answer[] gameAnswers = DatabaseHelper.INSTANCE.answerDao().getAnswersFromGame(GameActivity.game.getGame_id());
                if(gameAnswers == null) throw new Error("Erreur récupération des answers d'une game");

                int i = 0;
                String textResult = "";
                for(Answer ans : gameAnswers){
                    if(ans.isCorrect()) i++;
                    Utils.sendLog(this.getClass(), ans.getAnswer() + " : " + ans.getQuest_id());

                    Question quest = DatabaseHelper.INSTANCE.questDao().getQuestionFromID(ans.getQuest_id());
                    textResult += Html.fromHtml(quest.getStatement() + " : " + ans.getAnswer() + " <b>(" + (ans.isCorrect() ?  "✓" : "✕") + ")</b> <br/><br/>");

                }
                textResult += Html.fromHtml("<center><b> Ce qui donne un résultat de "+ i + " bonne(s) réponse(s)</b></center>");
                final String output = textResult;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        score = findViewById(R.id.results_score);
                        score.setText(output);
                    }
                });
            }
        }).start();
        int i = 0;


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
                DatabaseHelper.INSTANCE.gameDao().updateGameEndTime(GameActivity.game.getGame_id(), Utils.dateFormated());
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
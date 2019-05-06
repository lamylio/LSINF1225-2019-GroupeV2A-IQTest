package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;

public class QuestionActivity extends AppCompatActivity {
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button next;

    TextView time;
    TextView questionStatement;

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

        showQuestion();
    }

    Iterator<Question> iterator = GameActivity.answersTable.keySet().iterator();
    Question question;
    Answer[] answers;

    private void showQuestion(){
        question = iterator.next();
        answers = GameActivity.answersTable.get(question);

        if(answers.length != 4) throw new Error("Erreur récupération des réponses");

        questionStatement.setText(question.getStatement());
        choice1.setText(answers[0].getAnswer());
        choice2.setText(answers[1].getAnswer());
        choice3.setText(answers[2].getAnswer());
        choice4.setText(answers[3].getAnswer());

        timer =  new CountDownTimer(5*1000, 1000) {
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

    public void checkAnswers(final View view){
        timer.cancel();

        new Runnable(){
            @Override
            public void run() {
                int i = -1;
                if(view != null){
                    if(view.getId() == R.id.question_ans_1) i = 0;
                    if(view.getId() == R.id.question_ans_2) i = 1;
                    if(view.getId() == R.id.question_ans_3) i = 2;
                    if(view.getId() == R.id.question_ans_4) i = 3;
                }
                DatabaseHelper.INSTANCE.resultDao().updateResult(new Result(GameActivity.game.getGame_id(), question.getQuest_id(), i != -1 ? answers[i].getAns_id() : null));
                for (Result result : DatabaseHelper.INSTANCE.resultDao().getAllResultFromGame(GameActivity.game.getGame_id())) {
                    Utils.sendLog(this.getClass(), result.getGame_id() + " " + result.getQuest_id() + result.getAns_id());
                }
            }
        };

        if(!iterator.hasNext()) {
            Utils.sendLog(this.getClass(), "No more elements - show results (TODO)");
            showEndingResults();
            return;
        }
        else showQuestion();
    }

    private void showEndingResults(){
        /* TODO */
        Utils.gimmeToast(getApplicationContext(), "Test terminé. Afficher les résultats (à faire)");
        return;
    }

    boolean pressed = false;
    @Override
    public void onBackPressed() {
        /* Ask for confirmation, in case of missclick */
        if(!pressed){
            Utils.gimmeToast(getApplicationContext(), getText(R.string.CANNOT_BACK).toString());
            pressed = true;
        }else{
            /* Remove the game */
            GameActivity.answersTable = null;
            GameActivity.game = null;
            Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
            finish();
        }

    }
}
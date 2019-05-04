package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.widget.Button;

        import androidx.appcompat.app.AppCompatActivity;

        import be.uclouvain.lsinf1225.groupev2a.iqtest.R;

public class QuestionActivity extends AppCompatActivity {
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    CountDownTimer timer = new CountDownTimer() {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }


}
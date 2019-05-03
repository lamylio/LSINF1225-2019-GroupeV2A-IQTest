package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
    }
    public void onClickCategoriesButton(View view) {
        setContentView(R.layout.activity_category);
    }
    public void onClickNormalButton(View view) {
        setContentView(R.layout.activity_normal);
    }
    public void onClickRapideButton(View view) {
        setContentView(R.layout.activity_rapide);
    }
}

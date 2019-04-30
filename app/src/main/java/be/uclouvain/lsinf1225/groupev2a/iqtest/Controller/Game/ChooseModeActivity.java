package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller.Game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
    }

    public void onClickCategoriesButton(View view){
        Utils.changeActivity(getApplicationContext(), CategoryActivity.class);
        finish();
    }
}

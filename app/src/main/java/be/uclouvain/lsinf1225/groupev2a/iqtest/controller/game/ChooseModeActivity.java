package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.ProfileActivity;

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

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
        finish();
    }
}


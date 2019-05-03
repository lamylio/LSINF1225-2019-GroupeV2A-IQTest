package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.ProfileActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
        finish();
    }


}



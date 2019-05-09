package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.UserActivity;

public class HistoryActivity extends AppCompatActivity {

    TableRow[] rows = new TableRow[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rows[0] = findViewById(R.id.history_row1);
        rows[1] = findViewById(R.id.history_row2);
        rows[2] = findViewById(R.id.history_row3);
        rows[3] = findViewById(R.id.history_row4);
        rows[4] = findViewById(R.id.history_row5);

        updateUI();
    }

    private void updateUI(){

        for (TableRow row : rows) {

            for(int i = 1; i < 6; i++){
                TextView text = new TextView(this, null);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                text.setTextColor(getResources().getColor(R.color.white));
                text.setLayoutParams(params);
                text.setGravity(Gravity.CENTER);
                text.setText(""+i);
                row.addView(text);
            }
        }

    }




    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), UserActivity.class);
        finish();
    }
}
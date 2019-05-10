package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.UserActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class HistoryActivity extends AppCompatActivity {

    TableLayout table;
    TableRow[] rows;
    Game[] games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        table = findViewById(R.id.history_table);
        updateUI(null, 100);
    }

    private Activity getActivity(){return this;}

    private void updateUI(final String type, final int limit){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(type == null) games = DatabaseHelper.INSTANCE.gameDao().findLastGamesByPlayer(User.loggedUser.getUsername(), limit);
                else games = DatabaseHelper.INSTANCE.gameDao().findLastGamesByPlayerAndType(User.loggedUser.getUsername(), type, limit);

                rows = new TableRow[games.length];
                Utils.sendLog(this.getClass(), games.length+" games");
                for (int i = 0; i < games.length; i++) {

                    double score = DatabaseHelper.INSTANCE.answerDao().howManyCorrectAnswersFromGame(games[i].getGame_id());
                    double score_max = DatabaseHelper.INSTANCE.answerDao().getAnswersFromGame(games[i].getGame_id()).length;

                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    row.setPadding(0, 10, 0, 10);
                    rows[i] = row;

                    TextView[] columns = new TextView[3];
                    for (int j = 0; j < columns.length; j++) {
                        TextView text = new TextView(getActivity());
                        text.setTextColor(getResources().getColor(R.color.white));
                        text.setTextSize(18);
                        text.setGravity(Gravity.CENTER);
                        columns[j] = text;
                        row.addView(text);
                        Utils.sendLog(this.getClass(), i + " : " + j);
                    }
                    columns[0].setText(games[i].getEnd_time());
                    columns[1].setText(games[i].getType().toUpperCase());
                    columns[2].setText((int) ((score/score_max) *100) + " %");
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            Log.e("IQW/HistoryActivity", e.getMessage());
        }

        Utils.sendLog(this.getClass(), rows.length + " rows");

        table.removeAllViews();
        for(TableRow row : rows){
            table.addView(row);
            Utils.sendLog(this.getClass(), row.getId() + "");
        }
    }

    protected void updateHistory(View view){
        switch (view.getId()){
            case R.id.history_buttonNormal:
                updateUI("normal", 10);
                break;
            case R.id.history_buttonSpeed:
                updateUI("speed", 10);
                break;
            case R.id.history_buttonFriends:
                Utils.gimmeToast(getApplicationContext(), getText(R.string.NOT_IMPLEMENTED_YET).toString());
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), UserActivity.class);
        finish();
    }
}
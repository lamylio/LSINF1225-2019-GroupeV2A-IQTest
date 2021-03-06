package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class MainActivity extends AppCompatActivity {

    EditText loginUsername;
    EditText loginPassword;

    TextView loginRegisterButton;
    TextView loginMessage;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Initialize the database | Destruct when you've changed the schema **/
        new DatabaseHelper(getApplicationContext(), true);

        loginButton = findViewById(R.id.login_button);
        loginUsername = findViewById(R.id.login_pseudo);
        loginPassword = findViewById(R.id.login_password);
        loginRegisterButton = findViewById(R.id.login_registerButton);

        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.changeActivity(getApplicationContext(), RegisterActivity.class);
                finish();
            }
        });
    }

    public void checkUserLoginInfos(View view) {
        if (TextUtils.isEmpty(loginUsername.getText()) || TextUtils.isEmpty(loginPassword.getText()))
            Utils.gimmeToast(getApplicationContext(), getResources().getText(R.string.USERPASS_REQUIRED).toString());
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    /* Try to find the username in the database */
                    User logUser = DatabaseHelper.INSTANCE.userDao().findByName(loginUsername.getText().toString());

                    /* User found in the database **/
                    if (logUser != null) {
                        /* The given password equals the stored one */
                        if (User.hashPassword(loginPassword.getText().toString()).equals(logUser.getPassword())) {

                            /* Send the loggedUser to the UserActivity */
                            User.loggedUser = logUser;
                            Utils.changeActivity(getApplicationContext(), UserActivity.class);
                            finish();

                            Utils.toast = getResources().getText(R.string.WELCOME) + ", " + logUser.getUsername();
                        /* Password's hash aren't the same */
                        } else {Utils.toast = getResources().getText(R.string.INVALID_PASSWORD).toString();}
                    /* User not found **/
                    } else {Utils.toast = getResources().getText(R.string.INVALID_USER).toString();}

                    /* I decided to store the toast and send the message afterward
                     * But I could have used gimmeToast directly each time */
                    Utils.sendLog(this.getClass(), Utils.toast);
                    Utils.gimmeToast(getApplicationContext());
                }
            }).start();
        }
    }
}


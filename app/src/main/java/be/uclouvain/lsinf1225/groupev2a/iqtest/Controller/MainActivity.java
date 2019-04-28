package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.AppDatabase;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table.User;
import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

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
        /* Initialize the database **/
        AppDatabase.setup(this.getApplicationContext());

        loginButton = findViewById(R.id.login_button);
        loginUsername = findViewById(R.id.login_pseudo);
        loginPassword = findViewById(R.id.login_password);
        loginMessage = findViewById(R.id.login_message);
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
                    User logUser = AppDatabase.INSTANCE.userDao().findByName(loginUsername.getText().toString());

                    /* User found in the database **/
                    if (logUser != null) {
                        /* The given password equals the stored one */
                        if (User.hashPassword(loginPassword.getText().toString()).equals(logUser.getPassword())) {

                            /* Send the loggedUser to the ProfileActivity */
                            Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                            profile.putExtra("username", logUser.getUsername());
                            startActivity(profile);
                            finish();

                            Utils.toast = getResources().getText(R.string.WELCOME) + logUser.getUsername();
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


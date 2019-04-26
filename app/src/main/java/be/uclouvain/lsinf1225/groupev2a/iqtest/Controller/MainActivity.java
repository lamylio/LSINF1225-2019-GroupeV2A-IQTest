package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    static String toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Initialize the database **/
        AppDatabase.setup(this.getApplicationContext());

        loginButton = findViewById(R.id.login_button);
        loginUsername = findViewById(R.id.login_pseudo);
        loginPassword = findViewById(R.id.login_password);
        loginMessage = findViewById(R.id.login_message);
        loginRegisterButton = findViewById(R.id.login_registerButton);

        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
    }

    public void checkUserLoginInfos(View view) {
        if (TextUtils.isEmpty(loginUsername.getText()) || TextUtils.isEmpty(loginPassword.getText()))
            Utils.gimmeToast(getApplicationContext(), getResources().getText(R.string.USERPASS_REQUIRED));
        else {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    User logUser = AppDatabase.INSTANCE.userDao().findByName(loginUsername.getText().toString());

                    /** User found in the database **/
                    if (logUser != null) {
                        if (User.hashPassword(loginPassword.getText().toString()).equals(logUser.getPassword())) {

                            /** TODO Send the user to the ProfileActivity (use an Intent)**/
                             Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                             startActivity(profile);
                             finish();


                            MainActivity.toast = getResources().getText(R.string.WELCOME) + logUser.getUsername();

                        } else {
                            MainActivity.toast = getResources().getText(R.string.INVALID_PASSWORD).toString();
                        }
                    } else {
                        MainActivity.toast = getResources().getText(R.string.INVALID_USER).toString();
                    }
                    Log.d("IQW/Main", MainActivity.toast);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.gimmeToast(getApplicationContext(), MainActivity.toast);
                        }
                    });
                }
            }).start();
        }
    }
}


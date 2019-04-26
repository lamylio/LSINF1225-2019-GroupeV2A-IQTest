package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.AppDatabase;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table.User;
import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class RegisterActivity extends AppCompatActivity {

    EditText registerUsername;
    EditText registerPassword;
    EditText registerCity;
    EditText registerBirtdate;

    Button registerButton;
    Button registerBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerCity = findViewById(R.id.register_city);
        registerBirtdate = findViewById(R.id.register_birthdate);

        registerButton = findViewById(R.id.register_button);
        registerBackButton = findViewById(R.id.register_backButton);

        registerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    public void checkUserRegisterInfos(View v) {
        if (TextUtils.isEmpty(registerUsername.getText()) || TextUtils.isEmpty(registerPassword.getText()))
            Utils.gimmeToast(getApplicationContext(), getResources().getText(R.string.USERPASS_REQUIRED));
        else {
            /** TODO : Verify integrity of the data and register the user in the database **/

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("IQW/RegisterActivity", "Cleared all tables");
                    AppDatabase.INSTANCE.clearAllTables();
                    User newUser = new User(registerUsername.getText().toString(), registerPassword.getText().toString());
                    newUser.setCity(registerCity.getText().toString());
                    AppDatabase.INSTANCE.userDao().registerUser(newUser);
                    Log.d("IQW/RegisterActivity", "User created");
                }
            }).start();

            Utils.gimmeToast(getApplicationContext(), "Inscription réussie ! Veuillez vous connecter.");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void showDatePickerDialog(View v) {
        /** TODO: Create a date picker for the birthday */
    }
}

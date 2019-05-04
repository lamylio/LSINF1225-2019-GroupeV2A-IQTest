package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class RegisterActivity extends AppCompatActivity {

    EditText registerUsername;
    EditText registerPassword;
    EditText registerCity;
    EditText registerBirthdate;

    Button registerButton;
    Button registerBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerCity = findViewById(R.id.register_city);
        registerBirthdate = findViewById(R.id.register_birthdate);

        registerButton = findViewById(R.id.register_button);
    }

    public void checkUserRegisterInfos(View v) {
        if (TextUtils.isEmpty(registerUsername.getText()) || TextUtils.isEmpty(registerPassword.getText()))
            Utils.gimmeToast(getApplicationContext(), getResources().getText(R.string.USERPASS_REQUIRED).toString());
        else {
            /** TODO : Verify integrity of the data and register the user in the database **/

            new Thread(new Runnable() {
                @Override
                public void run() {

                    User check = DatabaseHelper.INSTANCE.userDao().findByName(registerUsername.getText().toString());
                    if(check == null){
                        check = new User(registerUsername.getText().toString(), registerPassword.getText().toString());
                        check.setCity(registerCity.getText().toString());
                        DatabaseHelper.INSTANCE.userDao().registerUser(check);

                        Utils.toast = "Inscription réussie !";
                        Utils.sendLog(this.getClass(), "User " + check.getUsername() + " created");

                        User.loggedUser = check;
                        Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
                        finish();

                    }else{
                        Utils.toast = "Nom d'utilisateur déjà emprunté !";
                    }
                    Utils.gimmeToast(getApplicationContext());
                }
            }).start();
        }
    }

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), MainActivity.class);
        finish();
    }

    public void showDatePickerDialog(View v) {
        /** TODO: Create a date picker for the birthday */
    }
}

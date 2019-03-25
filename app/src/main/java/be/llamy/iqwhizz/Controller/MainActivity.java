package be.llamy.iqwhizz.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import be.llamy.iqwhizz.Database.AppDatabase;
import be.llamy.iqwhizz.Database.Table.User;
import be.llamy.iqwhizz.R;

public class MainActivity extends AppCompatActivity {


    /** This is just a prototype of the login page, not definitive at all

        I added Room's dependencies to the build.gradle in order to implement the database
        We are going to use a MVC architecture, so please keep it in mind ;)
        - Lioche
     **/

    EditText userName;
    EditText userPassword;;

    TextView loginRegisterButton;
    TextView loginMessage;
    Button loginButton;

    String toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase.setup(this.getApplicationContext());

        loginButton = (Button) findViewById(R.id.login_button);
        userName = (EditText) findViewById(R.id.login_pseudoBox);
        userPassword = (EditText) findViewById(R.id.login_passwordBox);
        loginMessage = (TextView) findViewById(R.id.login_message);
        loginRegisterButton = (TextView) findViewById(R.id.login_registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().length() < 5 || userPassword.getText().length() < 5)
                    loginMessage.setText("Votre pseudo et mot de passe doivent faire au moins 5 caractères.");
                else {
                    checkUserCredentials(userName.getText().toString(), userPassword.getText().toString());
                    loginMessage.setText(toast);
                }
            }
        });

        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
    }



    private void checkUserCredentials(final String username, final String givenpassword){
        /** TODO: The toast is not implemented yet and is useless **/
        new Thread(new Runnable() {
            @Override
            public void run() {

                User logUser = AppDatabase.INSTANCE.userDao().findByName(username);

                /** User found in the database **/
                if(logUser != null) {
                    if(User.hashPassword(givenpassword) == logUser.getPassword()){

                        /**
                         TODO Send the user to the ProfileActivity (use an Intent)
                         Intent profile = new Intent(this, ProfileActivity.getClass());
                         startActivity(profile);
                         finish();
                         **/

                        toast = "Bienvenue " + userName.getText();

                    }else {toast = "Mot de passe incorrect.";}
                }else{toast = "Utilisateur introuvable dans la base de donnée";}
                Log.d("IQW/Main", toast);
            }
        }).start();
    }
}


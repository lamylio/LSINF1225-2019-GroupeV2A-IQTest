package be.llamy.iqwhizz.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import be.llamy.iqwhizz.Database.AppDatabase;
import be.llamy.iqwhizz.R;

public class MainActivity extends AppCompatActivity {


    /** This is just a prototype of the login page, not definitive at all

        I added Room's dependencies to the build.gradle in order to implement the database
        We are going to use a MVC architecture, so please keep it in mind ;)
        - Lioche
     **/

    EditText userName;
    EditText userPassword;;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase.setup(this.getApplicationContext());

        loginButton = (Button) findViewById(R.id.login_button);
        userName = (EditText) findViewById(R.id.login_pseudoBox);
        userPassword = (EditText) findViewById(R.id.login_passwordBox);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast = "Unknown error in " + this.getClass().getName();
                switch (checkUserCredentials()){
                    case 0 :
                        toast = "Pseudo et mot de passe requis.";
                        break;
                    case 1:
                        /* TODO Send the user to the ProfileActivity (use an Intent)
                        Intent profile = new Intent(this, ProfileActivity.getClass());
                        startActivity(profile);
                        finish();*/
                        toast = "Bienvenue " + userName.getText();
                        break;
                    case -1:
                        toast = "Oops.. pseudo et/ou mot de passe incorrect.";
                        break;
                }
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }
        });


    }



    private int checkUserCredentials(){
        /** Separate thread to I/O with the database and don't stay lock or crash the Activity
            TODO : Check in the database if the user exist and if the password match with the hash stored
         **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* AppDatabase.INSTANCE; is the way to get the connection */
            }
        }).start();
        return 2;
    }
}

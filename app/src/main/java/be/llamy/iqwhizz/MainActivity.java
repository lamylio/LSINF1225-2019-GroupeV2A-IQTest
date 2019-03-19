package be.llamy.iqwhizz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /** This is just a prototype of the login page, not definitive at all
        TODO : Use a MVC architecture to build the project ?

        I added Room's dependencies to the build.gradle in order to link the database
        Do you want to use SQLite's API instead ? It's much more time-eater and buggy
     */

    final int PSEUDO_MIN_LENGTH = 5, PASSWORD_MIN_LENGTH = 8;
    EditText userName;
    EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.login_button);
        userName = (EditText) findViewById(R.id.login_pseudoBox);
        userPassword = (EditText) findViewById(R.id.login_passwordBox);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast = "Unknown error in " + this.getClass().getCanonicalName();
                switch (checkUserCredentials()){
                    case 0 :
                        toast = "Pseudo et mot de passe requis.";
                        break;
                    case 1:
                        /* TODO Send the user to the ProfilActivity (use an Intent)
                        Intent profil = new Intent(this, ProfilActivity.getClass());
                        startActivity(profil);
                        finish();*/
                        toast = "Bienvenue " + userName.getText();
                        break;
                    case -1:
                        toast = "Oops.. pseudo et/ou mot de passe incorrect.";
                        break;
                }

                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* TODO : Integration with the database and check if credentials are wrong, if not then return 1 */

    private int checkUserCredentials(){
        if(userName.getText().length() < this.PSEUDO_MIN_LENGTH || userPassword.getText().length() < this.PASSWORD_MIN_LENGTH) return 0;
        return 1;
    }
}

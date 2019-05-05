package be.uclouvain.lsinf1225.groupev2a.iqtest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String toast;
    static boolean DEBUG_LOG = true;

    public static void sendLog(Class from, String message){
        if(DEBUG_LOG)Log.d("IQW/"+from.getName().substring(40), message);
    }

    public static void gimmeToast(Context appcontext) {gimmeToast(appcontext, toast);}
    public static void gimmeToast(final Context appcontext, final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
               Toast t = Toast.makeText(appcontext, message, Toast.LENGTH_SHORT);
               t.show();
            }
        });
    }

    /**Méthode pour passer à une autre activité en lui fournissant des informations
     * @param appcontext getApplicationContext()
     * @param gotoActivity TheActivity.class
     * @param extras le nom de l'information suivi d'une virgule et de sa valeur (ex: "username", "Lioche") */
    public static void changeActivity(Context appcontext, Class gotoActivity, String... extras) {
        Intent intent = new Intent(appcontext, gotoActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (extras != null){
            for (int i = 0; i < extras.length; i += 2) {
                intent.putExtra(extras[i], extras[i + 1]);
            }
        }

        appcontext.startActivity(intent);
    }

    public static void changeActivity(Context appcontext, Class gotoActivity){
        changeActivity(appcontext, gotoActivity, null);
    }

    public static String dateFormated(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(new Date().getTime());
    }
}

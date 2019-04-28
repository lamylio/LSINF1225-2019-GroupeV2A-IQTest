package be.uclouvain.lsinf1225.groupev2a.iqtest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    public static String toast;
    public static boolean DEBUG_LOG = true;

    public static void sendLog(Class from, String message){
        if(DEBUG_LOG)Log.d("IQW/"+from.getSimpleName(), message);
    }

    public static void gimmeToast(Context appcontext) {gimmeToast(appcontext, toast);}
    public static void gimmeToast(final Context appcontext, final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(appcontext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void changeActivity(Context appcontext, Class gotoActivity){
        Intent intent = new Intent(appcontext, gotoActivity);
        appcontext.startActivity(intent);
    }

}

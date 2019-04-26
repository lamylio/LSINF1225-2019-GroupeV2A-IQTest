package be.uclouvain.lsinf1225.groupev2a.iqtest;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void gimmeToast(Context appcontext, String toast) {
        Toast.makeText(appcontext, toast, Toast.LENGTH_SHORT).show();
    }

    public static void gimmeToast(Context appcontext, CharSequence toast) {
        Toast.makeText(appcontext, toast, Toast.LENGTH_SHORT).show();
    }
}

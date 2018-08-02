package com.example.usuario.techsolutions;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.EditText;

public class Util {

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(int dp, Resources r) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static boolean emptyFieldMSG(EditText txt, String msg){
        if(emptyWT(txt))txt.setError(msg);
        return emptyWT(txt);
    }

    public static String getTxt(EditText txt){
        return emptyWT(txt)? "":txt.getText().toString();
    }

    public static boolean emptyWT(EditText txt){
        return txt.getText().toString().equals("");
    }

}

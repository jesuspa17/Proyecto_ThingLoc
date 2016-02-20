package com.salesianostriana.thinglocapp.thinglocapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class Preferencias {

    public static SharedPreferences preferencias;
    public static SharedPreferences.Editor editor;

    public static void iniciarPreferencias(Context context){
        preferencias = context.getSharedPreferences("thingloc",Context.MODE_PRIVATE);
        editor = preferencias.edit();
    }
}

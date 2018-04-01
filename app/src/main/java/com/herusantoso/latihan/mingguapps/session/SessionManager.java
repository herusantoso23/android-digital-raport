package com.herusantoso.latihan.mingguapps.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.herusantoso.latihan.mingguapps.LoginActivity;
import com.herusantoso.latihan.mingguapps.MainActivity;
import com.herusantoso.latihan.mingguapps.model.Login;

import java.util.HashMap;

/**
 * Created by santoso on 4/1/18.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String id_key = "keyid";
    public static final String nis_key = "keynis";
    public static final String name_key = "keyname";
    public static final String class_key = "keyclass";
    public static final String phone_key = "keyphone";
    public static final String address_key = "keyaddress";
    public static final String password_key = "keypassword";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession(String nis){
        editor.putBoolean(is_login, true);
        editor.putString(nis_key, nis);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(nis_key, pref.getString(nis_key, null));
        return user;
    }
}

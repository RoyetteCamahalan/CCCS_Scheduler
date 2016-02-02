package com.cccsscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference_Fragment {
	private SharedPreferences prefs;

    public Preference_Fragment(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
        
    }
    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}

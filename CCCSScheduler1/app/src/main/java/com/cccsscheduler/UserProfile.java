package com.cccsscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User PC on 9/28/2015.
 */
public class UserProfile extends Activity {

    ImageView userpicture;
    TextView Name, Fname, Mname, Lname, Gender, Bdate, City, ContactNumber, EmailAddress, Username;
    Button Changepass;

    DBHelper dbHelper;

    ArrayList<HashMap<String, String>> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        dbHelper = new DBHelper(this);

        Name = (TextView) findViewById(R.id.txt_name);
        Fname = (TextView) findViewById(R.id.txt_fname);
        Mname = (TextView) findViewById(R.id.txt_mname);
        Lname = (TextView) findViewById(R.id.txt_lname);
        Gender = (TextView) findViewById(R.id.txt_gender);
        Bdate = (TextView) findViewById(R.id.txt_birthdate);
        City = (TextView) findViewById(R.id.txt_city);
        ContactNumber = (TextView) findViewById(R.id.txt_contact_number);
        EmailAddress = (TextView) findViewById(R.id.txt_email_address);
        Username = (TextView) findViewById(R.id.txt_username);
        SharedPreferences sharedPreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt(login.USERPREFID, 0);
        int count = dbHelper.getInformation2(user_id);
        Toast.makeText(this, String.valueOf(user_id), Toast.LENGTH_SHORT).show();
        user = dbHelper.getInformation(user_id);

        Name.setText(user.get(0).get("FULLNAME"));
        Gender.setText(user.get(0).get("USERS_GENDER") + ",");
        Fname.setText(user.get(0).get("USERS_FNAME"));
        Mname.setText(user.get(0).get("USERS_MNAME"));
        Lname.setText(user.get(0).get("USERS_LNAME"));
        Bdate.setText(user.get(0).get("USERS_BDATE"));
        City.setText(user.get(0).get("USERS_ADDRESS"));
        ContactNumber.setText(user.get(0).get("USERS_CONTACT"));
        EmailAddress.setText(user.get(0).get("USERS_EMAIL"));
        Username.setText(user.get(0).get("USERS_UNAME"));
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}

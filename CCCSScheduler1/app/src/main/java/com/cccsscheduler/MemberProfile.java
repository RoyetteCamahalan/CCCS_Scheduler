package com.cccsscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
public class MemberProfile extends Activity {

    TextView Name, Fname, Mname, Lname, Gender, Bdate, City, ContactNumber, EmailAddress;

    DBHelper dbHelper;
    int leader_id=-1;
    ArrayList<HashMap<String, String>> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_profile);

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
        Intent intent=getIntent();
        int user_id = intent.getIntExtra("user_id", 0);
        leader_id=intent.getIntExtra("leader_id",0);
        if(user_id!=0){
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
        }

        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gmember_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            //case R.id.action_remove_member:


        }
        return super.onOptionsItemSelected(item);
    }
}

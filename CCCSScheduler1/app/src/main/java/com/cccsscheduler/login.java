package com.cccsscheduler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Network.miscellaneous;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends Activity implements OnClickListener {
    public static final String USERPREFERENCES = "UserPrefs";
    public static final String USERPREFID = "IDKey";
    public static final String USERLASTLOGIN = "lastlogin";
    public static final String LASTLOGIN = "LASTKey";
    public static final String SHAREDLASTUPDATE = "sharedLASTupdate";
    public static final String LASTUPDATE = "LASTupdate";
    SharedPreferences sharedpreferences, sharedlastlogin, sharedlastupdate;

    EditText uname, pass;
    TextView btnlogin, txtcreate, forgotpassword;
    DBHelper dbhelper;
    ImageView showpass;

    boolean flag = true;
    ProgressDialog PD;
    String url = "http://bsit701.com/cccs_scheduler/user_login.php";
    miscellaneous misc = new miscellaneous();

    public static Activity loginActivity;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbhelper = new DBHelper(this);
        loginActivity = this;

        uname = (EditText) findViewById(R.id.txt_username);
        pass = (EditText) findViewById(R.id.txt_password);
        btnlogin = (TextView) findViewById(R.id.txt_signin);
        txtcreate = (TextView) findViewById(R.id.txt_createaccount);
        showpass = (ImageView) findViewById(R.id.img_showpassword);
        forgotpassword = (TextView) findViewById(R.id.txt_forgotpassword);

        sharedpreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        sharedlastlogin = getSharedPreferences(USERLASTLOGIN, Context.MODE_PRIVATE);
        sharedlastupdate = getSharedPreferences(SHAREDLASTUPDATE, Context.MODE_PRIVATE);

        btnlogin.setOnClickListener(this);
        txtcreate.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (flag == false) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    flag = true;
                    ;
                } else {
                    pass.setInputType(129);
                    flag = false;

                }
            }
        });
        pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() == 0) {
                    showpass.setVisibility(View.INVISIBLE);
                } else {
                    showpass.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.txt_forgotpassword:
                String forgotpass = uname.getText().toString();
                Intent intent1 = new Intent(this, forgot_pass.class);
                intent1.putExtra("forgot_pass", forgotpass);
                startActivity(intent1);
                break;

            case R.id.txt_signin:
                final String username = uname.getText().toString();
                final String password = pass.getText().toString();

                //final String email = uname.getText().toString();
                int userid = dbhelper.searchUser(username, password);

                if (userid > 0) {
                    //SharedPreferences sharedpref=this.getSharedPreferences(getString(R.string.userid), this.MODE_PRIVATE);
                    //set sharepreference here
                    int lastlog = sharedlastlogin.getInt(LASTLOGIN, 0);
                    Log.d("counter", "current login "+String.valueOf(userid)+" last login " +String.valueOf(lastlog));
                    if (userid == lastlog) {

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        SharedPreferences.Editor editor2 = sharedlastlogin.edit();
                        editor.putInt(USERPREFID, userid);
                        editor2.putInt(LASTLOGIN, userid);
                        editor.commit();
                        editor2.commit();

                        Intent service = new Intent(this, MyService.class);
                        startService(service);
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        pass.setText("");
                    } else {//clear sqlite
                        if (misc.checkInternet(this)) {
                            SharedPreferences sharedpreferenceslastupdate = getSharedPreferences(SHAREDLASTUPDATE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor3 = sharedpreferenceslastupdate.edit();
                            editor3.clear();
                            editor3.commit();
                            onlinelogin(username, password, 2);
                            dbhelper.clearDB();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            SharedPreferences.Editor editor2 = sharedlastlogin.edit();
                            editor.putInt(USERPREFID, userid);
                            editor2.putInt(LASTLOGIN, userid);
                            editor.commit();
                            editor2.commit();
                        } else {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                            dialogBuilder.setTitle("Unable to Sign in");
                            dialogBuilder.setMessage("Unable to Fetch your Data from server due to Connection Problem.");
                            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            dialogBuilder.create().show();
                        }
                    }
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    dialogBuilder.setTitle("Account not Found");
                    dialogBuilder.setMessage("The App wants to find your Account in Server and requires Internet Connection");
                    dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (misc.checkInternet(getApplicationContext())) {
                                onlinelogin(username, password, 1);
                                dbhelper.clearDB();
                                SharedPreferences sharedpreferenceslastupdate = getSharedPreferences(SHAREDLASTUPDATE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor3 = sharedpreferenceslastupdate.edit();
                                editor3.clear();
                                editor3.commit();
                            } else {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
                                dialogBuilder.setTitle("No Internet Connection");
                                dialogBuilder.setMessage("You are not connected to the server. Check your Internet Connection and Try again");
                                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                dialogBuilder.create().show();
                            }
                        }
                    });

                    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    dialogBuilder.create().show();
                }


                break;

            case R.id.txt_createaccount:

                if (misc.checkInternet(this)) {
                    Intent intent = new Intent(getBaseContext(), sign_up.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(this);
                    dialogBuilder2.setTitle("No Internet Connection");
                    dialogBuilder2.setMessage("You are not connected to the server. Check your Internet Connection and Try again");
                    dialogBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    dialogBuilder2.create().show();
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedpreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        int user_id = sharedpreferences.getInt(USERPREFID, 0);

        if (user_id > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void onlinelogin(final String uname, final String pword, final int action) {
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        PD.show();
        JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url + "?uname=" + uname + "&&pword=" + pword, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");

                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("users");


                                JSONObject jobj = ja.getJSONObject(0);

                                int id = Integer.valueOf(jobj.getString("user_id"));
                                String fname = jobj.getString("fname");
                                String mname = jobj.getString("mname");
                                String lname = jobj.getString("lname");
                                String address = jobj.getString("address");
                                String gender = jobj.getString("gender");
                                String bdate = jobj.getString("bdate");
                                String contactno = jobj.getString("contactno");
                                String email = jobj.getString("email");
                                String gmail = jobj.getString("gmail");
                                String updated_at = jobj.getString("updated_at");

                                DBHelper helper = new DBHelper(getApplicationContext());
                                if (action == 1) {
                                    if (helper.sign_up(fname, mname, lname, gender, bdate, address, uname, pword, email, contactno, id)) {
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    }


                                } else if (action == 2) {
                                    if (helper.updateUserinfo(fname, mname, lname, gender, bdate, address, uname, pword, email, contactno, id)) {
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                sharedpreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                SharedPreferences.Editor editor2 = sharedlastlogin.edit();
                                editor.putInt(login.USERPREFID, id);
                                editor2.putInt(login.LASTLOGIN, id);
                                editor.commit();
                                editor2.commit();

                                Intent service = new Intent(getApplicationContext(), MyService.class);
                                startService(service);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                PD.dismiss();
                                pass.setText("");
                            } // if end
                            else {
                                PD.dismiss();
                                AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(login.this);
                                dialogBuilder2.setTitle("Invalid Log In Credentials");
                                dialogBuilder2.setMessage("Username and Password does not Exist.");
                                dialogBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                dialogBuilder2.create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                PD.dismiss();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jreq);

    }
}


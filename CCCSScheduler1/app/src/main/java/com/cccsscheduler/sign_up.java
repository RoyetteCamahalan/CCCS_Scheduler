package com.cccsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class sign_up extends Activity implements OnClickListener {
    private static String url = "http://bsit701.com/cccs_scheduler/insert_user.php";

    JSONParser jsonParser = new JSONParser();
    EditText ET_email, ET_uname, ET_pass, ET_cpass;
    ImageButton btn_next;
    public String fname, mname, lname, bdate, gender = "", email, uname, pass, address, cpass, contactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_basic);

        ET_email = (EditText) findViewById(R.id.txt_email);
        ET_uname = (EditText) findViewById(R.id.txt_uname);
        ET_pass = (EditText) findViewById(R.id.txt_pass);
        ET_cpass = (EditText) findViewById(R.id.txt_conpass);

        btn_next = (ImageButton) findViewById(R.id.imgbtn_next);
        btn_next.setOnClickListener(this);


        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        switch (v.getId()) {
            case R.id.imgbtn_next:

                //validate here
                email = ET_email.getText().toString();
                uname = ET_uname.getText().toString();
                pass = ET_pass.getText().toString();
                cpass = ET_cpass.getText().toString();
                if (email.matches("")) {
                    ET_email.setError("Enter Email");
                } else {
                }
                if (!isValidEmail(email)) {
                    ET_email.setError("Invalid Email");
                } else {
                }
                if (uname.matches("")) {
                    ET_uname.setError("Enter Username");
                } else {
                }
                if (pass.matches("")) {
                    ET_pass.setError("Enter Password");
                } else {
                }
                if (isValidPassword(pass)) {
                    ET_pass.setError("Password must be atleast 5 Characters.");
                } else {
                }
                if (pass.equals(cpass)) {
                    if (!isValidPassword(pass)) {
                        ET_pass.setError("Invalid Password");
                    } else {
                        Intent intent = new Intent(this, sign_up_address.class);
                        intent.putExtra("fname", fname);
                        intent.putExtra("mname", mname);
                        intent.putExtra("lname", lname);
                        intent.putExtra("bdate", bdate);
                        intent.putExtra("address", address);
                        intent.putExtra("gender", gender);
                        intent.putExtra("contactno", contactno);
                        startActivityForResult(intent, 1);
                    }
                } else {
                    ET_cpass.setError("Password Do Not Math");

                }
                break;


            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            int checker = data.getIntExtra("issave", 0);
            fname = data.getStringExtra("fname");
            mname = data.getStringExtra("mname");
            lname = data.getStringExtra("lname");
            address = data.getStringExtra("address");
            bdate = data.getStringExtra("bdate");
            gender = data.getStringExtra("gender");
            contactno = data.getStringExtra("contactno");
            Toast.makeText(this, fname + " " + mname + " " + lname, Toast.LENGTH_SHORT).show();
            if (checker == 1) {
                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                String tag_string_req = "req_register";
                StringRequest strReq = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                int id = Integer.parseInt(response);
                                if (id == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "Username already exists",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    DBHelper helper = new DBHelper(getApplicationContext());
                                    if (helper.sign_up(fname, mname, lname, gender, bdate, address, uname, pass, email, contactno, id)) {
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedpreferences;
                                        sharedpreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putInt(login.USERPREFID, id);
                                        editor.putInt(login.LASTLOGIN, id);
                                        editor.commit();
                                        SharedPreferences sharedpreferenceslastupdate;
                                        sharedpreferenceslastupdate = getSharedPreferences(login.SHAREDLASTUPDATE, Context.MODE_PRIVATE);
                                        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        dateFormatter.setLenient(false);
                                        Date today = new Date();
                                        String s = dateFormatter.format(today);

                                        SharedPreferences.Editor editor2 = sharedpreferenceslastupdate.edit();
                                        editor2.putString(login.LASTUPDATE, s);
                                        editor2.commit();
                                        finish();
                                        Intent service = new Intent(getApplicationContext(), MyService.class);
                                        startService(service);


                                    }
                                    Toast.makeText(getApplicationContext(),
                                            "Data Inserted Successfully",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Failed to Insert", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fname", fname);
                        params.put("mname", mname);
                        params.put("lname", lname);
                        params.put("bdate", bdate);
                        params.put("gender", gender);
                        params.put("address", address);
                        params.put("contactno", contactno);
                        params.put("uname", uname);
                        params.put("pass", pass);
                        params.put("email", email);
                        return params;

                    }
                };
                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            }
        }
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 2) {

            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

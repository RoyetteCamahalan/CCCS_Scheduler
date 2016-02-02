package com.cccsscheduler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.Custom_Adapters.add_user_custom;
import com.Getters_Setters.users_getset;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class AddMember extends Activity implements TextWatcher {
    ListView lst_users;
    EditText searchUser;
    add_user_custom user_custom;

    ArrayList<users_getset> users = new ArrayList();
    ArrayList<users_getset> temp_users = new ArrayList();

    String url = "http://bsit701.com/cccs_scheduler/get_users.php/?";
    ProgressDialog PD;

    int group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_layout);

        Intent intent = getIntent();
        group_id = intent.getIntExtra("group_id", 0);
        lst_users = (ListView) findViewById(R.id.lst_users);
        searchUser = (EditText) findViewById(R.id.searchUser);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        getusers();

        PD.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                user_custom = new add_user_custom(getApplicationContext(), users, group_id);
                lst_users.setAdapter(user_custom);
            }
        });

        lst_users.setTextFilterEnabled(true);
        searchUser.addTextChangedListener(this);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getusers() {
        PD.show();
        JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url + "group_id=" + String.valueOf(group_id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        JSONArray ja = response.getJSONArray("users");

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jobj = ja.getJSONObject(i);
                            users_getset user = new users_getset();

                            int id = Integer.valueOf(jobj.getString("user_id"));
                            String fname = jobj.getString("fname");
                            String mname = jobj.getString("mname");
                            String lname = jobj.getString("lname");
                            String address = jobj.getString("address");
                            int stat = Integer.valueOf(jobj.getString("status"));

                            user.setId(id);
                            user.setName(fname, mname, lname);
                            user.setAddress(address);
                            user.setStatus(stat);
                            users.add(user);
                            temp_users.add(user);
                        } // for loop ends
                    } // if ends

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PD.dismiss();

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String search = searchUser.getText().toString();

        if (TextUtils.isEmpty(search)) {
            lst_users.clearTextFilter();
        } else {
            lst_users.setFilterText(search);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

package com.cccsscheduler;

import java.util.HashMap;
import java.util.Map;

import com.Network.miscellaneous;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class new_group extends Activity {
    private static String url = "http://bsit701.com/cccs_scheduler/manage_groups.php";
    DBHelper dbhelper = new DBHelper(this);
    String g_name;
    String g_desc;
    EditText txtgroupname, txtdesc;
    int user_id;
    MenuItem item_save, item_cancel, item_edit, item_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_group);
        txtdesc = (EditText) findViewById(R.id.group_desc);
        txtgroupname = (EditText) findViewById(R.id.group_name);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        item_save = menu.findItem(R.id.save);
        item_cancel = menu.findItem(R.id.cancel);
        item_edit = menu.findItem(R.id.menu_edit);
        item_delete = menu.findItem(R.id.menu_delete);
        item_save.setVisible(true);
        item_cancel.setVisible(false);
        item_edit.setVisible(false);
        item_delete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                miscellaneous misc = new miscellaneous();
                if (misc.checkInternet(this)) {
                    g_name = txtgroupname.getText().toString();
                    g_desc = txtdesc.getText().toString();
                    SharedPreferences sharedpreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
                    user_id = sharedpreferences.getInt(login.USERPREFID, 0);

                    final ProgressDialog pDialog = new ProgressDialog(this);
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    String tag_string_req = "req_register";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    try {
                                        int id = Integer.parseInt(response);
                                        if (dbhelper.addNewGroup(g_name, g_desc, user_id, id)) {
                                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(),
                                                "Error",
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
                            params.put("action_type", "1");
                            params.put("user_id", String.valueOf(user_id));
                            params.put("group_name", g_name);
                            params.put("group_desc", g_desc);
                            return params;

                        }
                    };
                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    dialogBuilder.setTitle("Connection Lost");
                    dialogBuilder.setMessage("You Connection to the server has been lost. Check your Internet Connection and Try again");
                    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    dialogBuilder.create().show();
                }


                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

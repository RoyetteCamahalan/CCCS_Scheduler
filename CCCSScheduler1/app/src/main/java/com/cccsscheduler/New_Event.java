package com.cccsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.Custom_Adapters.Events_Custom_Adapter;
import com.Getters_Setters.events_adapter;
import com.Network.miscellaneous;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.R.menu;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class New_Event extends Activity implements OnClickListener, OnTimeSetListener, OnDateSetListener {
    JSONParser jsonParser = new JSONParser();

    private static String url = "http://bsit701.com/cccs_scheduler/insert_user.php";
    EditText event_name, event_desc, date_deadline, time_deadline;
    public String ename, edesc;
    Spinner priority;
    private int extraeventID = 0;
    private String extraeventName = "";
    private String extraeventDesc = "";
    private String extraeventDateDeadline = "";
    private String extraeventTimeDeadline = "";
    private int extraeventpriority = -1;

    String str_name, str_desc, str_date, str_time;
    int prio = -1;
    MenuItem item_save, item_cancel, item_edit, item_delete;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        Intent intent = getIntent();
        extraeventID = intent.getIntExtra("event_id", -1);
        extraeventName = intent.getStringExtra("event_name");
        extraeventDesc = intent.getStringExtra("event_desc");
        extraeventDateDeadline = intent.getStringExtra("event_date_deadline");
        extraeventTimeDeadline = intent.getStringExtra("event_time_deadline");
        extraeventpriority = intent.getIntExtra("event_priority", -1);

        event_name = (EditText) findViewById(R.id.event_name);
        event_desc = (EditText) findViewById(R.id.event_description);
        date_deadline = (EditText) findViewById(R.id.event_date_deadline);
        time_deadline = (EditText) findViewById(R.id.event_time_deadline);
        priority = (Spinner) findViewById(R.id.event_priority);
        String[] priorities = getResources().getStringArray(R.array.priority_dataset);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, priorities);
        priority.setAdapter(adapter);
        date_deadline.setOnClickListener(this);
        time_deadline.setOnClickListener(this);

        if (extraeventID > 0) {
            disableviews();
            //fill in all the extra data to the ui elements
            event_name.setText(extraeventName);
            event_desc.setText(extraeventDesc);
            date_deadline.setText(extraeventDateDeadline);
            time_deadline.setText(extraeventTimeDeadline);
            priority.setSelection(extraeventpriority - 1, true);
        } else {
            enableviews();
        }
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        item_save = menu.findItem(R.id.save);
        item_cancel = menu.findItem(R.id.cancel);
        item_edit = menu.findItem(R.id.menu_edit);
        item_delete = menu.findItem(R.id.menu_delete);
        if (extraeventID > 0) {

            item_save.setVisible(false);
            item_cancel.setVisible(false);
            item_edit.setVisible(true);
            item_delete.setVisible(true);
        } else {
            item_save.setVisible(true);
            item_cancel.setVisible(false);
            item_edit.setVisible(false);
            item_delete.setVisible(false);
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        Calendar cal = Calendar.getInstance();

        switch (v.getId()) {

            case R.id.event_date_deadline:

                DatePickerDialog datePicker = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePicker.show();

                break;

            case R.id.event_time_deadline:

                TimePickerDialog timePicker = new TimePickerDialog(this, this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                timePicker.show();

                break;

            default:
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        String strTime = "";

        if (hourOfDay > 12) {
            strTime = strTime + String.format("%02d", (hourOfDay - 12)) + ":" + String.format("%02d", minute) + " PM";
        } else if (hourOfDay == 12 && minute > 0) {
            strTime = strTime + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + " PM";
        } else if (hourOfDay == 0) {
            strTime = strTime + String.format("%02d", (hourOfDay + 12)) + ":" + String.format("%02d", minute) + " AM";
        } else {
            strTime = strTime + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + " AM";
        }

        time_deadline.setText(strTime);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 00);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub
        date_deadline.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        DBHelper dbHelper = new DBHelper(this);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_delete:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(R.string.title_to_delete_event);
                dialogBuilder.setMessage(R.string.ask_to_delete_event);
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        dbHelper.deleteEvent(extraeventID);
                        finish();
                    }

                });

                //set negative button and it's click listener
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogBuilder.create().show();


                return true;
            case R.id.menu_edit:
                item_save.setVisible(true);
                item_cancel.setVisible(true);
                item_edit.setVisible(false);
                item_delete.setVisible(false);
                enableviews();
                //getMenuInflater().inflate(R.menu.save_cancel, R.menu.menu_edit);
                break;

            case R.id.save:

                str_name = event_name.getText().toString();
                str_desc = event_desc.getText().toString();
                str_date = date_deadline.getText().toString();
                str_time = time_deadline.getText().toString();
                prio = priority.getSelectedItemPosition() + 1;
                boolean checker = true;
                if (str_name.equals("")) {
                    event_name.setError("Please Enter Name");
                    checker = false;
                }
                if (str_date.equals("")) {
                    date_deadline.setError("Please Enter Date");
                    checker = false;
                }
                if (str_time.equals("")) {
                    time_deadline.setError("Please Enter Time");
                    checker = false;
                }

                if (checker) {


                    if (extraeventID > 0) {
                        events_adapter event = new events_adapter();
                        event.setId(extraeventID);
                        event.setName(str_name);
                        event.setDescription(str_desc);
                        event.setdate_deadline(str_date);
                        event.settime_deadline(str_time);
                        event.setpriority(prio);
                        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        String s = dateFormatter.format(today);
                        event.setupdated_at(s);

                        if (str_name.equals("")) {
                            event_name.setError("Please Enter Name");
                        } else {
                            if (dbHelper.updateTask(event)) {

                                Toast.makeText(this, "Event is updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to update event!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        setalarm();


                        finish();
                        return true;
                    }


                    DBHelper helper = new DBHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    extraeventID = (int) helper.addNewEvent(str_name, str_desc, str_date, str_time, formattedDate, "", prio, 0);
                /*if(extraeventID>0){
					miscellaneous misc=new miscellaneous();
					if(misc.checkInternet(this)){
						String tag_string_req = "req_register";

						StringRequest strReq = new StringRequest(Request.Method.POST, url,
								new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

								int id=Integer.parseInt(response);
								if(id!=0){
									DBHelper helper=new DBHelper(getApplicationContext());
									events_adapter event = new events_adapter();
									event.setId(extraeventID);
									event.setName(str_name);
									event.setDescription(str_desc);
									event.setdate_deadline(str_date);
									event.settime_deadline(str_time);
									event.setpriority(prio);
									event.setserverid(id);
									if(helper.updateTask(event)){

									}
								}
								Toast.makeText(getApplicationContext(),
										"Data Inserted Successfully",
										Toast.LENGTH_SHORT).show();
							}


						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
									Toast.makeText(getApplicationContext(),
											"failed to insert", Toast.LENGTH_SHORT).show();
							}
						}) {
							@Override
							protected Map<String, String> getParams() {
								Map<String, String> params = new HashMap<String, String>();
								params.put("event_name", str_name);
								params.put("event_desc", str_desc);
								params.put("event_date",str_date);
								params.put("event_time", str_time);
								params.put("event_prio", String.valueOf(prio));
								return params;

							}
						};
						// Adding request to request queue
						AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

					}
				}*/
                    setalarm();
                    finish();
                }


                break;
            case R.id.cancel:

                item_save.setVisible(false);
                item_cancel.setVisible(false);
                item_edit.setVisible(true);
                item_delete.setVisible(true);
                disableviews();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disableviews() {
        event_name.setKeyListener(null);
        event_desc.setKeyListener(null);
        date_deadline.setEnabled(false);
        time_deadline.setEnabled(false);
        priority.setEnabled(false);
    }

    private void enableviews() {
        event_name.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        event_desc.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        date_deadline.setEnabled(true);
        time_deadline.setEnabled(true);
        priority.setEnabled(true);
    }

    private void setalarm() {
            if (prio == 3) {

                Intent intent = new Intent(this, alarm_activity.class);
                intent.putExtra("name", str_name);
                intent.putExtra("desc", str_desc);
                intent.putExtra("id", extraeventID);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, extraeventID, intent, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            } else {
                Intent intent = new Intent(this, alarmcontroller.class);
                intent.putExtra("id", extraeventID);
                intent.putExtra("title", str_name);
                intent.putExtra("desc", str_desc);
                intent.putExtra("date", str_date);
                intent.putExtra("time", str_time);
                intent.putExtra("prio", prio);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, extraeventID, intent, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }

    }
}

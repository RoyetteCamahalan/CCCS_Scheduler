package com.cccsscheduler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.Custom_Adapters.Group_Events_CustomAdapter;
import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.events_adapter;
import com.Network.miscellaneous;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cccsscheduler.R;
import com.cccsscheduler.R.array;
import com.cccsscheduler.R.id;
import com.cccsscheduler.R.layout;
import com.cccsscheduler.R.menu;
import com.cccsscheduler.R.string;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class New_Group_Event extends Activity implements OnClickListener, OnDateSetListener, OnTimeSetListener{
	private EditText event_name,event_desc,date_deadline,time_deadline;
	private static String url= "http://bsit701.com/cccs_scheduler/manage_group_events.php";
	Spinner priority;
	private int group_id=-1;
	private int leader_id=-1;
	private int extraeventID = -1;
	private String extraeventName = "";
	private String extraeventDesc = "";
	private String extraeventDateDeadline = "";
	private String extraeventTimeDeadline = "";
    private int extraeventUserID = -1;
	private int extraeventpriority=-1;
	private int extraeventposition = -1;
	int user_id;
	MenuItem item_save,item_cancel,item_edit,item_delete;
	Calendar cal=Calendar.getInstance();
	miscellaneous misc=new miscellaneous();
	DBHelper dbhelper=new DBHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group_event);
		SharedPreferences sharedpreferences = getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
		user_id =sharedpreferences.getInt(login.USERPREFID, 0);
		Intent intent=getIntent();
		group_id=intent.getIntExtra("group_id", 0);
		leader_id=intent.getIntExtra("leader_id", 0);
		extraeventID = intent.getIntExtra("event_id",-1);
		extraeventName = intent.getStringExtra("event_name");
		extraeventDesc = intent.getStringExtra("event_desc");
		extraeventDateDeadline = intent.getStringExtra("event_date_deadline");
		extraeventTimeDeadline = intent.getStringExtra("event_time_deadline");
		extraeventpriority=intent.getIntExtra("event_priority", -1);
        extraeventUserID = intent.getIntExtra("userID", -1);

		event_name= (EditText) findViewById(R.id.Gevent_name);
		event_desc= (EditText) findViewById(R.id.Gevent_description);
		date_deadline= (EditText) findViewById(R.id.Gevent_date_deadline);
		time_deadline= (EditText) findViewById(R.id.Gevent_time_deadline);
		priority=(Spinner) findViewById(R.id.Gevent_priority);
		String []priorities=getResources().getStringArray(R.array.priority_dataset);
		ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,priorities);
		priority.setAdapter(adapter);

		date_deadline.setOnClickListener(this);
		time_deadline.setOnClickListener(this);
		if(extraeventID>0){
			disableviews();
			//fill in all the extra data to the ui elements
			event_name.setText(extraeventName);
			event_desc.setText(extraeventDesc);
			date_deadline.setText(extraeventDateDeadline);
			time_deadline.setText(extraeventTimeDeadline);
			priority.setSelection(extraeventpriority-1, true);
		}
		else {
			enableviews();
		}
		getActionBar().setHomeButtonEnabled(true);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		switch(v.getId()){

		case R.id.Gevent_date_deadline:

			DatePickerDialog datePicker = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			datePicker.show();

			break;

		case R.id.Gevent_time_deadline:

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

		if(hourOfDay>12){
			strTime = strTime + String.format("%02d", (hourOfDay-12)) + ":" + String.format("%02d", minute)+" PM";
		} else if(hourOfDay==12 && minute>0){
			strTime = strTime + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)+" PM";
		} else if(hourOfDay==0){
			strTime = strTime + String.format("%02d", (hourOfDay+12))+ ":" + String.format("%02d", minute)+" AM";
		} else {
			strTime = strTime + String.format("%02d", hourOfDay)+ ":" + String.format("%02d", minute)+" AM";
		}

		time_deadline.setText(strTime);
		cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
		cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,00);
	}
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		date_deadline.setText(year+"-"+String.format("%02d", (monthOfYear+1))+"-"+String.format("%02d", dayOfMonth));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.save_cancel, menu);
		item_save=menu.findItem(R.id.save);
		item_cancel=menu.findItem(R.id.cancel);
		item_edit=menu.findItem(R.id.menu_edit);
		item_delete=menu.findItem(R.id.menu_delete);
		if(extraeventID>0){

			item_save.setVisible(false);
			item_cancel.setVisible(false);
			item_edit.setVisible(true);
			item_delete.setVisible(true);
		}
		else {
			item_save.setVisible(true);
			item_cancel.setVisible(false);
			item_edit.setVisible(false);
			item_delete.setVisible(false);
		}

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_delete:
            if(extraeventUserID == user_id || leader_id == user_id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(R.string.title_to_delete_event);
                dialogBuilder.setMessage(R.string.ask_to_delete_event);
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper =new DBHelper(getApplicationContext());
                        dbHelper.deleteGroup_Event(extraeventID);
                        finish();
                    }

                });

                //set negative button and it's click listener
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogBuilder.create().show();
            } else {
                Toast.makeText(this, "You are not authorized to delete this event.", Toast.LENGTH_SHORT).show();
            }

			return true;
		case R.id.menu_edit:
            if(extraeventUserID == user_id || leader_id == user_id) {
                if(misc.checkInternet(this)){
                    item_save.setVisible(true);
                    item_cancel.setVisible(true);
                    item_edit.setVisible(false);
                    item_delete.setVisible(false);
                    enableviews();
                }else {
                    AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(this);
                    dialogBuilder2.setTitle("No Internet Connection");
                    dialogBuilder2.setMessage("You are not connected to the server. Check your Internet Connection and Try again");
                    dialogBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    dialogBuilder2.create().show();
                }
            } else {
                Toast.makeText(this, "You are not authorized to edit this event.", Toast.LENGTH_SHORT).show();
            }

			//getMenuInflater().inflate(R.menu.save_cancel, R.menu.menu_edit);
			break;
		case R.id.save:
			if(misc.checkInternet(this)){
				final String str_name=event_name.getText().toString();
				final String str_desc=event_desc.getText().toString();
				final String str_date=date_deadline.getText().toString();
				final String str_time=time_deadline.getText().toString();

				final int prio=priority.getSelectedItemPosition()+1;
				String [] time = str_time.split(" ");
				String time2 = time[0];
				String time3 = time[1]; //AM OR PM

				String [] time4=time2.split(":");
				int hr=Integer.valueOf(time4[0]);

                if(time3.equals("PM") && hr >= 1){
                    hr += 12;
				}

				final String str_time2 = String.valueOf(hr)+":"+time4[1]+":00";
                final ProgressDialog pDialog = new ProgressDialog(this);

				pDialog.setMessage("Loading...");
				pDialog.show();     
				String tag_string_req = "req_register";

				StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							String [] arr=response.split(" ");
							int local_id=Integer.valueOf(arr[0]);
							int ser_id=Integer.valueOf(arr[1]);

							if(extraeventID>0){
								Group_Events_Adapter event = new Group_Events_Adapter();
								event.setId(extraeventID);
								event.setName(str_name);
								event.setDescription(str_desc);
								event.setdate_deadline(str_date);
								event.settime_deadline(str_time);
								event.setpriority(prio);
								if(dbhelper.updateGroup_event(event,0)){
									Toast.makeText(getApplicationContext(), "Event is updated successfully!", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getApplicationContext(), "Failed to update event!", Toast.LENGTH_SHORT).show();
								}
							}
							else {
								int status=0;
								if(leader_id==user_id){
									status=1;
								}
                                if(str_name.equals("")){
                                    event_name.setError("Please Enter Name");
                                }
                                if(str_date.equals("")){
                                    date_deadline.setError("Please Enter Date");
                                }
                                if(str_time.equals("")){
                                    time_deadline.setError("Please Enter Time");
                                }
                                else {
                                    dbhelper.insertgroup_event(group_id, user_id, str_name, str_desc, str_date, str_time, prio, status, ser_id);

                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                }
									
							}
                            Calendar cal2=Calendar.getInstance();
                            cal2.set(Calendar.MINUTE,cal.MINUTE-1);
                            if (cal.getTimeInMillis() < cal2.getTimeInMillis()){
                                if(prio==2){
                                    Intent intent = new Intent(getApplicationContext(), alarm_activity.class);
                                    intent.putExtra("name", str_name);
                                    intent.putExtra("desc", str_desc);
                                    intent.putExtra("id", ser_id);

                                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), ser_id, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

                                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
                                }
                                else {
                                    Intent intent = new Intent(getApplicationContext(), alarmcontroller.class);
                                    intent.putExtra("id", ser_id);
                                    intent.putExtra("title", str_name);
                                    intent.putExtra("desc", str_desc);
                                    intent.putExtra("date", str_date);
                                    intent.putExtra("time", str_time);
                                    intent.putExtra("prio", prio);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ser_id, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

                                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
                                }
                            }


							pDialog.dismiss();
							finish();
						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(),
								"failed to insert", Toast.LENGTH_SHORT).show();
					}
				}) {
					@Override
					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
						if(extraeventID>0){
							params.put("action_type", "2");
							params.put("event_id", String.valueOf(extraeventID));
						}
						else {
							params.put("action_type", "1");
						}


						params.put("user_id", String.valueOf(user_id));
						params.put("group_id", String.valueOf(group_id));
						params.put("event_name", str_name);
						params.put("event_desc", str_desc);
						params.put("event_date",str_date);
						params.put("event_time", str_time2);
						params.put("event_prio", String.valueOf(prio));
						if(leader_id==user_id){
							params.put("status", "1");
						}else {
							params.put("status", "0");
						}
						return params;

					}
				};
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

			}
			else{
				AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
				dialogBuilder1.setTitle("Connection Lost");
				dialogBuilder1.setMessage("You have lost your connection to the server. Check your Internet Connection and Try again");
				dialogBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}					
				});

				dialogBuilder1.create().show();
			}
			if(extraeventID>0){
				disableviews();
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
	private void disableviews(){
		event_name.setKeyListener(null);
		event_desc.setKeyListener(null);
		date_deadline.setEnabled(false);
		time_deadline.setEnabled(false);
		priority.setEnabled(false);
	}
	private void enableviews(){
		event_name.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
		event_desc.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
		date_deadline.setEnabled(true);
		time_deadline.setEnabled(true);
		priority.setEnabled(true);
	}
}

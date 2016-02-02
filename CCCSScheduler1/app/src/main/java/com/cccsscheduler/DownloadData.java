package com.cccsscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.events_adapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

public class DownloadData {

	Context context;
    public static String url;
	String lastupdate="";
	int prio = -1;
	int server_id = -1;
	int local_id = -1;
	int user_id = -1;
	DBHelper helper;

	public void download(final Context context,int userid,String lastupdate) throws ParseException{
		this.context=context;
		this.user_id=userid;
		this.lastupdate=lastupdate;
		helper=new DBHelper(context);
        url= "http://bsit701.com/cccs_scheduler/download_data.php/"+"?user_id="+String.valueOf(userid)+"&&last_update="+this.lastupdate;
		JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					int success = response.getInt("success");
					if (success == 1) {
                        int counter=0;
						JSONArray ja = response.getJSONArray("MEMBERSHIP_REQUEST");
                        Log.d("counter",url);
						for (int i = 0; i < ja.length() ; i++) {

							JSONObject jobj = ja.getJSONObject(i);

							int notif_id=Integer.valueOf(jobj.getString("notification_id"));
							int group_id=Integer.valueOf(jobj.getString("group_id"));
							String gname=jobj.getString("group_name");
							String gdesc=jobj.getString("group_description");
							String leadername=jobj.getString("leader_name");
							String notifdate=jobj.getString("notification_date");
							int status=Integer.valueOf(jobj.getString("status"));

							if(helper.check_mrequest(group_id)){
								helper.manage_mrequest(notif_id, group_id, gname, gdesc, leadername, notifdate,status, 2);
							}
							else {
								helper.manage_mrequest(notif_id, group_id, gname, gdesc, leadername, notifdate,status, 1);
							}
                            if(status==0){
                                counter++;
                                Log.d("counter","friendrequest");
                            }
						} // for loop ends

						JSONArray jag_events = response.getJSONArray("group_events");


						for (int i = 0; i < jag_events.length() ; i++) {
							counter++;
                            Log.d("counter","group_events");
							JSONObject jobj = jag_events.getJSONObject(i);

							int event_id=Integer.valueOf(jobj.getString("event_id"));

							int group_id=Integer.valueOf(jobj.getString("group_id"));
							int userid=Integer.valueOf(jobj.getString("user_id"));
							String title=jobj.getString("title");
							String description=jobj.getString("description");
							String date=jobj.getString("date");
							String time=jobj.getString("time");
                            String [] t=time.split(":");
                            int hourOfDay=0;
                            int minute=0;

                            try {
                                hourOfDay=Integer.valueOf(t[0]);
                                minute=Integer.valueOf(t[1]);
                            }catch (Exception e){

                            }

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

							int priority=Integer.valueOf(jobj.getString("priority"));

                            try {
                                setalarm(event_id,title,description,date,strTime,hourOfDay,minute,priority);
                            } catch (ParseException e) {
                                Log.d("catch error", e + "");
                            }

                            String created_at=jobj.getString("created_at");
							int status=Integer.valueOf(jobj.getString("status"));
							if(helper.check_group_events(event_id)){
								Group_Events_Adapter event = new Group_Events_Adapter();
								event.setId(event_id);
								event.setName(title);
								event.setDescription(description);
								event.setdate_deadline(date);
								event.settime_deadline(strTime);
								event.setpriority(prio);
								event.setstatus(status);
								helper.updateGroup_event(event, 1);
							}
							else {
								helper.insertgroup_event(group_id, userid, title, description, date, strTime, priority, status, event_id);
							}
							
						} // for loop ends

						JSONArray jag_groups = response.getJSONArray("groups");
						for (int i = 0; i < jag_groups.length() ; i++) {
							JSONObject jobj = jag_groups.getJSONObject(i);
							int group_id=Integer.valueOf(jobj.getString("group_id"));
							String gname=jobj.getString("group_name");
							String gdesc=jobj.getString("group_description");
							int leader_id=Integer.valueOf(jobj.getString("leader_id"));
							String created_at=jobj.getString("created_at");
							if(helper.addNewGroup(gname, gdesc, leader_id, group_id)){
								
							}else {
								helper.update_group(gname, gdesc, leader_id, group_id);
							}
						} // for loop ends

						JSONArray jausers = response.getJSONArray("users");
						for (int i = 0; i < jausers.length() ; i++) {

							JSONObject jobj1 = jausers.getJSONObject(i);
							int user_id=Integer.valueOf(jobj1.getString("user_id"));
							String fname=jobj1.getString("fname");
							String mname=jobj1.getString("mname");
							String lname=jobj1.getString("lname");
							String gender=jobj1.getString("gender");
							String bdate=jobj1.getString("bdate");
							String address=jobj1.getString("address");
							String contactno=jobj1.getString("contactno");
							String username=jobj1.getString("username");
							String password=jobj1.getString("password");
							String email=jobj1.getString("email");
							String gmail=jobj1.getString("gmail");
							String created_at=jobj1.getString("created_at");
							String updated_at=jobj1.getString("updated_at");

							if(helper.searchUserID(user_id)){
								helper.updateUserinfo(fname, mname, lname, gender, bdate, address, username, password, email, contactno, user_id);
							}
							else {
								helper.sign_up(fname, mname, lname, gender, bdate, address, username, password, email, contactno, user_id);
							}


						}

                        JSONArray ja_personal_event = response.getJSONArray("personal_events");

                        for (int i = 0; i < ja_personal_event.length() ; i++) {

                            JSONObject jobj1 = ja_personal_event.getJSONObject(i);

                            int event_id=Integer.valueOf(jobj1.getString("event_id"));
                            String title=jobj1.getString("title");
                            String description=jobj1.getString("description");
                            String date=jobj1.getString("date");
                            String time=jobj1.getString("time");
                            int priority=Integer.valueOf(jobj1.getString("priority"));
                            String created_at=jobj1.getString("created_at");
                            String updated_at=jobj1.getString("updated_at");

                            Log.d("updated from server", updated_at);

                            String [] t=time.split(":");
                            int hourOfDay=0;
                            int minute=0;

                            hourOfDay=Integer.valueOf(t[0]);
                            minute=Integer.valueOf(t[1]);

                            String strTime = "";

                            try {
                                setalarm(event_id,title,description,date,strTime,hourOfDay,minute,priority);
                            } catch (ParseException e) {
                                Log.d("catch pevents", e+"");
                            }
                            if(hourOfDay>12){
                                strTime = strTime + String.format("%02d", (hourOfDay-12)) + ":" + String.format("%02d", minute)+" PM";
                            } else if(hourOfDay==12 && minute>0){
                                strTime = strTime + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)+" PM";
                            } else if(hourOfDay==0){
                                strTime = strTime + String.format("%02d", (hourOfDay+12))+ ":" + String.format("%02d", minute)+" AM";
                            } else {
                                strTime = strTime + String.format("%02d", hourOfDay)+ ":" + String.format("%02d", minute)+" AM";
                            }

                            if(helper.check_event(event_id)){
                                events_adapter event = new events_adapter();
                                event.setId(event_id);
                                event.setName(title);
                                event.setDescription(description);
                                event.setdate_deadline(date);
                                event.settime_deadline(strTime);
                                event.setpriority(prio);
                                event.setupdated_at(updated_at);
                                helper.updateTask(event);
                            }
                            else {
                                helper.addNewEvent(title,description,date,strTime,created_at,updated_at,priority,event_id);
                            }
                        }
						
						
						JSONArray jamembers= response.getJSONArray("group_members");
						for (int i = 0; i < jamembers.length() ; i++) {
							
							JSONObject jobj1 = jamembers.getJSONObject(i);
							int group_id=Integer.valueOf(jobj1.getString("group_id"));
							int user_id=Integer.valueOf(jobj1.getString("user_id"));
							String created_at=jobj1.getString("created_at");
							helper.insert_member(group_id, user_id);
						}
                        JSONArray ja_last_update= response.getJSONArray("last_update");
                        String s="";
                        for (int i = 0; i < ja_last_update.length() ; i++) {

                            JSONObject jobj1 = ja_last_update.getJSONObject(i);
                            s=jobj1.getString("last_update");
                        }
                        SharedPreferences sharedpreferenceslastupdate;
                        sharedpreferenceslastupdate = context.getSharedPreferences(login.SHAREDLASTUPDATE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editors = sharedpreferenceslastupdate.edit();
                        editors.putString(login.LASTUPDATE, s);
                        editors.commit();

						int count=MainActivity.mNotificationsCount;

                        Log.d("counter","notif count "+String.valueOf(count));
                        MainActivity.mNotificationsCount = count+counter;
                        MainActivity.main.invalidateOptionsMenu();
						//act.updateNotificationsBadge(count);
					} // if ends
					
				} catch (JSONException e) {
                    Log.d("catch error", e+"");
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(DownloadData.this.context, "Error", Toast.LENGTH_SHORT).show();
			}
		}) ;

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jreq);


	}
    private void setalarm(int ser_id,String str_name,String str_desc,String str_date,String str_time,int hr,int min,int prio) throws ParseException {
        Calendar cal=Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date eventdate = dateFormat.parse(str_date+" "+hr+":"+min+":00");

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date formattedDate = c2.getTime();
        /*String []arr=str_date.split("-");
        cal.set(Calendar.YEAR,Integer.valueOf(arr[0]));
        cal.set(Calendar.YEAR,Integer.valueOf(arr[1]));
        cal.set(Calendar.YEAR,Integer.valueOf(arr[2]));*/
        cal.set(Calendar.HOUR_OF_DAY, hr);
        cal.set(Calendar.MINUTE,min);
        cal.set(Calendar.SECOND, 00);
        if (eventdate.after(formattedDate)){
            if(prio==3){
                Intent intent = new Intent(context, alarm_activity.class);
                intent.putExtra("name", str_name);
                intent.putExtra("desc", str_desc);
                intent.putExtra("id", ser_id);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, ser_id, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
            }
            else {
                Intent intent = new Intent(context, alarmcontroller.class);
                intent.putExtra("id", ser_id);
                intent.putExtra("title", str_name);
                intent.putExtra("desc", str_desc);
                intent.putExtra("date", str_date);
                intent.putExtra("time", str_time);
                intent.putExtra("prio", prio);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ser_id, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
            }
        }

    }
	
}

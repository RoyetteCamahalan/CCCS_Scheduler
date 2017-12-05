package com.Custom_Adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Custom_Adapters.Events_Custom_Adapter.ViewHolder;
import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.Notif_getset;
import com.Getters_Setters.events_adapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cccsscheduler.AppController;
import com.cccsscheduler.DBHelper;
import com.cccsscheduler.DownloadData;
import com.cccsscheduler.R;
import com.cccsscheduler.alarm_activity;
import com.cccsscheduler.alarmcontroller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notification_custom extends BaseAdapter{
	public Context context;
	DBHelper helper;
	public ArrayList<Notif_getset> notifications;
	Notif_getset getset;
    ProgressDialog PD;
	public LayoutInflater inflater;
	int userid;
	private static String url= "http://bsit701.com/cccs_scheduler/accept_membership_request.php/";
	public Notification_custom(Context context,ArrayList<Notif_getset> notifications,int user_id){
		this.context = context;
        PD = new ProgressDialog(context);
		this.notifications = notifications;
		this.userid=user_id;
		helper=new DBHelper(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notifications.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notifications.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return notifications.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if(convertView==null){

			convertView = inflater.inflate(R.layout.notification_item, parent, false);

			//assign the data to the layout
			holder.txt_notifinfo = (TextView) convertView.findViewById(R.id.txt_notifinfo);
			holder.txt_time_date = (TextView) convertView.findViewById(R.id.txt_time);
			holder.btn_accept = (TextView) convertView.findViewById(R.id.btn_accept);
			holder.btn_decline = (TextView) convertView.findViewById(R.id.btn_decline);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}
		final Notif_getset notifs = (Notif_getset) getItem(position);

		
		holder.txt_time_date.setText(notifs.getnotifdate());
		int notiftype=notifs.getnotiftype();
        int status=notifs.getstatus();
		if(notiftype==1){
				holder.txt_notifinfo.setText(Html.fromHtml("<b>" + notifs.getleadername() + "</b> invited you to join <u><b>" +
				notifs.getName()+"</b></u>"));
				holder.btn_accept.setVisibility(View.VISIBLE);
				holder.btn_decline.setVisibility(View.VISIBLE);
		}
        else if(notiftype==2) {
            if(userid==notifs.getgroupid()){
                holder.txt_notifinfo.setText(Html.fromHtml("<b>You</b> added an event to <u><b>" +
                        notifs.getName()+"</b></u>"));
                holder.btn_accept.setVisibility(View.INVISIBLE);
                holder.btn_decline.setVisibility(View.INVISIBLE);
            }
            else {
                switch (status){
                    case 0:
                        holder.txt_notifinfo.setText(Html.fromHtml("<b>" + notifs.getleadername() + "</b> added an event to <u><b>" +
                                notifs.getName()+"</b></u>"));
                        holder.btn_accept.setVisibility(View.VISIBLE);
                        holder.btn_decline.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        holder.txt_notifinfo.setText(Html.fromHtml("<b>" + notifs.getleadername() + "</b> added an event to <u><b>" +
                                notifs.getName()+"</b></u>"));
                        holder.btn_accept.setVisibility(View.INVISIBLE);
                        holder.btn_decline.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }
        else if(notiftype==3){
            if(userid==notifs.getId()){
                holder.txt_notifinfo.setText(Html.fromHtml("<b>You</b> joined the group <u><b>" +
                        notifs.getName()+"</b></u>"));
            }
            else {
                holder.txt_notifinfo.setText(Html.fromHtml("<b>" + notifs.getleadername() + "</b> joined the group <u><b>" +
                        notifs.getName()+"</b></u>"));
            }

            holder.btn_accept.setVisibility(View.INVISIBLE);
            holder.btn_decline.setVisibility(View.INVISIBLE);
        }
		holder.btn_accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final TextView accept=(TextView) v;

				View parentRow = (View) v.getParent();
				final TextView decline=(TextView) parentRow.findViewById(R.id.btn_decline);
				View parentrow2=(View) parentRow.getParent();
				View relativelayout1=(View)parentrow2.findViewById(R.id.rel1);
				final TextView notifinfo=(TextView) relativelayout1.findViewById(R.id.txt_notifinfo);
				ListView listView = (ListView) parentrow2.getParent();
				final int position = listView.getPositionForView(parentrow2);


				PD.setMessage("Loading...");
				PD.show();
				getset=(Notif_getset) getItem(position);
				final int group_id=getset.getgroupid();
				final int notifid=getset.getId();
                if(getset.getnotiftype()==1){
                    JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url+"?user_id="+String.valueOf(userid)+"&&group_id="+String.valueOf(group_id)+"&&action_type=1", null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        int success = response.getInt("success");

                                        if (success == 1) {
                                            JSONArray group = response.getJSONArray("group");
                                            JSONObject jobj = group.getJSONObject(0);
                                            String group_name=jobj.getString("gname");
                                            String group_desc=jobj.getString("gdesc");
                                            int leader_id=Integer.valueOf(jobj.getString("leader_id"));

                                            //int current_leader_id=Integer.valueOf(jobj.getString("current_leader_id"));

                                            if(helper.addNewGroup(group_name, group_desc, leader_id, group_id)){
                                                Toast.makeText(context,"group" +group_name+" "+group_desc, Toast.LENGTH_SHORT).show();
                                            }
                                            JSONArray ja = response.getJSONArray("users");
                                            helper.updaterequeststatus(notifid, 1, 1);
                                            accept.setVisibility(View.INVISIBLE);
                                            decline.setVisibility(View.INVISIBLE);
                                            notifinfo.setText(Html.fromHtml("<b>You accepted " + getset.getleadername() + "'s</b> invitation to join <u><b>" +
                                                    getset.getName()+"</b></u>"));
                                            for (int i = 0; i < ja.length() ; i++) {

                                                JSONObject jobj1 = ja.getJSONObject(i);
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
                                                if(leader_id!=user_id){
                                                    helper.insert_member(group_id, user_id);
                                                }

                                            } // for loop ends
                                            helper.insert_member(group_id,userid);
                                            Toast.makeText(context,String.valueOf(group_id)+" "+String.valueOf(userid), Toast.LENGTH_SHORT).show();


                                            JSONArray events = response.getJSONArray("events");

                                            for (int i = 0; i < events.length() ; i++) {
                                                Toast.makeText(context,"sevents" , Toast.LENGTH_SHORT).show();
                                                JSONObject jobj1 = events.getJSONObject(i);
                                                int event_id=Integer.valueOf(jobj1.getString("event_id"));
                                                int user_id=Integer.valueOf(jobj1.getString("user_id"));
                                                String title=jobj1.getString("title");
                                                String description=jobj1.getString("description");
                                                String date_time=jobj1.getString("date_time");
                                                String created_at=jobj1.getString("created_at");
                                                String updated_at=jobj1.getString("updated_at");
                                                int priority=Integer.valueOf(jobj1.getString("priority"));
                                                int status=Integer.valueOf(jobj1.getString("status"));
                                                String arr[]=date_time.split(" w");
                                                String date=arr[0];
                                                String time=arr[1];
                                                String arrtime[]=time.split(":");
                                                int hourOfDay=Integer.valueOf(arr[0]);
                                                int minute=Integer.valueOf(arr[1]);
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
                                                if(helper.insertgroup_event(group_id, user_id, title, description, date, strTime, priority,status, event_id)>0){
                                                }
                                            }
                                            Toast.makeText(context,"events" , Toast.LENGTH_SHORT).show();
                                        } // if ends

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    PD.dismiss();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "erroronrequest", Toast.LENGTH_SHORT).show();
                            PD.dismiss();
                        }
                    }) ;

                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(jreq);
                }
                else {
                    managepost(notifs.getId(),1);
                    accept.setVisibility(View.INVISIBLE);
                    decline.setVisibility(View.INVISIBLE);
                    setalarm(notifs.getId());

                }

			}
		});
        holder.btn_decline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView decline=(TextView) v;
                View parentRow = (View) v.getParent();
                final TextView accept=(TextView) parentRow.findViewById(R.id.btn_accept);
                View parentrow2=(View) parentRow.getParent();
                View relativelayout1=(View)parentrow2.findViewById(R.id.rel1);
                final TextView notifinfo=(TextView) relativelayout1.findViewById(R.id.txt_notifinfo);
                ListView listView = (ListView) parentrow2.getParent();
                final int position = listView.getPositionForView(parentrow2);


                PD.setMessage("Loading...");
                PD.show();
                getset=(Notif_getset) getItem(position);
                final int group_id=getset.getgroupid();
                final int notifid=getset.getId();
                if(notifs.getnotiftype()==1){
                    JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url+"?user_id="+String.valueOf(userid)+"&&group_id="+String.valueOf(group_id)+"&&action_type=1", null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        int success = response.getInt("success");
                                        if(success==1){
                                            helper.updaterequeststatus(notifid, 1, 2);
                                            accept.setVisibility(View.INVISIBLE);
                                            decline.setVisibility(View.INVISIBLE);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    PD.dismiss();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "erroronrequest", Toast.LENGTH_SHORT).show();
                            PD.dismiss();
                        }
                    }) ;

                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(jreq);
                }
                else {
                    managepost(notifs.getId(),2);
                    accept.setVisibility(View.INVISIBLE);
                    decline.setVisibility(View.INVISIBLE);
                }
            }
        });
		return convertView;
	}
	public static class ViewHolder {
		public TextView txt_notifinfo;
		public TextView txt_time_date;
		public TextView btn_accept;
		public TextView btn_decline;
	}
    private void managepost(final int event_id, final int actiontype){
        String tag_string_req = "req_register";
        String url= "http://bsit701.com/cccs_scheduler/manage_post.php";
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                    int a=Integer.valueOf(response);
                    if(a==1 || a==2){
                        helper.updatepoststatus(event_id,a);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                PD.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(context,
                        "failed to insert", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id", String.valueOf(event_id));
                params.put("action_type", String.valueOf(actiontype));
                return params;

            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
    private void setalarm(int ser_id){
        Calendar cal=Calendar.getInstance();
        ArrayList<HashMap<String, String>> event_info=helper.get_event_info(ser_id);
        String str_name="";
        String str_desc="";
        String str_date="";
        String str_time="";
        int prio=0;
        if(event_info.size()>0){
            str_name=event_info.get(0).get(helper.TITLE);
            str_desc=event_info.get(0).get(helper.DESCRIPTION);
            str_date=event_info.get(0).get(helper.EVENTS_DATE);
            str_time=event_info.get(0).get(helper.EVENTS_TIME);
            prio=Integer.valueOf(event_info.get(0).get(helper.PRIORITY));
            String []arr=str_date.split("-");
            String []arr3=str_time.split(" ");
            String []arr2=arr3[0].split(":");
            cal.set(Calendar.YEAR,Integer.valueOf(arr[0]));
            cal.set(Calendar.YEAR,Integer.valueOf(arr[1]));
            cal.set(Calendar.YEAR,Integer.valueOf(arr[2]));
            cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(arr2[0]));
            cal.set(Calendar.MINUTE,Integer.valueOf(arr2[1]));
            cal.set(Calendar.SECOND, 00);
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

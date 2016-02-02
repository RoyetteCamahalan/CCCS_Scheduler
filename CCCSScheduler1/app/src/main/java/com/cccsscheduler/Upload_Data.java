package com.cccsscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.Getters_Setters.events_adapter2;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class Upload_Data {
	Context context;
	events_adapter2 adapter=new events_adapter2();
	private static String url= "http://bsit701.com/cccs_scheduler/personal_events.php";
	String str_name,str_desc,str_date, str_time;
	String action="";
	String lastupdate="";
	int prio=-1;
	int server_id=-1;
	int local_id=-1;
	int user_id=-1;
	DBHelper helper;
	public boolean upload(Context context,int userid,String lastupdate) throws ParseException{
		this.context=context;
		this.user_id=userid;
		this.lastupdate=lastupdate;
		helper=new DBHelper(context);
		update_personal_events();

		return false;

	}
	private void update_personal_events() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<events_adapter2> events = helper.geteventsforupload(user_id, dateFormat.parse(lastupdate));
		//Toast.makeText(context, String.valueOf(events.size()), Toast.LENGTH_SHORT).show();
		if(events.size()>0){
			int j=0;

			while (events.size()>j) {
				adapter=events.get(j);
				j++;
				local_id=adapter.getId();
				str_name=adapter.getName();
				str_desc=adapter.getDescription();
				str_date=adapter.getdate_deadline();
				str_time=adapter.gettime_deadline();

				String [] time=str_time.split(" ");
				String time2=time[0];
				String time3=time[1];

				String [] time4=time2.split(":");
				int hr=Integer.valueOf(time4[0]);
				if(time3.equals("PM") && hr>=1){
					hr=+12;
				}
				str_time=String.valueOf(hr)+":"+time4[1]+":00";
				prio=adapter.getpriority();
				server_id=adapter.getserver_id();
				if (server_id>0)
				{
					action="2";
				}
				else {
					action="1";
				}
				Toast.makeText(context, action +String.valueOf(server_id), Toast.LENGTH_SHORT).show();
				String tag_string_req = "req_register";
				StringRequest strReq = new StringRequest(Request.Method.POST, url,
						new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						
						try {
							String [] arr=response.split(" ");
							int local_id=Integer.valueOf(arr[0]);
							int ser_id=Integer.valueOf(arr[1]);
							Toast.makeText(Upload_Data.this.context,
									String.valueOf(ser_id)+" "+String.valueOf(local_id), Toast.LENGTH_SHORT).show();
							if(action.equals("1")){
								DBHelper helper=new DBHelper(Upload_Data.this.context);
								helper.updateserver_id(1,local_id, ser_id);
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(Upload_Data.this.context,
								"Failed to Insert", Toast.LENGTH_SHORT).show();
					}
				}) {
					@Override
					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
						params.put("action_type", action);
						params.put("user_id", String.valueOf(user_id));
						if(action.equals("1")){
							params.put("event_id", String.valueOf(local_id));
						}
						else {
							params.put("event_id", String.valueOf(server_id));
						}
						
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
			
		}
	}


}

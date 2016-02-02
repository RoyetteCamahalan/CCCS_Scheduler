package com.cccsscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.Custom_Adapters.Events_Custom_Adapter;
import com.Getters_Setters.events_adapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Events extends Fragment{
	private static String url= "http://bsit701.com/cccs_scheduler/insert_dummy.php";
	ImageButton add_event;
	ListView lst_events;
	public Events_Custom_Adapter eventadapter=null;
	events_adapter event;
	int event_position;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View event_view=inflater.inflate(R.layout.events, container,false);
		add_event=(ImageButton) event_view.findViewById(R.id.icon_addevent);
		lst_events=(ListView) event_view.findViewById(R.id.lst_events);
		lst_events.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				event=(events_adapter) eventadapter.getItem(position);
				Intent intent = new Intent(getActivity(),New_Event.class);
				//load extras from the task
				
				intent.putExtra("event_id", event.getId());
				intent.putExtra("event_name", event.getName());
				intent.putExtra("event_desc", event.getDescription());
				intent.putExtra("event_date_deadline", event.getdate_deadline());
				intent.putExtra("event_time_deadline", event.gettime_deadline());
				intent.putExtra("event_priority", event.getpriority());
				startActivity(intent);
			}
		});
		registerForContextMenu(lst_events);
		add_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),New_Event.class);
				startActivity(intent);
				/*NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(getActivity())
				.setSmallIcon(R.drawable.logo)
				.setContentTitle("Adding nOTIFICATION")
				.setContentText("YOU ARE ABOUT TO ADD NEW EVENT");
				Intent intent=new Intent(getActivity(),New_Event.class);
				TaskStackBuilder stackbuilder=TaskStackBuilder.create(getActivity());
				stackbuilder.addParentStack(New_Event.class);
				stackbuilder.addNextIntent(intent);
				PendingIntent resultpendingintent=stackbuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultpendingintent);
				mBuilder.setAutoCancel(true);
				NotificationManager mNotificationManager =
					    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(0, mBuilder.build());
				//startActivity(intent);
				final ProgressDialog pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Loading...");
				pDialog.show();     
				String tag_string_req = "req_register";

				StringRequest strReq = new StringRequest(Request.Method.POST, url,
						new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						pDialog.dismiss();
						Toast.makeText(getActivity(),
								response,
								Toast.LENGTH_SHORT).show();


					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.dismiss();
						Toast.makeText(getActivity(),
								"failed to insert", Toast.LENGTH_SHORT).show();
					}
				}) {
					@Override
					protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
						params.put("dummy", "this is the dummy");
						return params;
					}
				};
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(strReq, tag_string_req);*/
			}
		});
		return event_view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DBHelper helper=new DBHelper(getActivity());
		ArrayList<events_adapter> events = helper.getevents();
		eventadapter=new Events_Custom_Adapter(getActivity(),events);
		lst_events.setAdapter(eventadapter);
	}
	
	/*@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		event = (events_adapter) eventadapter.getItem(info.position);
		event_position=info.position;
		switch (item.getItemId()) {
		case R.id.context_action_delete:
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
			dialogBuilder.setTitle(R.string.title_to_delete_event);
			dialogBuilder.setMessage(R.string.ask_to_delete_event);
			dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					DBHelper dbHelper = new DBHelper(getActivity());		
					dbHelper.deleteEvent(event.getId());

					//delete the task from the adapter
					eventadapter.removeEvent(event_position);
					//update the listview
					eventadapter.notifyDataSetChanged();
				}					
			});

			//set negative button and it's click listener
			dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}					
			});

			dialogBuilder.create().show();
			

			return true;
		return super.onContextItemSelected(item);
	}*/
	
	

}

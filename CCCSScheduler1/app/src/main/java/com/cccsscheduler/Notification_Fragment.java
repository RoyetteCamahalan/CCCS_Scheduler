package com.cccsscheduler;

import java.util.ArrayList;

import com.Custom_Adapters.Events_Custom_Adapter;
import com.Custom_Adapters.Notification_custom;
import com.Getters_Setters.Notif_getset;
import com.Getters_Setters.events_adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Notification_Fragment extends Fragment {
	public Notification_custom notif_custom=null;
	ListView lst_notification;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View Notif_layout=inflater.inflate(R.layout.notification_layout, container, false);
		lst_notification=(ListView) Notif_layout.findViewById(R.id.lst_notifs);
		return Notif_layout;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DBHelper helper=new DBHelper(getActivity());
		ArrayList<Notif_getset> notifs =helper.getnotifications();
		SharedPreferences sharepref=getActivity().getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
		int user_id=sharepref.getInt(login.USERPREFID, 0);
		notif_custom=new Notification_custom(getActivity(), notifs,user_id);
		lst_notification.setAdapter(notif_custom);
	}
}

package com.cccsscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class alarmcontroller extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String title=intent.getStringExtra("title");
		String desc=intent.getStringExtra("desc");
		String date=intent.getStringExtra("date");
		String time=intent.getStringExtra("time");
		int prio=intent.getIntExtra("prio", -1);
		int id=intent.getIntExtra("id", -1);
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
		NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle("Event Due")
		.setContentText(title);
		Intent intent1=new Intent(context,New_Event.class);
		intent1.putExtra("event_id", id);
		intent1.putExtra("event_name", title);
		intent1.putExtra("event_desc", desc);
		intent1.putExtra("event_date_deadline", date);
		intent1.putExtra("event_time_deadline", time);
		intent1.putExtra("event_priority", prio);
		TaskStackBuilder stackbuilder=TaskStackBuilder.create(context);
		stackbuilder.addParentStack(New_Event.class);
		stackbuilder.addNextIntent(intent1);
		PendingIntent resultpendingintent=stackbuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultpendingintent);
		mBuilder.setAutoCancel(true);
		NotificationManager mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(id, mBuilder.build());
		
	}

	
	

}

package com.cccsscheduler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service{
    public static final long NOTIFY_INTERVAL = 10 * 2000;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void onCreate() {
        super.onCreate();
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    DownloadData download = new DownloadData();
                    Upload_Data upload = new Upload_Data();
                    SharedPreferences shared = getSharedPreferences(login.USERPREFERENCES, MODE_PRIVATE);
                    int user_id = shared.getInt(login.USERPREFID, 0);
                    try {

                        SharedPreferences sharedpreferenceslastupdate;
                        sharedpreferenceslastupdate = getSharedPreferences(login.SHAREDLASTUPDATE, Context.MODE_PRIVATE);
                        String lastupdate = sharedpreferenceslastupdate.getString(login.LASTUPDATE, "2012-12-12 12:02:00");
                        Log.d("counter", lastupdate);
                        System.out.print("lastUpdate: "+lastupdate);
                        upload.upload(MyService.this, user_id, lastupdate);
                        download.download(MyService.this, user_id, lastupdate);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
        }

    }
}

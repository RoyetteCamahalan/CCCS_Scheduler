package com.cccsscheduler;

import java.io.IOException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class alarm_activity extends Activity implements View.OnClickListener {
	private MediaPlayer mMediaPlayer; 
	String title;
	String desc;
	Intent intent1;
	int event_id;
	Dialog dialog;

	TextView txt_title,txt_description;
	Button btn_snooze,btn_dismiss;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		intent1 = new Intent(this, alarm_activity.class);

		title = intent.getStringExtra("name");
		desc = intent.getStringExtra("desc");
		event_id=intent.getIntExtra("id", 0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.TYPE_TOAST,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);


		dialog=new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.alarm_act);
		dialog.show();
		txt_title= (TextView) dialog.findViewById(R.id.title);
		txt_description= (TextView) dialog.findViewById(R.id.description);
		btn_dismiss= (Button) dialog.findViewById(R.id.btn_dismiss);
		btn_snooze= (Button) dialog.findViewById(R.id.btn_snooze);
		btn_dismiss.setOnClickListener(this);
		btn_snooze.setOnClickListener(this);

		txt_title.setText(title);
		txt_description.setText(desc);
 
        
 
        playSound(this, getAlarmUri());
    }
 
    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }
 
    //Get an alarm sound. Try for an alarm. If none set, try notification, 
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_dismiss:
				mMediaPlayer.stop();
				finish();
				break;
			case R.id.btn_snooze:
				intent1.putExtra("title",title);
				intent1.putExtra("desc",desc);
				intent1.putExtra("id", event_id);
				//PendingIntent pendingIntent = PendingIntent.getService(this, 0, PendingIntent.FLAG_UPDATE_CURRENT);
				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), event_id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
				long currentTimeMillis = System.currentTimeMillis();
				long nextUpdateTImeMillis = currentTimeMillis + 1 * DateUtils.MINUTE_IN_MILLIS;
				Time nextUpdateTime = new Time();
				nextUpdateTime.set(nextUpdateTImeMillis);

				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC, nextUpdateTImeMillis, pendingIntent );
				mMediaPlayer.stop();
				finish();
				break;
		}
	}
}

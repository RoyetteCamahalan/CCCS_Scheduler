package com.Network;

import com.cccsscheduler.MainActivity;
import com.cccsscheduler.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class Download_Data extends AsyncTask<String, String, String>{
	
	
	private Context context;
	private ProgressDialog mProgressDialog;
	public Download_Data(Context context) 
	{
	    this.context = context;
	     mProgressDialog = new ProgressDialog(context);
	     mProgressDialog.setMessage("Downloading file..");
	     mProgressDialog.setIndeterminate(false);
	     mProgressDialog.setMax(100);
	     mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	     mProgressDialog.setCancelable(true);

	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

}

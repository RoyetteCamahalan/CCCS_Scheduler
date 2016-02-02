package com.cccsscheduler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class forgot_pass extends Activity  {
	JSONParser jsonParser = new JSONParser();
	DBHelper dbHelper=new DBHelper(this);
	TextView btn_forgot;
	EditText uname;
	ImageButton save;
	String str_pass;
	private int extraeventID = 0;
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_pass);
		uname =(EditText) findViewById(R.id.txt_uname);
		//btn_forgot = (TextView) findViewById(R.id.txt_signin);
		save = (ImageButton) findViewById(R.id.imgbtn_save);


		Intent intent=getIntent();
		String a=intent.getStringExtra("forgot_pass");

		//uname.setText(a);

		//save.setOnClickListener(this);
	}
	public void process(View view)
	{
			Intent intent=null,chooser=null;

		if(view.getId()==R.id.imgbtn_save)
		{
		//uname.getText().toString();
		intent=new Intent(Intent.ACTION_SEND);
		intent.setData(Uri.parse("junix"));
		String[] to={"royette.camahalan@gmail.com","junixcordero@gmail.com"};
		intent.putExtra(Intent.EXTRA_EMAIL, to);
		intent.putExtra(Intent.EXTRA_SUBJECT, "The password will be send to your account.");
		//intent.putExtra(Intent.EXTRA_TEXT, "ayaw nag kalimti");
		intent.setType("message/rfc822");
		chooser=Intent.createChooser(intent, "Send mail");
		startActivity(chooser);
		}

	}
	
}

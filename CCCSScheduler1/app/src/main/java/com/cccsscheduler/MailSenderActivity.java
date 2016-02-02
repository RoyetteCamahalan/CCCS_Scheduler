package com.cccsscheduler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MailSenderActivity extends Activity {
	Button send;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);

        send = (Button) this.findViewById(R.id.imgbtn_save);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {   
                    GMailSender sender = new GMailSender("junixcordero@gmail.com", "password");
                    sender.sendMail("This is Subject",   
                            "This is Body",   
                            "junixcordero@gmail.com",   
                            "junixcordero@gmail.com");  
                    Toast.makeText(getApplicationContext(), "get", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {   
                    Log.e("SendMail", e.getMessage(), e);   
                } 

            }
        });

    }
}

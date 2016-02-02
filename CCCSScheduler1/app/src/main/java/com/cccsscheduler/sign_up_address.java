package com.cccsscheduler;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


public class sign_up_address extends Activity implements OnClickListener, OnDateSetListener {
    EditText ET_fname, ET_mname, ET_lname, ET_bdate, ET_address, ET_contactno;
    ImageButton btnsave, btnback;
    RadioButton rd_male, rd_female;
    String fname, mname, lname, bdate, address, gender, contactno;
    int checker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_address);

        Intent signup_add = getIntent();
        fname = signup_add.getStringExtra("fname");
        mname = signup_add.getStringExtra("mname");
        lname = signup_add.getStringExtra("lname");
        bdate = signup_add.getStringExtra("bdate");
        address = signup_add.getStringExtra("address");
        gender = signup_add.getStringExtra("gender");
        contactno = signup_add.getStringExtra("contactno");

        ET_fname = (EditText) findViewById(R.id.txt_fname);
        ET_mname = (EditText) findViewById(R.id.txt_mname);
        ET_lname = (EditText) findViewById(R.id.txt_lname);
        ET_address = (EditText) findViewById(R.id.ET_address);
        ET_contactno = (EditText) findViewById(R.id.txt_contact);
        ET_bdate = (EditText) findViewById(R.id.txt_bdate);
        rd_male = (RadioButton) findViewById(R.id.rd_male);
        rd_female = (RadioButton) findViewById(R.id.rd_female);
        btnback = (ImageButton) findViewById(R.id.imgbtn_back);
        btnsave = (ImageButton) findViewById(R.id.imgbtn_save);
        ET_bdate.setOnClickListener(this);

        ET_fname.setText(fname);
        ET_mname.setText(mname);
        ET_lname.setText(lname);
        ET_address.setText(address);
        ET_bdate.setText(bdate);
        ET_contactno.setText(contactno);
        if (gender.equals("male")) {
            rd_male.setChecked(true);
        } else {
            rd_female.setChecked(true);
        }

        btnback.setOnClickListener(this);
        btnsave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        fname = ET_fname.getText().toString();
        mname = ET_mname.getText().toString();
        lname = ET_lname.getText().toString();
        address = ET_address.getText().toString();
        bdate = ET_bdate.getText().toString();
        contactno = ET_contactno.getText().toString();

        if (rd_male.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }

        switch (v.getId()) {
            case R.id.txt_bdate:
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
                break;
            case R.id.imgbtn_back:

                Intent intent = new Intent();
                intent.putExtra("issave", 0);
                intent.putExtra("fname", fname);
                intent.putExtra("mname", mname);
                intent.putExtra("lname", lname);
                intent.putExtra("address", address);
                intent.putExtra("bdate", bdate);
                intent.putExtra("gender", gender);
                intent.putExtra("contactno", contactno);
                setResult(1, intent);
                finish();
                break;

            case R.id.imgbtn_save:

                //validation here
                if (fname.matches("")) {
                    ET_fname.setError("Enter Firstname");
                }

                if (lname.matches("")) {
                    ET_lname.setError("Enter Lastname");
                }

                if (address.matches("")) {
                    ET_address.setError("Enter Address");
                }

                if (bdate.matches("")) {
                    ET_bdate.setError("Enter Birthdate");
                } else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("issave", 1);
                    intent1.putExtra("fname", fname);
                    intent1.putExtra("mname", mname);
                    intent1.putExtra("lname", lname);
                    intent1.putExtra("address", address);
                    intent1.putExtra("bdate", bdate);
                    intent1.putExtra("gender", gender);
                    intent1.putExtra("contactno", contactno);
                    setResult(1, intent1);
                    finish();
                    break;
                }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub
        ET_bdate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
    }
}

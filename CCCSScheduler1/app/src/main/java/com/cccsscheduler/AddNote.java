package com.cccsscheduler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User PC on 9/28/2015.
 */
public class AddNote extends Activity {
    EditText title, note;

    MenuItem item_save, item_cancel, item_edit, item_delete;

    DBHelper db;

    int extraeventID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.note);

        db = new DBHelper(this);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        item_save = menu.findItem(R.id.save);
        item_cancel = menu.findItem(R.id.cancel);
        item_edit = menu.findItem(R.id.menu_edit);
        item_delete = menu.findItem(R.id.menu_delete);

        if (extraeventID > 0) {
            item_save.setVisible(false);
            item_cancel.setVisible(false);
            item_edit.setVisible(true);
            item_delete.setVisible(true);
        } else {
            item_save.setVisible(true);
            item_cancel.setVisible(false);
            item_edit.setVisible(false);
            item_delete.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                String s_title = title.getText().toString();
                String s_note = note.getText().toString();
                int check = 0;

                if (s_title.equals("")) {
                    title.setError("Field required");
                    check++;
                }
                if (s_note.equals("")) {
                    note.setError("Field required");
                    check++;
                }

                if (check == 0) {
                    if (db.insertNote(s_title, s_note)) {
                        this.finish();
                    } else {
                        Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.cancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

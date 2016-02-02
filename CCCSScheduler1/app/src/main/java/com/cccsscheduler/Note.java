package com.cccsscheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Note extends Fragment implements View.OnClickListener {
    ListView listOfNotes;
    ImageButton icon_addevent;
    TextView title, note;

    ArrayList<HashMap<String, String>> getNotesFromDB;

    NotesAdapter adapter;
    DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View notes = inflater.inflate(R.layout.notes, container, false);

        listOfNotes = (ListView) notes.findViewById(R.id.listOfNotes);
        icon_addevent = (ImageButton) notes.findViewById(R.id.icon_addevent);

        db = new DBHelper(getActivity());

        icon_addevent.setOnClickListener(this);

        return notes;
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), AddNote.class));
    }

    @Override
    public void onResume() {
        super.onResume();

        getNotesFromDB = db.listOfNotes();
        adapter = new NotesAdapter(getActivity(), R.layout.item_notes, getNotesFromDB);
        listOfNotes.setAdapter(adapter);
    }

    private class NotesAdapter extends ArrayAdapter {
        LayoutInflater inflater;

        public NotesAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
            super(context, resource, objects);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = inflater.inflate(R.layout.item_notes, parent, false);

            title = (TextView) v.findViewById(R.id.title);
            note = (TextView) v.findViewById(R.id.note);

            title.setText(getNotesFromDB.get(position).get(db.TITLE));
            String notes = getNotesFromDB.get(position).get(db.DESCRIPTION);

            if (notes.length() >= 35) {
                note.setText(notes.substring(0, 34) + "...");
            } else {
                note.setText(notes);
            }

            return v;
        }
    }
}

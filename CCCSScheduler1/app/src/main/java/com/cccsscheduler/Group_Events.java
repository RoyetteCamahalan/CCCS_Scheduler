package com.cccsscheduler;

import java.util.ArrayList;

import com.Custom_Adapters.Events_Custom_Adapter;
import com.Custom_Adapters.Group_Events_CustomAdapter;
import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.events_adapter;
import com.Getters_Setters.groups_adapter;
import com.Network.miscellaneous;
import com.cccsscheduler.R;
import com.cccsscheduler.R.id;
import com.cccsscheduler.R.layout;
import com.cccsscheduler.R.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class Group_Events extends Fragment {
    ImageButton imgbtn_add;
    private Group_Events_CustomAdapter Geventadapter = null;
    Group_Events_Adapter event;
    private int group_id = -1;
    private int leader_id = -1;
    ListView lstG_events = null;
    int event_position;

    public static Group_Events newInstance(int group_id, int leader_id) {
        Group_Events fragmentFirst = new Group_Events();
        Bundle bundle=new Bundle();
        bundle.putInt("group_id", group_id);
        bundle.putInt("leader_id", leader_id);
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        group_id = getArguments().getInt("group_id");
        leader_id = getArguments().getInt("leader_id");
        View v = inflater.inflate(R.layout.group_events_layout, container, false);
        lstG_events = (ListView) v.findViewById(R.id.lst_group_events);
        registerForContextMenu(lstG_events);
        lstG_events.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), New_Group_Event.class);
                event = (Group_Events_Adapter) Geventadapter.getItem(position);
                //load extras from the task
                intent.putExtra("group_id", group_id);
                intent.putExtra("event_id", event.getId());
                intent.putExtra("userID", event.getuserId());
                intent.putExtra("event_name", event.getName());
                intent.putExtra("event_desc", event.getDescription());
                intent.putExtra("event_date_deadline", event.getdate_deadline());
                intent.putExtra("event_time_deadline", event.gettime_deadline());
                intent.putExtra("event_priority", event.getpriority());
                startActivity(intent);
            }
        });
        imgbtn_add = (ImageButton) v.findViewById(R.id.icon_addgroupevent);
        imgbtn_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                miscellaneous misc = new miscellaneous();
                if (misc.checkInternet(getActivity())) {
                    Intent intent = new Intent(getActivity(), New_Group_Event.class);
                    intent.putExtra("group_id", group_id);
                    intent.putExtra("leader_id", leader_id);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setTitle("No Internet Connection");
                    dialogBuilder.setMessage("You are not connected to the server. Check your Internet Connection and Try again");
                    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    dialogBuilder.create().show();
                }

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        DBHelper helper = new DBHelper(getActivity());
        ArrayList<Group_Events_Adapter> Gevents = helper.get_group_events(group_id);
        SharedPreferences shared=getActivity().getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);
        int user_id=shared.getInt(login.USERPREFID,0);
        Geventadapter = new Group_Events_CustomAdapter(getActivity(), Gevents,user_id);
        lstG_events.setAdapter(Geventadapter);
        Log.d("events", Gevents + "");
    }

}

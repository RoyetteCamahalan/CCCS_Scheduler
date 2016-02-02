package com.cccsscheduler;

import java.util.ArrayList;

import com.Custom_Adapters.member_custom;
import com.Getters_Setters.member_getset;
import com.Network.miscellaneous;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class Group_Members extends Fragment {
    ImageButton addmember;
    private int group_id = -1;
    private int leader_id = -1;
    member_custom Member_custom;
    member_getset memberGetset;
    ListView lst_members;
    public static Group_Members newInstance(int group_id, int leader_id) {
        Group_Members fragmentFirst = new Group_Members();
        Bundle bundle=new Bundle();
        bundle.putInt("group_id", group_id);
        bundle.putInt("leader_id", leader_id);
        fragmentFirst.setArguments(bundle);
        return fragmentFirst;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        group_id = getArguments().getInt("group_id");
        leader_id = getArguments().getInt("leader_id");
        View v = inflater.inflate(R.layout.group_members_layout, container, false);

        addmember = (ImageButton) v.findViewById(R.id.icon_addgroupmember);
        lst_members = (ListView) v.findViewById(R.id.lst_group_members);
        addmember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                miscellaneous misc = new miscellaneous();
                if (misc.checkInternet(getActivity())) {
                    Intent intent = new Intent(getActivity(), AddMember.class);
                    intent.putExtra("group_id", group_id);
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
        lst_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),MemberProfile.class);
                memberGetset= (member_getset) Member_custom.getItem(position);
                intent.putExtra("user_id",memberGetset.getId());
                intent.putExtra("leader_id",leader_id);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper helper = new DBHelper(getActivity());

        ArrayList<member_getset> members = helper.get_group_member(group_id);
        SharedPreferences sharedpref = getActivity().getSharedPreferences(login.USERPREFERENCES, Context.MODE_PRIVATE);

        int user_id = sharedpref.getInt(login.USERPREFID, 0);
        Member_custom = new member_custom(getActivity(), members, group_id, user_id);
        lst_members.setAdapter(Member_custom);
    }
}

package com.cccsscheduler;

import java.util.ArrayList;

import com.cccsscheduler.R;
import com.cccsscheduler.R.id;
import com.cccsscheduler.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Group_About extends Fragment {
	public int group_id=-1;
	TextView txt_head,txt_group_name,txt_group_desc,txt_created_by,txt_created_at,txt_current_leader;
	ArrayList<String> group_info;
    public static Group_About newInstance(int group_id, int leader_id) {
        Group_About fragmentFirst = new Group_About();
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
		group_id=getArguments().getInt("group_id");
		View v=inflater.inflate(R.layout.about_group_layout, container,false);
		txt_head	=(TextView) v.findViewById(R.id.txt_about_group_head);
		txt_group_name	=(TextView) v.findViewById(R.id.txt_a_group_name);
		txt_group_desc	=(TextView) v.findViewById(R.id.txt_a_group_desc);
		txt_created_by	=(TextView) v.findViewById(R.id.txt_created_by);
		txt_current_leader	=(TextView) v.findViewById(R.id.txt_current_leader);
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DBHelper helper=new DBHelper(getActivity());
		group_info=helper.getgroupinfo(group_id);
		
		if (group_info.size()>0){
			txt_head.setText(" About "+group_info.get(0));
			txt_group_name.setText(group_info.get(0));
			txt_group_desc.setText(group_info.get(1));
			txt_created_by.setText(group_info.get(2));
			txt_current_leader.setText(group_info.get(3));
		}
	}
}

package com.Custom_Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Custom_Adapters.add_user_custom.ViewHolder;
import com.Getters_Setters.member_getset;
import com.Getters_Setters.users_getset;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cccsscheduler.AppController;
import com.cccsscheduler.R;

public class member_custom  extends BaseAdapter{
	private static String url= "http://bsit701.com/cccs_scheduler/add_member.php";
	
	public Context context;
	public ArrayList<member_getset> members;
	public LayoutInflater inflater;
	public int groupid;
	public int user_id;
	member_getset member ;
	public member_custom(Context context, ArrayList<member_getset> members,int groupid,int user_id){
		this.context = context;
		this.members = members;
		this.user_id=user_id;
		this.groupid=groupid;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return members.size();
	}
	public void removeEvent(int position){
		members.remove(position);
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return members.get(position);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if(convertView==null){

			convertView = inflater.inflate(R.layout.group_member_item, parent, false);

			//assign the data to the layout
			holder.userNameTV = (TextView) convertView.findViewById(R.id.txt_member_fullname);
			holder.UserSaddressTV = (TextView) convertView.findViewById(R.id.txt_member_address);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}
		member = (member_getset) getItem(position);
		if(member.getId()==user_id){
			convertView.setBackgroundColor(Color.parseColor("#4CBB17"));
		}
		else{
			convertView.setBackgroundColor(Color.WHITE);
		}
		holder.userNameTV.setText(member.getName());
		holder.UserSaddressTV.setText(member.getAddress());
		return convertView;
	}
	public static class ViewHolder {
		public TextView userNameTV;
		public TextView UserSaddressTV;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return members.get(position).getId();
	}
	

}


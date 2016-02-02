package com.Custom_Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.events_adapter;
import com.cccsscheduler.R;

public class Group_Events_CustomAdapter extends BaseAdapter{
	public Context context;
	public ArrayList<Group_Events_Adapter> events;
	public LayoutInflater inflater;
	int user_id=-1;
	
	public Group_Events_CustomAdapter(Context context, ArrayList<Group_Events_Adapter > Gevents,int user_id){
		this.context = context;
		this.events = Gevents;
		this.user_id=user_id;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}
	public void removeEvent(int position){
		events.remove(position);
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return events.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if(convertView==null){

			convertView = inflater.inflate(R.layout.group_events_item, parent, false);

			//assign the data to the layout
			holder.eventNameTV = (TextView) convertView.findViewById(R.id.Gevent_name);
			holder.eventDescriptionTV = (TextView) convertView.findViewById(R.id.Gevent_description);
			holder.eventDeadlineTV = (TextView) convertView.findViewById(R.id.Gevent_deadline);
            holder.eventPostedbyTV= (TextView) convertView.findViewById(R.id.txt_postby);
			convertView.setTag(holder);

		} else {
            holder = (ViewHolder) convertView.getTag();
        }

		Group_Events_Adapter Gevent = (Group_Events_Adapter) getItem(position);
        if(user_id==Gevent.getuserId()){
            holder.eventPostedbyTV.setText("Posted by: You");
        }else{
            holder.eventPostedbyTV.setText("Posted by: "+Gevent.getposted_by());
        }
		holder.eventNameTV.setText(Gevent.getName());
		holder.eventDescriptionTV.setText(Gevent.getDescription());
		holder.eventDeadlineTV.setText(Gevent.getdate_deadline()+" "+Gevent.gettime_deadline());
		return convertView;
	}
	public static class ViewHolder {
		public TextView eventNameTV;
		public TextView eventDescriptionTV;
		public TextView eventDeadlineTV;
        public TextView eventPostedbyTV;
	}
}


package com.Custom_Adapters;

import java.util.ArrayList;

import com.Getters_Setters.events_adapter;
import com.cccsscheduler.R;
import com.cccsscheduler.R.id;
import com.cccsscheduler.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Events_Custom_Adapter extends BaseAdapter{
	public Context context;
	public ArrayList<events_adapter> events;
	public LayoutInflater inflater;
	
	
	public Events_Custom_Adapter(Context context, ArrayList<events_adapter > events){
		this.context = context;
		this.events = events;
		
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

			convertView = inflater.inflate(R.layout.event_item, parent, false);

			//assign the data to the layout
			holder.eventNameTV = (TextView) convertView.findViewById(R.id.event_name);
			holder.eventDescriptionTV = (TextView) convertView.findViewById(R.id.event_description);
			holder.eventDeadlineTV = (TextView) convertView.findViewById(R.id.event_deadline);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}
		events_adapter event = (events_adapter) getItem(position);

		holder.eventNameTV.setText(event.getName());
		holder.eventDescriptionTV.setText(event.getDescription());
		holder.eventDeadlineTV.setText(event.getdate_deadline()+" "+event.gettime_deadline());
		return convertView;
	}
	public static class ViewHolder {
		public TextView eventNameTV;
		public TextView eventDescriptionTV;
		public TextView eventDeadlineTV;
	}
}

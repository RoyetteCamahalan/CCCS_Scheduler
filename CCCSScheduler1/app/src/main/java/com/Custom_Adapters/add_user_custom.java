package com.Custom_Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Getters_Setters.users_getset;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cccsscheduler.AppController;
import com.cccsscheduler.R;

public class add_user_custom extends BaseAdapter implements Filterable {
    private static String url = "http://bsit701.com/cccs_scheduler/add_member.php";

    public Context context;
    public ArrayList<users_getset> users;
    public ArrayList<users_getset> orig;
    public LayoutInflater inflater;
    public int groupid;
    users_getset user;

    public add_user_custom(Context context, ArrayList<users_getset> users, int groupid) {
        this.context = context;
        this.users = users;
        this.groupid = groupid;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return users.size();
    }

    public void removeEvent(int position) {
        users.remove(position);
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.user_item, parent, false);

            //assign the data to the layout
            holder.userNameTV = (TextView) convertView.findViewById(R.id.txt_user_fullname);
            holder.userNameTV.setTextColor(Color.BLACK);
            holder.UserSaddressTV = (TextView) convertView.findViewById(R.id.txt_users_address);
            holder.addmemberBTN = (ImageButton) convertView.findViewById(R.id.btn_addmember);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        user = (users_getset) getItem(position);
        holder.addmemberBTN.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);

                // TODO Auto-generated method stub;
                ImageButton addmember = (ImageButton) v;
                if (addmember.getTag().equals("0")) {
                    users.get(position).setStatus(1);
                    addmember.setTag("1");

                    addmember.setImageResource(R.drawable.ic_addmemberdone);
                    String tag_string_req = "req_register";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("action_type", "1");
                            params.put("group_id", String.valueOf(groupid));
                            params.put("user_id", String.valueOf(getItemId(position)));
                            return params;
                        }
                    };
                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                } else {
                    addmember.setTag("0");
                    users.get(position).setStatus(0);
                    addmember.setImageResource(R.drawable.ic_addmember);
                    String tag_string_req = "req_register";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap();
                            params.put("action_type", "2");
                            params.put("group_id", String.valueOf(groupid));
                            params.put("user_id", String.valueOf(getItemId(position)));
                            return params;
                        }
                    };
                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                }
            }
        });
        holder.userNameTV.setText(user.getName());
        holder.UserSaddressTV.setText(user.getAddress());

        if (user.getStatus() == 0) {
            holder.addmemberBTN.setImageResource(R.drawable.ic_addmember);
            holder.addmemberBTN.setTag("0");
        } else {
            holder.addmemberBTN.setImageResource(R.drawable.ic_addmemberdone);
            holder.addmemberBTN.setTag("1");
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView userNameTV;
        public TextView UserSaddressTV;
        public ImageButton addmemberBTN;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<users_getset> results = new ArrayList<users_getset>();
                if (orig == null)
                    orig = users;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final users_getset g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                users = (ArrayList<users_getset>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

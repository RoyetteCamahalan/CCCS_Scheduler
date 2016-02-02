package com.cccsscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class Groups extends Fragment {
	public int group_id=-1;
    int leader_id=-1;
    FragmentPagerAdapter adapterViewPager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		group_id=getArguments().getInt("group_id");

		View mRoot=inflater.inflate(R.layout.groups_layout, container,false);
        DBHelper helper=new DBHelper(getActivity());
        leader_id=helper.getGroupLeader(group_id);
        ViewPager vpPager = (ViewPager) mRoot.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager(),group_id,leader_id);
        vpPager.setAdapter(adapterViewPager);


		return mRoot;

	}
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        int leader_id=-1;
        int group_id=-1;
        public MyPagerAdapter(FragmentManager fragmentManager,int group_id,int leader_id) {
            super(fragmentManager);
            this.leader_id=leader_id;
            this.group_id=group_id;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return Group_Events.newInstance(group_id, leader_id);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Group_Members.newInstance(group_id,leader_id);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Group_About.newInstance(group_id, leader_id);
                default:
                    return null;
            }
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Group Events";
                case 1:
                    return "Group Members";
                case 2:
                    return "About";
            }
            return "Page ";
        }

    }
}

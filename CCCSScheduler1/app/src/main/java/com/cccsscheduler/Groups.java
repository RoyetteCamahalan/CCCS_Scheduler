package com.cccsscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class Groups extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
	public int group_id=-1;
    int leader_id=-1;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private TabHost mTabHost;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		group_id=getArguments().getInt("group_id");

		View mRoot=inflater.inflate(R.layout.groups_layout, container,false);
        DBHelper helper=new DBHelper(getActivity());

        mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
        mTabHost.setup();

        leader_id=helper.getGroupLeader(group_id);
        viewPager= (ViewPager) mTabHost.findViewById(R.id.viewpager);
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager(),group_id,leader_id);
        viewPager.setAdapter(pagerAdapter);

        mTabHost.addTab(mTabHost.newTabSpec("Events").setIndicator("Events").setContent(R.id.viewpager));
        mTabHost.addTab(mTabHost.newTabSpec("Members").setIndicator("Members").setContent(R.id.viewpager));
        mTabHost.addTab(mTabHost.newTabSpec("About").setIndicator("About").setContent(R.id.viewpager));

        viewPager.setOnPageChangeListener(this);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(1);
        mTabHost.setCurrentTab(0);
        viewPager.setCurrentItem(0);

		return mRoot;

	}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        switch (tabId){
            case "Events":
                viewPager.setCurrentItem(0);
                break;
            case "Members":
                viewPager.setCurrentItem(1);
                break;
            case "About":
                viewPager.setCurrentItem(3);
        }
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
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

    }
}

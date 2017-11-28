package com.example.dingo.dingoapp;

import android.app.Fragment;
import android.app.FragmentManager;


import android.support.v13.app.FragmentPagerAdapter;

public class FixedTabsPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public FixedTabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CurrentTaskListFragment currentTaskList = new CurrentTaskListFragment();
                return currentTaskList;
            case 1:
                PastTaskListFragment pastTaskListFragment = new PastTaskListFragment();
                return pastTaskListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

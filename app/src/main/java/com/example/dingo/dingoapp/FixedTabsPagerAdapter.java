package com.example.dingo.dingoapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FixedTabsPagerAdapter extends FragmentStatePagerAdapter {
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

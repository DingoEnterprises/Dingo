package com.example.dingo.dingoapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Tair on 11/22/2017.
 */

public class FixedTabsPagerAdapter extends FragmentStatePagerAdapter{


    public FixedTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new CurrentTaskListFragment();
            case 1:
                return new PastTaskListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Task List";
            case 1:
                return "Task List";
            default:
                return null;
        }
    }
}

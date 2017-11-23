package com.example.dingo.dingoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dingo.dingoapp.CurrentTaskListFragment;
import com.example.dingo.dingoapp.PastTaskListFragment;
import com.example.dingo.dingoapp.R;

/**
 * Created by Tair on 11/22/2017.
 */

public class MainTaskActivity extends AppCompatActivity implements CurrentTaskListFragment.OnFragmentInteractionListener, PastTaskListFragment.OnFragmentInteractionListener{
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Current Task List"));
        tabLayout.addTab(tabLayout.newTab().setText("Past Task List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return false;
            }
        };
        viewPager.setAdapter((adapter));
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            };

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            };

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

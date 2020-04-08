package com.ponysoft.coronavirusdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.ponysoft.fragments.dashboard.ConDashboardFragment;
import com.ponysoft.fragments.dashboard.QuickFactsFragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private List<Fragment> fragmentArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial Fragments

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new QuickFactsFragment());
        fragmentArrayList.add(new ConDashboardFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();
        DashboardFragmentPageAdapter dashboardFragmentPageAdapter = new DashboardFragmentPageAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        dashboardFragmentPageAdapter.setFragmentList(fragmentArrayList);

        ViewPager viewPager = (ViewPager)findViewById(R.id.dashboard_view_pager);
        viewPager.setAdapter(dashboardFragmentPageAdapter);
    }

    class DashboardFragmentPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = null;

        public DashboardFragmentPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void setFragmentList(List<Fragment> list) {

            fragmentList = list;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}

package com.ponysoft.coronavirusdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.ponysoft.fragments.dashboard.ConDashboardFragment;
import com.ponysoft.fragments.dashboard.IBaseFragment;
import com.ponysoft.fragments.dashboard.QuickFactsFragment;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<Fragment> fragmentArrayList = null;
    private DashboardFragmentPageAdapter dashboardFragmentPageAdapter = null;
    private ViewPager viewPager = null;

    private SwipeRefreshLayout swipeRefreshLayout = null;
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv_main);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        // Initial Fragments
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new QuickFactsFragment());
        //fragmentArrayList.add(new ConDashboardFragment(ConDashboardFragment.ConDashboardFragmentType.CON_DASHBOARD_FRAGMENT_TYPE_USA));
        //fragmentArrayList.add(new ConDashboardFragment(ConDashboardFragment.ConDashboardFragmentType.CON_DASHBOARD_FRAGMENT_TYPE_CHINA));

        FragmentManager fragmentManager = getSupportFragmentManager();
        dashboardFragmentPageAdapter = new DashboardFragmentPageAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        dashboardFragmentPageAdapter.setFragmentList(fragmentArrayList);

        viewPager = (ViewPager)findViewById(R.id.dashboard_view_pager);
        viewPager.setAdapter(dashboardFragmentPageAdapter);

        // fix SwipeRefreshLayout, ViewPager confict
        //
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeRefreshLayout.setEnabled(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        // xutils3 init
        //
        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    @Override
    public void onRefresh() {

        if (null != viewPager && null != dashboardFragmentPageAdapter) {

            IBaseFragment iBaseFragment = (IBaseFragment)dashboardFragmentPageAdapter.getItem(viewPager.getCurrentItem());
            if (null != iBaseFragment) {

                iBaseFragment.refreshData();
            }
        }
    }

    public void endRefresh() {

        if (null != swipeRefreshLayout) {

            swipeRefreshLayout.setRefreshing(false);
        }
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

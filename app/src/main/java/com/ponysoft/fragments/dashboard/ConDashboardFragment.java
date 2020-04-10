package com.ponysoft.fragments.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ponysoft.coronavirusdashboard.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConDashboardFragment extends Fragment implements IBaseFragment {

    public enum ConDashboardFragmentType {
        CON_DASHBOARD_FRAGMENT_TYPE_USA,
        CON_DASHBOARD_FRAGMENT_TYPE_EUROPE,
        CON_DASHBOARD_FRAGMENT_TYPE_ASIA,
        CON_DASHBOARD_FRAGMENT_TYPE_AFICA,
        CON_DASHBOARD_FRAGMENT_TYPE_LATIN_AM,
        CON_DASHBOARD_FRAGMENT_TYPE_CHINA,
        CON_DASHBOARD_FRAGMENT_TYPE_CANADA
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //
    // Fragment's type, different type has different UI
    //
    private ConDashboardFragmentType type;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConDashboardFragment() {
        // Required empty public constructor
    }

    public ConDashboardFragment(ConDashboardFragmentType type) {

        this.type = type;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConDashboardFragment newInstance(String param1, String param2) {
        ConDashboardFragment fragment = new ConDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_con_dashboard, container, false);
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void endRefresh() {

    }
}

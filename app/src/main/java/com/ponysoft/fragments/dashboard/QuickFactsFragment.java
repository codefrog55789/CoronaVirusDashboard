package com.ponysoft.fragments.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ponysoft.adapter.CountryListAdapter;
import com.ponysoft.api.DataAPI;
import com.ponysoft.coronavirusdashboard.R;
import com.ponysoft.models.CountryModel;
import com.ponysoft.models.QuickAllModel;
import com.ponysoft.utils.DateFormatter;
import com.ponysoft.utils.Formatter;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuickFactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickFactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragmentView = null;

    private ListView listView = null;
    private List<CountryModel> countriesList = null;
    private CountryListAdapter adapter = null;

    public QuickFactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickFactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickFactsFragment newInstance(String param1, String param2) {
        QuickFactsFragment fragment = new QuickFactsFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_quick_facts, container, false);

        listView = (ListView)fragmentView.findViewById(R.id.id_qucik_facts_list_view);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        DataAPI.shareInstance().getQuickAll(new DataAPI.OnQuickAllListener() {
            @Override
            public void success(int code, QuickAllModel model) {

                if (0 == code) {

                    updateQuickFacts(model);
                }
            }

            @Override
            public void fail(int code, String message) {
            }
        });
    }

    private void updateQuickFacts(QuickAllModel model) {

        if (null == fragmentView) return;

        // updated
        TextView updatedStatusTextView = (TextView)fragmentView.findViewById(R.id.id_quick_facts_status_tv);
        if (null != updatedStatusTextView) {
            updatedStatusTextView.setText("updated:" + DateFormatter.dateStringFromMillisSeconds(model.updated));
        }

        // confirmed
        TextView confirmedTextView = (TextView)fragmentView.findViewById(R.id.id_quick_facts_confirmed_tv);
        if (null != confirmedTextView) {
            confirmedTextView.setText(Formatter.numberFormat(model.cases));
        }

        // deaths
        TextView deceasedTextView = (TextView)fragmentView.findViewById(R.id.id_quick_facts_deceased_tv);
        if (null != deceasedTextView) {
            deceasedTextView.setText(Formatter.numberFormat(model.deaths));
        }

        // serious
        TextView seriousTextView = (TextView)fragmentView.findViewById(R.id.id_quick_facts_serious_tv);
        if (null != seriousTextView) {
            seriousTextView.setText(Formatter.numberFormat(model.critical));
        }

        // recovered
        TextView recoveredTextView = (TextView)fragmentView.findViewById(R.id.id_quick_facts_recovered_tv);
        if (null == recoveredTextView) {
            recoveredTextView.setText(Formatter.numberFormat(model.recovered));
        }
    }

    private void updateWorldAll() {


    }
}


package com.ponysoft.fragments.dashboard;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kproduce.roundcorners.RoundFrameLayout;
import com.ponysoft.adapter.CountryListAdapter;
import com.ponysoft.api.DataAPI;
import com.ponysoft.coronavirusdashboard.DashboardActivity;
import com.ponysoft.coronavirusdashboard.R;
import com.ponysoft.messages.MessageEvent;
import com.ponysoft.models.CountriesListModel;
import com.ponysoft.models.CountryModel;
import com.ponysoft.models.QuickAllModel;
import com.ponysoft.models.dbmodels.Saved;
import com.ponysoft.utils.DateFormatter;
import com.ponysoft.utils.Formatter;
import com.ponysoft.utils.dbs.SavedHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.text.Normalizer;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuickFactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickFactsFragment extends Fragment implements IBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragmentView = null;

    private ListView listView = null;
    private CountriesListModel countriesListModel = null;
    private CountryListAdapter adapter = null;

    private NestedScrollView scrollView = null;

    private RoundFrameLayout quickFactsLayout = null;
    private RoundFrameLayout savedLayout = null;
    private LinearLayout savedLinearLayout = null;
    private RoundFrameLayout worldLayout = null;

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
        adapter = new CountryListAdapter(getContext(), R.layout.list_item, null);
        listView.setAdapter(adapter);

        // disable scrollview scroll event
        scrollView = (NestedScrollView) fragmentView.findViewById(R.id.id_quick_facts_scroll_view);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        quickFactsLayout = (RoundFrameLayout)fragmentView.findViewById(R.id.id_quick_facts_layout);
        savedLayout = (RoundFrameLayout)fragmentView.findViewById(R.id.id_saved_layout);
        savedLinearLayout = (LinearLayout)fragmentView.findViewById(R.id.id_saved_list_items_layout);
        worldLayout = (RoundFrameLayout)fragmentView.findViewById(R.id.id_world_layout);

        quickFactsLayout.setVisibility(View.GONE);
        savedLayout.setVisibility(View.GONE);
        worldLayout.setVisibility(View.GONE);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (false == EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);
        }

        getQuikAllData();
    }

    public void getQuikAllData() {

        DataAPI.shareInstance().getQuickAll(new DataAPI.OnQuickAllListener() {
            @Override
            public void success(int code, final QuickAllModel model) {

                if (0 == code) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            updateQuickFacts(model);
                        }
                    });
                }

                getWorldAllData();
            }

            @Override
            public void fail(int code, String message) {

                getWorldAllData();
            }
        });
    }

    public void getWorldAllData() {

        DataAPI.shareInstance().getYesterdayAll(new DataAPI.OnYesterdayListener() {
            @Override
            public void success(int code, final CountriesListModel model) {

                if (0 == code) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            countriesListModel = model;

                            updateWorldAllUI(model);

                            updateSavedUI(model);
                        }
                    });
                }

                endRefresh();
            }

            @Override
            public void fail(int code, String message) {

                endRefresh();
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
        if (null != recoveredTextView) {
            recoveredTextView.setText(Formatter.numberFormat(model.recovered));
        }

        quickFactsLayout.setVisibility(View.VISIBLE);
    }

    private void updateWorldAllUI(CountriesListModel model) {

        if (null == model) return ;
        if (null != adapter) {

            adapter.setCountriesList(model.list);
            adapter.notifyDataSetChanged();
        }

        worldLayout.setVisibility(View.VISIBLE);
    }

    private void updateSavedUI(CountriesListModel model) {

        List<Saved> savedList = SavedHelper.shareInstace().all();
        if (null != savedList && null != model) {

            if (savedList.size() == 0) {

                savedLayout.setVisibility(View.GONE);
                return;
            }

            savedLayout.setVisibility(View.VISIBLE);
            savedLinearLayout.removeAllViewsInLayout();

            // init list items
            for (int i = 0; i < savedList.size(); i ++) {

                Saved saved = savedList.get(i);
                CountryModel countryModel = null;
                for (int j = 0; j < model.list.size(); j ++) {

                    CountryModel tmpModel = (CountryModel)model.list.get(j);
                    if (null != tmpModel && tmpModel.countryInfo._id == saved.getIid()) {

                        countryModel = tmpModel;
                        break;
                    }
                }

                if (null != countryModel) {
                    View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);

                    ImageView imageView = (ImageView)listItemView.findViewById(R.id.id_list_item_imgview);
                    TextView countryTextView = (TextView)listItemView.findViewById(R.id.id_list_item_country_tv);
                    TextView confirmedTextView = (TextView)listItemView.findViewById(R.id.id_list_item_confirmed_tv);
                    TextView deceasedTextView = (TextView)listItemView.findViewById(R.id.id_list_item_deceased_tv);
                    TextView recoveredTextView = (TextView)listItemView.findViewById(R.id.id_list_item_recovered_tv);

                    if (null != imageView) {
                        imageView.setImageResource(R.drawable.star);
                    }

                    if (null != countryTextView) {
                        countryTextView.setText(countryModel.country);
                    }

                    if (null != confirmedTextView) {
                        confirmedTextView.setText(Formatter.numberFormat(countryModel.cases));
                    }

                    if (null != deceasedTextView) {
                        deceasedTextView.setText(Formatter.numberFormat(countryModel.deaths));
                    }

                    if (null != recoveredTextView) {
                        recoveredTextView.setText(Formatter.numberFormat(countryModel.recovered));
                    }

                    savedLinearLayout.addView(listItemView);

                    final LinearLayout linearLayout = (LinearLayout)listItemView.findViewById(R.id.id_list_item_star_bg_layout);
                    linearLayout.setTag(countryModel.countryInfo._id);
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int _id = (int)linearLayout.getTag();
                            Log.d("LinearLayout", "click.... " + _id);

                            Saved delSaved = SavedHelper.shareInstace().findByIid(_id);
                            if (null != delSaved) {

                                SavedHelper.shareInstace().delete(delSaved);

                                refreshSavedUI();

                                // post refresh list view
                                EventBus.getDefault().post(new MessageEvent("refresh list view", MessageEvent.MESSAGETYPE.MESSAGETYPE_REFRESH_LIST_DATA));
                            }
                        }
                    });
                }
            }
        }
    }

    public void refreshSavedUI() {

        updateSavedUI(countriesListModel);
    }

    @Override
    public void refreshData() {

        getQuikAllData();
    }

    @Override
    public void endRefresh() {

        DashboardActivity dashboardActivity = (DashboardActivity)getActivity();
        if (null != dashboardActivity) {

            dashboardActivity.endRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNitify(MessageEvent event) {

        if (event.messageType == MessageEvent.MESSAGETYPE.MESSAGETYPE_REFRESH_SAVED_UI) {

            refreshSavedUI();
        }
    }
}


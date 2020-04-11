package com.ponysoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ponysoft.coronavirusdashboard.R;
import com.ponysoft.messages.MessageEvent;
import com.ponysoft.models.CountryModel;
import com.ponysoft.models.dbmodels.Saved;
import com.ponysoft.utils.Formatter;
import com.ponysoft.utils.dbs.SavedHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CountryListAdapter extends ArrayAdapter<CountryModel> {

    private List<CountryModel> countriesList = null;
    private int resourceId = 0;
    private List<Saved> savedList = null;

    public CountryListAdapter(@NonNull Context context, int resource, List<CountryModel> list) {
        super(context, resource);

        EventBus.getDefault().register(this);

        this.countriesList = list;
        this.resourceId = resource;
        this.savedList = SavedHelper.shareInstace().all();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CountryModel model = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (null == convertView) {

            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder = new ViewHolder();

            viewHolder.linearLayout = (LinearLayout)view.findViewById(R.id.id_list_item_star_bg_layout);
            viewHolder.imgView = (ImageView)view.findViewById(R.id.id_list_item_imgview);
            viewHolder.countryTextView = (TextView)view.findViewById(R.id.id_list_item_country_tv);
            viewHolder.confirmedTextView = (TextView)view.findViewById(R.id.id_list_item_confirmed_tv);
            viewHolder.deceasedTextView = (TextView)view.findViewById(R.id.id_list_item_deceased_tv);
            viewHolder.recoveredTextView = (TextView)view.findViewById(R.id.id_list_item_recovered_tv);

            view.setTag(viewHolder);
        } else {

            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        updateViewHolderUI(viewHolder, model);

        return view;
    }


    @Nullable
    @Override
    public CountryModel getItem(int position) {
        if (null == countriesList) return  null;
        return countriesList.get(position);
    }

    @Override
    public int getCount() {
        if (null == countriesList) return 0;
        return countriesList.size();
    }

    public void setCountriesList(List<CountryModel> countriesList) {
        this.countriesList = countriesList;
    }

    public void updateViewHolderUI(final ViewHolder viewHolder, final CountryModel model) {

        if (null == viewHolder || null == model) return;

        viewHolder.countryTextView.setText(model.country);
        viewHolder.confirmedTextView.setText(Formatter.numberFormat(model.cases));
        viewHolder.deceasedTextView.setText(Formatter.numberFormat(model.deaths));
        viewHolder.recoveredTextView.setText(Formatter.numberFormat(model.recovered));

        if (null != savedList) {

            boolean isExisted = false;
            for (int i = 0; i < savedList.size(); i ++) {
                Saved saved = (Saved)savedList.get(i);
                if (saved.getIid() == model.countryInfo._id) {

                    isExisted = true;
                    break;
                }
            }

            if (true == isExisted) {

                viewHolder.imgView.setImageResource(R.drawable.star);
            } else {

                viewHolder.imgView.setImageResource(R.drawable.unstar);
            }
        }


        // click star
        //
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Saved saved = SavedHelper.shareInstace().findByIid(model.countryInfo._id);
                if (null == saved) {

                    SavedHelper.shareInstace().saveOrUpdate(new Saved(model.countryInfo._id));

                    // notify to update
                    savedList = SavedHelper.shareInstace().all();
                    notifyDataSetChanged();

                    // post refresh saved ui
                    EventBus.getDefault().post(new MessageEvent("refresh saved ui", MessageEvent.MESSAGETYPE.MESSAGETYPE_REFRESH_SAVED_UI));
                }
            }
        });
    }

    @Subscribe
    public void onNitify(MessageEvent event) {
        if (event.messageType == MessageEvent.MESSAGETYPE.MESSAGETYPE_REFRESH_LIST_DATA) {

            savedList = SavedHelper.shareInstace().all();
            notifyDataSetChanged();
        }
    }


    class ViewHolder {
        public LinearLayout linearLayout;
        public ImageView imgView;
        public TextView countryTextView;
        public TextView confirmedTextView;
        public TextView deceasedTextView;
        public TextView recoveredTextView;
    }
}

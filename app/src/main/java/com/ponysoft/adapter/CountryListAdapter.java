package com.ponysoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ponysoft.coronavirusdashboard.R;
import com.ponysoft.models.CountryModel;
import com.ponysoft.utils.Formatter;

import java.util.List;

public class CountryListAdapter extends ArrayAdapter<CountryModel> {

    private List<CountryModel> countriesList = null;
    private int resourceId = 0;

    public CountryListAdapter(@NonNull Context context, int resource, List<CountryModel> list) {
        super(context, resource);

        this.countriesList = list;
        this.resourceId = resource;
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

    public void updateViewHolderUI(ViewHolder viewHolder, CountryModel model) {

        if (null == viewHolder || null == model) return;

        viewHolder.countryTextView.setText(model.country);
        viewHolder.confirmedTextView.setText(Formatter.numberFormat(model.cases));
        viewHolder.deceasedTextView.setText(Formatter.numberFormat(model.deaths));
        viewHolder.recoveredTextView.setText(Formatter.numberFormat(model.recovered));
    }


    class ViewHolder {
        public ImageView imgView;
        public TextView countryTextView;
        public TextView confirmedTextView;
        public TextView deceasedTextView;
        public TextView recoveredTextView;
    }
}

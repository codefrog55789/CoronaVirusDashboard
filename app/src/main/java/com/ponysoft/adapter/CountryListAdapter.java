package com.ponysoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ponysoft.coronavirusdashboard.R;
import com.ponysoft.models.CountryModel;

import java.util.List;

public class CountryListAdapter extends ArrayAdapter<CountryModel> {

    private List<CountryModel> countriesList = null;
    private int resourceId = 0;

    public CountryListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

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
            viewHolder.tv = (TextView)view.findViewById(R.id.id_list_item_tv);
            view.setTag(viewHolder);
        } else {

            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.tv.setText("update list item ui...");

        Log.d("CountryListAdapter", "update list item ui...");

        return view;
    }


    @Nullable
    @Override
    public CountryModel getItem(int position) {
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

    //
    // ViewHolder is a class to hold view
    //
    class ViewHolder {
        TextView tv;
    }
}

package com.ponysoft.coronavirusdashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ponysoft.api.DataAPI;
import com.ponysoft.models.QuickAllModel;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataAPI.shareInstance().getQuickAll(new DataAPI.OnQuickAllListener() {
            @Override
            public void success(int code, QuickAllModel model) {

                Log.v("MainActivity", String.valueOf(model.updated));
            }

            @Override
            public void fail(int code, String message) {

            }
        });
    }
}

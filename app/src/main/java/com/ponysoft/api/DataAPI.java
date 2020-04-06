package com.ponysoft.api;


import android.util.Log;

import com.google.gson.Gson;
import com.ponysoft.models.QuickAllModel;
import com.ponysoft.models.USStateModel;
import com.ponysoft.models.USStatesListModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @name: DataAPI
 * @description: 关于病毒数据接口
 *
 * */

public class DataAPI {

    private static DataAPI _dataAPI = null;

    private OkHttpClient _client = null;
    private Gson _gson = null;

    public static DataAPI shareInstance() {

        if (null == _dataAPI) {

            _dataAPI = new DataAPI();
        }

        return _dataAPI;
    }

    public DataAPI() {

        _client = new OkHttpClient();

        _gson = new Gson();
    }

    private void _GET(String url, Callback callback) {

        Call call = _client.newCall(new Request.Builder().url(url).get().build());

        call.enqueue(callback);
    }

    private void _POST() {}

    //
    // @description: http相关请求的interfaces
    //
    public interface OnListener {
        public void fail(int code, String message);
    }

    public interface OnQuickAllListener extends OnListener {
        public void success(int code, QuickAllModel model);
    }

    public interface OnUSStateListener extends OnListener {
        public void success(int code, USStateModel model);
    }

    public interface OnUSStatesListener extends OnListener {
        public void success(int code, List<USStateModel> list);
    }

    //
    // @name: getQuickAll
    // @description: 获取所有的感染者的名单
    //
    public void getQuickAll(final OnQuickAllListener listener) {

        _GET("https://corona.lmao.ninja/all", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                listener.fail(-1, "fail...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                QuickAllModel model = _gson.fromJson(response.body().string(), QuickAllModel.class);

                listener.success(0, model);
            }
        });
    }

    //
    // @name: getUSStatsAll
    // @description: 获取美国所有州的感染数据
    //
    public void getUSStatesAll(final OnUSStatesListener listener) {

        _GET("https://corona.lmao.ninja/states", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                listener.fail(-1, "getUSStatesAll fail...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                USStatesListModel model = _gson.fromJson(response.body().string(), USStatesListModel.class);

                listener.success(0, model.list);
            }
        });
    }

    //
    // @name: getUSStateAll
    // @params: state 州名
    // @description: 获取美国没一个州的数据
    public void getUSStateAll(String state, OnUSStateListener listener) {

        String url = "https://corona.lmao.ninja/states/" + state;

        _GET(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    //
    // @name: getYesterdayAll
    // @description: 获取昨日所有国家的数据
    //
    public void getYesterdayAll() {}

    //
    // @name: getYesterdayByCountry
    // @param: country
    // @description: 通过国家名称获取数据
    public void getYesterdayByCountry(String country) {}

    //
    // @name: getYesterdayByCountries
    // @params: {country, country...}
    // @description: 通过多个国家名称获取数据
    public void getYesterdayByCountries(List<String> countries) {

    }

    //
    // @name: getCountriesAll
    // @params:
    // @description: 获取所有国家的数据
    public void getCountiesAll() {

    }

    //
    // @name: getCountryAllByName
    // @params: country
    // @description: 通过国家的名字获取数据
    public void getCountryAllByName(String name) {

    }

    //
    // @name: getCountriesDataByNames
    // @params: {country, country ...}
    // @description: 通过多个国家的名字获取数据
    public void getCountriesDataByNames(List<String> countries) {

    }
}

package com.ponysoft.api;


import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ponysoft.models.CountriesListModel;
import com.ponysoft.models.CountryModel;
import com.ponysoft.models.QuickAllModel;
import com.ponysoft.models.USStateModel;
import com.ponysoft.models.USStatesListModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private JsonParser _parser = null;

    public static DataAPI shareInstance() {

        if (null == _dataAPI) {

            _dataAPI = new DataAPI();
        }

        return _dataAPI;
    }

    public DataAPI() {

        _client = new OkHttpClient();

        _gson = new Gson();

        _parser = new JsonParser();
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
        void fail(int code, String message);
    }

    public interface OnQuickAllListener extends OnListener {
        void success(int code, QuickAllModel model);
    }

    public interface OnUSStateListener extends OnListener {
        void success(int code, USStateModel model);
    }

    public interface OnUSStatesListener extends OnListener {
        void success(int code, List<USStateModel> list);
    }

    public interface OnYesterdayListener extends OnListener {
        void success(int code, CountriesListModel model);
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
    public void getUSStateAll(String state, final OnUSStateListener listener) {

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
    public void getYesterdayAll(final OnYesterdayListener listener) {

        _GET("https://corona.lmao.ninja/yesterday", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.fail(-1, "getYesterdayAll...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                CountriesListModel model = new CountriesListModel();
                try {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = new JSONArray(jsonData);

                    for (int i = 0; i < jsonArray.length(); i ++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CountryModel countryModel = new CountryModel();

                        countryModel.country = jsonObject.getString("country");
                        countryModel.updated = jsonObject.getLong("updated");
                        countryModel.cases = jsonObject.getInt("cases");
                        countryModel.todayCases = jsonObject.getInt("todayCases");
                        countryModel.deaths = jsonObject.getInt("deaths");
                        countryModel.todayDeaths = jsonObject.getInt("todayDeaths");
                        countryModel.recovered = jsonObject.getInt("recovered");
                        countryModel.active = jsonObject.getInt("active");
                        countryModel.critical = jsonObject.getInt("critical");
                        countryModel.casesPerOneMillion = Integer.parseInt(jsonObject.getString("casesPerOneMillion"));
                        countryModel.deathsPerOneMillion = Integer.parseInt(jsonObject.getString("deathsPerOneMillion"));
                        countryModel.tests = jsonObject.getInt("tests");
                        countryModel.testsPerOneMillion = Integer.parseInt(jsonObject.getString("testsPerOneMillion"));

                        JSONObject countryInfoObject = jsonObject.getJSONObject("countryInfo");

                        if (null != countryInfoObject) {

                            String _id = countryInfoObject.getString("_id");
                            if (_id.equals("null")) {
                                countryModel.countryInfo._id = -999;
                            } else {
                                countryModel.countryInfo._id = Integer.parseInt(_id);
                            }
                            countryModel.countryInfo.flag = countryInfoObject.getString("flag");
                            countryModel.countryInfo.iso2 = countryInfoObject.getString("iso2");
                            countryModel.countryInfo.ios3 = countryInfoObject.getString("iso3");
                            countryModel.countryInfo.lat = countryInfoObject.getInt("lat");
                            countryModel.countryInfo.lng = countryInfoObject.getInt("long");
                        }

                        model.list.add(countryModel);
                    }

                    if (null != model) {

                        listener.success(0, model);
                    } else {

                        listener.fail(-1, "fail parse...");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    listener.fail(-1, "fail parse...");
                }

            }
        });
    }

    //
    // @name: getYesterdayByCountry
    // @param: country
    // @description: 通过国家名称获取数据
    public void getYesterdayByCountry(String country) {

        String url = "https://corona.lmao.ninja/yesterday/" + country;

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

        _GET("https://corona.lmao.ninja/countries", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    //
    // @name: getCountryAllByName
    // @params: country
    // @description: 通过国家的名字获取数据
    public void getCountryAllByName(String name) {

        String url = "https://corona.lmao.ninja/countries/" + name;

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
    // @name: getCountriesDataByNames
    // @params: {country, country ...}
    // @description: 通过多个国家的名字获取数据
    public void getCountriesDataByNames(List<String> countries) {

    }
}

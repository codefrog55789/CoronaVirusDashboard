package com.ponysoft.api;


import android.util.Log;

import com.google.gson.Gson;
import com.ponysoft.models.QuickAllModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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

    //
    // @name: getQuickAll
    // @parameters:
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

                Log.i("getQuickAll", String.valueOf(model.active));

                listener.success(0, model);
            }
        });
    }
}

package com.example.root.myapplication.model.helper;

import com.example.root.myapplication.model.data.Market;
import com.example.root.myapplication.model.data.MaxMarkets;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MarketGetHelper {
    private static String targetUrl = "https://max-api.maicoin.com/api/v2/tickers";

    private OkHttpClient client;
    private Gson gson;
    private Request request;
    private Subject<List<Market>> mSubjectOnRefreshMarkets;

    public MarketGetHelper() {
        client = new OkHttpClient();
        gson = new Gson();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(targetUrl).newBuilder();
        String url = urlBuilder.build().toString();
        request = new Request.Builder()
                .url(url)
                .build();
        mSubjectOnRefreshMarkets = PublishSubject.create();
    }

    public Observable<List<Market>> getObservableRefreshMarkets() {
        return mSubjectOnRefreshMarkets;
    }

    public void refreshMarkets() {
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    String resp = responseBody.string();
                    MaxMarkets respMarkets = gson.fromJson(resp, MaxMarkets.class);
                    mSubjectOnRefreshMarkets.onNext(respMarkets.getMarketsArray());
                }
            }
        });
    }
}

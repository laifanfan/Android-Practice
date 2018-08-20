package com.example.root.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Item> itemList;
    // Constructor of the class
    public MyAdapter(int layoutId, ArrayList<Item> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        myViewHolder.getObservableOnClickItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataChange);
        return myViewHolder;
    }

    private void onDataChange(ArrayList<Item> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }
    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        item.setText(itemList.get(listPosition).getName());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Subject<ArrayList<Item>> mSubjectOnClickItem = PublishSubject.create();
        public TextView item;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = (TextView) itemView.findViewById(R.id.row_item);
        }

        Observable<ArrayList<Item>> getObservableOnClickItem() {
            return mSubjectOnClickItem;
        }

        @Override
        public void onClick(View view) {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.kraken.com/0/public/Ticker").newBuilder();
            urlBuilder.addQueryParameter("pair", "ETHUSD");
            String url = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
/*
                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            Log.d("onClick", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
*/
                        Gson gson = new Gson();
                        String resp = responseBody.string();
                        ResponseObj responsceObj = gson.fromJson(resp, ResponseObj.class);
                        ArrayList<Item> newList = new ArrayList<>();
                        for (String s : responsceObj.getA()) {
                            newList.add(new Item(s));
                        }
                        for (String s : responsceObj.getB()) {
                            newList.add(new Item(s));
                        }
                        for (String s : responsceObj.getC()) {
                            newList.add(new Item(s));
                        }
                        for (String s : responsceObj.getV()) {
                            newList.add(new Item(s));
                        }
                        mSubjectOnClickItem.onNext(newList);
                        Log.d("onClickkk", resp);
                    }
                }
            });

            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getText());
        }
    }
}


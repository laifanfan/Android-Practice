package com.example.root.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.myapplication.model.data.Market;
import com.example.root.myapplication.model.helper.MarketGetHelper;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Market> itemList;
    private MarketGetHelper marketGetHelper;
    private Subject<Boolean> mSubjectOnRefreshDone = PublishSubject.create();

    // Constructor of the class
    public MyAdapter(int layoutId, MarketGetHelper marketGetHelper) {
        listItemLayout = layoutId;
        this.marketGetHelper = marketGetHelper;
        this.marketGetHelper.getObservableRefreshMarkets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataChange);

        marketGetHelper.refreshMarkets();
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        return new ViewHolder(view);
    }

    public Observable<Boolean> getObservableRefreshDone() {
        return mSubjectOnRefreshDone;
    }

    private void onDataChange(List<Market> list) {
        this.itemList = list;
        notifyDataSetChanged();
        mSubjectOnRefreshDone.onNext(false);
    }

    private static String round(double num) {
        NumberFormat nf = new DecimalFormat();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setMaximumFractionDigits(3);

        try {
            return nf.format(num);
        } catch (Exception e) {
            return "0.00";
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {
        Market item = itemList.get(listPosition);
        holder.bind(item);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.last) TextView last;
        @BindView(R.id.vol) TextView vol;
        @BindView(R.id.high) TextView high;
        @BindView(R.id.low) TextView low;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Market item) {
            if (item.isBtc()) {
                icon.setImageResource(R.drawable.icon_btc);
            } else if (item.isEth()) {
                icon.setImageResource(R.drawable.icon_eth);
            } else {
                icon.setImageResource(R.drawable.icon_mith);
            }
            last.setText(round(item.getLast()));
            vol.setText(round(item.getVol()));
            high.setText(round(item.getHigh()));
            low.setText(round(item.getLow()));
        }

        @OnClick()
        void onClickItem() {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + last.getText());
        }
    }
}


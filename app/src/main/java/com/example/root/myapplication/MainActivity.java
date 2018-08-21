package com.example.root.myapplication;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.myapplication.model.helper.MarketGetHelper;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MarketGetHelper marketGetHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup header = findViewById(R.id.header);
        ((ImageView)header.findViewById(R.id.icon)).setImageResource(R.drawable.icon_maicoin);
        ((TextView)header.findViewById(R.id.last)).setText("Last");
        ((TextView)header.findViewById(R.id.vol)).setText("24h Vols");
        ((TextView)header.findViewById(R.id.high)).setText("High");
        ((TextView)header.findViewById(R.id.low)).setText("Low");

        marketGetHelper = new MarketGetHelper();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> marketGetHelper.refreshMarkets());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        MyAdapter myAdapter = new MyAdapter(R.layout.list_item, marketGetHelper);
        myAdapter.getObservableRefreshDone().
                subscribe(swipeRefreshLayout::setRefreshing);

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }
}

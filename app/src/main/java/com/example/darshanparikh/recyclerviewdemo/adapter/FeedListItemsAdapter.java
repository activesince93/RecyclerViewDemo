package com.example.darshanparikh.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darshanparikh.recyclerviewdemo.R;
import com.example.darshanparikh.recyclerviewdemo.model.FeedListItems;

import java.util.List;

/**
 * Created by darshan.parikh on 25-Aug-15.
 */
public class FeedListItemsAdapter extends RecyclerView.Adapter<FeedListItemsAdapter.CustomViewHolder> {
    private List<FeedListItems> feedItemList;
    private Context mContext;

    public FeedListItemsAdapter(Context context, List<FeedListItems> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_raw, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        FeedListItems feedItem = feedItemList.get(i);
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.textName);
        }
    }
}

package com.alok.worldoffood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alok.worldoffood.R;
import com.alok.worldoffood.room.entity.Feeds;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapters extends RecyclerView.Adapter<FeedAdapters.FeedViewHolder> {

    Context context;

    private static final String TAG = "FeedAdapters";
    List<Feeds> feedsList;

    LayoutInflater layoutInflater;


    public FeedAdapters(Context context){

        layoutInflater = LayoutInflater.from(context);


    }

    public void setFeedList(List<Feeds> feedsList){

        this.feedsList = feedsList;
        Log.d(TAG, "setFeedList: "+feedsList);



    }




    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.layout_feed,parent,false);

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        holder.profileName.setText(feedsList.get(position).getProfileName());

      //  holder.profileImage.setImageResource(feedsList.get(position).getProfileImage());

        Picasso.get().load(feedsList.get(position).getFeedImage()).resize(500,450).centerCrop().
                into(holder.postFeed);

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView profileName;
        ImageView postFeed;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            profileName = itemView.findViewById(R.id.profile_name);
            postFeed = itemView.findViewById(R.id.post_feed);

        }
    }

}

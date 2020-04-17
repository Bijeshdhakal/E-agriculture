package com.bjshDkl.agriculture.resource.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.newsOffers.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsReyclerViewAdapter extends RecyclerView.Adapter<NewsReyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<NewsModel> newsArrayList;

    public NewsReyclerViewAdapter(Context mContext, ArrayList<NewsModel> newsArrayList) {
        this.mContext = mContext;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NewsModel currentNews = newsArrayList.get(position);
        holder.newsTitle.setText(currentNews.getTitle());
        holder.newsDate.setText(currentNews.getPublishedDate());
        holder.newsSource.setText(currentNews.getSource());
        try {
            Picasso.get()
                    .load(currentNews.getImageUrl())
                    .placeholder(R.drawable.ic_terrain_black_24dp)
                    .error(R.drawable.ic_terrain_black_24dp)
                    .into(holder.newsImageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.newsImageView.setImageResource(R.drawable.ic_terrain_black_24dp);
        }
        holder.newsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setPackage("com.android.chrome");
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentNews.getUrl()));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDate;
        TextView newsSource;
        ImageView newsImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.searchNewsTitle);
            newsDate = (TextView) itemView.findViewById(R.id.searchNewsDate);
            newsSource = (TextView) itemView.findViewById(R.id.sourceTextView);
            newsImageView = (ImageView) itemView.findViewById(R.id.searchNewsImageView);
        }
    }
}

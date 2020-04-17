package com.bjshDkl.agriculture.resource.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.resource.model.YoutubeVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.MyViewHolder> {
    Context context;
    ArrayList<YoutubeVideo> youtubeVideos = new ArrayList<>();

    public YoutubeVideoAdapter(Context context, ArrayList<YoutubeVideo> youtubeVideos) {
        this.context = context;
        this.youtubeVideos = youtubeVideos;
    }

    @NonNull
    @Override
    public YoutubeVideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_youtube, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeVideoAdapter.MyViewHolder holder, int position) {
        final YoutubeVideo currentVideo = youtubeVideos.get(position);

//        holder.youtubeView.loadData( currentVideo.getVideoUrl(), "text/html" , "utf-8" );

        String imageUrl = "https://img.youtube.com/vi/" + currentVideo.getVideoUrl() + "/sddefault.jpg";
        final String videoUrl = "http://www.youtube.com/watch?v=" + currentVideo.getVideoUrl();
        Log.d("url",videoUrl);
        Picasso.get()
                .load(currentVideo.getImage())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(holder.youtubeView);


        holder.title.setText(currentVideo.getTitle());
        holder.description.setText(currentVideo.getDesc());

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + currentVideo.getVideoUrl()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(videoUrl));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView youtubeView;
        TextView title;
        TextView description;
        LinearLayout mainView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainView = (LinearLayout) itemView.findViewById(R.id.mainView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);

            youtubeView = (ImageView) itemView.findViewById(R.id.videoWebView);
//            youtubeView.getSettings().setJavaScriptEnabled(true);
//            youtubeView.setWebChromeClient(new WebChromeClient() {


        }
    }
}

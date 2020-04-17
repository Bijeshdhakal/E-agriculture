package com.bjshDkl.agriculture.resource.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.resource.model.ArticleModel;

import java.util.ArrayList;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ArticleModel> articleModels;

    public ArticleRecyclerViewAdapter(Context context, ArrayList<ArticleModel> articleModels) {
        this.context = context;
        this.articleModels = articleModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ArticleModel currentArticleModel = articleModels.get(position);

        holder.title.setText(currentArticleModel.getTitle());
        holder.author.setText(currentArticleModel.getAuthors());

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setPackage("com.android.chrome");
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentArticleModel.getUrl()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView download;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            download = itemView.findViewById(R.id.download);
        }
    }
}

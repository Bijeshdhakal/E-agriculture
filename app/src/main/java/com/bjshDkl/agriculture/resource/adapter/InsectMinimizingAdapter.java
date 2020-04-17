package com.bjshDkl.agriculture.resource.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.insectDetail.InsectMinimizeDetailActivity;
import com.bjshDkl.agriculture.resource.model.MinimizingTechniqueModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InsectMinimizingAdapter extends RecyclerView.Adapter<InsectMinimizingAdapter.MyViewHolder> {
    Context context;
    ArrayList<MinimizingTechniqueModel> minimizingTechniqueModels = new ArrayList<>();

    public InsectMinimizingAdapter(Context context, ArrayList<MinimizingTechniqueModel> minimizingTechniqueModels) {
        this.context = context;
        this.minimizingTechniqueModels = minimizingTechniqueModels;
        Log.d("insect", minimizingTechniqueModels.size() + "");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_insect_control, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final MinimizingTechniqueModel model = minimizingTechniqueModels.get(position);

        holder.title.setText(model.getTitle());
        Picasso.get()
                .load(model.getImage())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsectMinimizeDetailActivity.class);
                intent.putExtra("data", model);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return minimizingTechniqueModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}

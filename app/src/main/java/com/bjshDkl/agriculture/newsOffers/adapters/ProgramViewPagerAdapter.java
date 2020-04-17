package com.bjshDkl.agriculture.newsOffers.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.newsOffers.model.ProgramModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProgramViewPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<ProgramModel> programModels = new ArrayList<>();

    public ProgramViewPagerAdapter(Context context, ArrayList<ProgramModel> programModels) {
        this.context = context;
        this.programModels = programModels;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final ProgramModel currentEvent = programModels.get(position);
        View viewInflated = LayoutInflater.from(collection.getContext()).inflate(R.layout.item_program, null);


        ImageView imageView = viewInflated.findViewById(R.id.imageView);
        TextView location = viewInflated.findViewById(R.id.location);
        TextView title = viewInflated.findViewById(R.id.title);
        TextView date = viewInflated.findViewById(R.id.date);
        Picasso.get()
                .load(currentEvent.getImage())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(imageView);
        location.setText(currentEvent.getLocation());
        title.setText(currentEvent.getTitle());
        date.setText(currentEvent.getDate());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, EventDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constants.EVENTCONSTANT,currentEvent);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
            }
        });
        collection.addView(viewInflated);
        return viewInflated;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((RelativeLayout) view);
    }

    @Override
    public int getCount() {
        return programModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

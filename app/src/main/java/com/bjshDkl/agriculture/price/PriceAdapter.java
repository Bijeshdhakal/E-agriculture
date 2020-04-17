package com.bjshDkl.agriculture.price;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjshDkl.agriculture.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.MyViewHolder> {
    Context context;
    ArrayList<PriceModel> priceModelArrayList = new ArrayList<>();

    public PriceAdapter(Context context, ArrayList<PriceModel> priceModelArrayList) {
        this.context = context;
        this.priceModelArrayList = priceModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_price, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.itemName.setText(priceModelArrayList.get(position).getItemName());
        holder.itemPrice.setText(priceModelArrayList.get(position).getItemPrice() + " / " + priceModelArrayList.get(position).getItemUnit());

    }

    @Override
    public int getItemCount() {
        return priceModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemPrice;
        TextView itemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}

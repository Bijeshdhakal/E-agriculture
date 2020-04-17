package com.bjshDkl.agriculture.forum.chat.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.forum.chat.model.Message;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    Context context;
    List<Message> messages;
    DatabaseReference messageDb;
    SharedPreferences sharedPreferences;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messageDb, SharedPreferences sharedPreferences) {
        this.context = context;
        this.messages = messages;
        this.messageDb = messageDb;
        this.sharedPreferences = sharedPreferences;
    }

//    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messageDb) {
//        this.context = context;
//        this.messages = messages;
//        this.messageDb = messageDb;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messages.get(position);
        if (message.getUid().equals(sharedPreferences.getString("uid", ""))) {

            holder.tvUsername.setText("You : ");
            holder.tvTitle.setText(message.getMessage());
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));

            holder.tvTitle.setGravity(Gravity.START);
//            holder.l1.setBackgroundColor(context.getResources().getColor(R.color.primary));


        } else {
            holder.tvUsername.setTypeface(null, Typeface.NORMAL);
            holder.tvUsername.setText(message.getName() + " : ");
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvTitle.setText( message.getMessage());
            holder.ibDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvUsername;
        ImageButton ibDelete;
        LinearLayout l1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvUsername = (TextView) itemView.findViewById(R.id.tvusername);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
            l1 = (LinearLayout) itemView.findViewById(R.id.l1Message);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageDb.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}

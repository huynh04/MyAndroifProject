package com.example.my_blog.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_blog.DoMain.Message;
import com.example.my_blog.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    Context ctx;
    ArrayList<Message>messages;
    String senderImg,receiverImg;

    public ChatAdapter(Context ctx, ArrayList<Message> messages) {
        this.ctx = ctx;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_holder,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        holder.textChat.setText(messages.get(position).getContent());
        ConstraintLayout constraintLayout = holder.MessLayout;

        if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            holder.cardViewChat1.setVisibility(View.VISIBLE);
            holder.cardViewChat.setVisibility(View.GONE);
            holder.textChat1.setText(messages.get(position).getContent());
        }
        else{
            holder.textChat.setBackgroundResource(R.color.message);
            holder.cardViewChat1.setVisibility(View.GONE);
            holder.cardViewChat.setVisibility(View.VISIBLE);
            holder.textChat.setText(messages.get(position).getContent());

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{
        TextView textChat,textChat1;
        CardView cardViewChat,cardViewChat1;
        ConstraintLayout MessLayout;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            textChat = itemView.findViewById(R.id.textChat);
            cardViewChat = itemView.findViewById(R.id.CardViewChat);
            MessLayout = itemView.findViewById(R.id.constraintLayout);
            textChat1 = itemView.findViewById(R.id.textChat1);
            cardViewChat1 = itemView.findViewById(R.id.CardViewChat1);
        }
    }
}

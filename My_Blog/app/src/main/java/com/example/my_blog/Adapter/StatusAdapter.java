package com.example.my_blog.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_blog.DoMain.Status;
import com.example.my_blog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {
    ArrayList<Status>status;
    Context ctx;

    public StatusAdapter(ArrayList<Status> status, Context ctx) {
        this.status = status;
        this.ctx = ctx;
    }



    @NonNull
    @Override
    public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_status,parent,false);
        return new StatusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolder holder, int position) {
        holder.username.setText(status.get(position).getUsername());
        holder.usertitle.setText(status.get(position).getTitle());

        if (!status.get(position).getPicture().equals("")){
            Picasso.with(ctx).load(status.get(position).getPicture()).into(holder.userpicture);
        }
        else{
            holder.userpicture.setVisibility(View.GONE);
        }


        Picasso.with(ctx).load(status.get(position).getUserimg()).into(holder.userimg);



    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    public class StatusHolder extends RecyclerView.ViewHolder{
        TextView username,usertitle;
        ImageView userimg,userpicture;

        public StatusHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            usertitle = itemView.findViewById(R.id.user_title);
            userimg = itemView.findViewById(R.id.user_image);
            userpicture = itemView.findViewById(R.id.user_picture_status);
        }
    }
}

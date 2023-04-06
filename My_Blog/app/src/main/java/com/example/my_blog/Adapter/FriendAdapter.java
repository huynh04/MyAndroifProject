package com.example.my_blog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_blog.Activity.ChatActivity;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {
    public FriendAdapter(ArrayList<User> users, Context ctx) {
        this.users = users;
        this.ctx = ctx;
    }

    ArrayList<User>users;
    Context ctx;


    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_friend,parent,false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        holder.friendname.setText(users.get(position).getUsername());
        if (users.get(position).getUserpicture().equals("")){
            Picasso.with(ctx).load(R.mipmap.ic_launcher).into(holder.friendimg);
        }
        else{
            Picasso.with(ctx).load(users.get(position).getUserpicture()).into(holder.friendimg);
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                intent.putExtra("UserEmail",users.get(position).getUseremail());
                intent.putExtra("UserName", users.get(position).getUsername());
                intent.putExtra("UserImg",users.get(position).getUserpicture());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class FriendHolder extends RecyclerView.ViewHolder{
        ImageView friendimg;
        TextView friendname,frienddes;
        LinearLayout layout;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);

            friendimg = itemView.findViewById(R.id.FriendImg);
            friendname = itemView.findViewById(R.id.FriendName);
            frienddes = itemView.findViewById(R.id.FriendDes);
            layout = itemView.findViewById(R.id.LL_FriendChat);
        }
    }
}

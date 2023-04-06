package com.example.my_blog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_blog.Activity.MainActivity;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserExistAdapter extends RecyclerView.Adapter<UserExistAdapter.UserExistHolder> {
    Context ctx;
    ArrayList<User>users;

    public UserExistAdapter(Context ctx, ArrayList<User> users) {
        this.ctx = ctx;
        this.users = users;
    }

    @NonNull
    @Override
    public UserExistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_exists,parent,false);
        return new UserExistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserExistHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());

        if (users.get(position).getUserpicture().equals("")){
            Picasso.with(ctx).load(R.mipmap.ic_launcher).into(holder.user);
        }
        else{
            Picasso.with(ctx).load(users.get(position).getUserpicture()).into(holder.user);
        }


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserExistHolder extends RecyclerView.ViewHolder{
        ImageView user;
        TextView username;

        public UserExistHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.UserImg_Exist);
            username = itemView.findViewById(R.id.UserName_Exist);
        }
    }
}

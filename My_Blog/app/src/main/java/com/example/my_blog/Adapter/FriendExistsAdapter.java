package com.example.my_blog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

public class FriendExistsAdapter extends RecyclerView.Adapter<FriendExistsAdapter.FriendExistsHolder> {
    ArrayList<User>userArrayList;
    Context ctx;

    public FriendExistsAdapter(ArrayList<User> userArrayList, Context ctx) {
        this.userArrayList = userArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public FriendExistsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_exists,parent,false);
        return new FriendExistsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendExistsHolder holder, int position) {
        holder.NameFriend.setText(userArrayList.get(position).getUsername());
        if (userArrayList.get(position).getUserpicture().equals("")){
            Picasso.with(ctx).load(R.mipmap.ic_launcher).into(holder.FriendExist);
        }
        else {
            Picasso.with(ctx).load(userArrayList.get(position).getUserpicture()).into(holder.FriendExist);
        }

        holder.FriendExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                intent.putExtra("UserEmail",userArrayList.get(position).getUseremail());
                intent.putExtra("UserName", userArrayList.get(position).getUsername());
                intent.putExtra("UserImg",userArrayList.get(position).getUserpicture());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class FriendExistsHolder extends RecyclerView.ViewHolder{
        ImageView FriendExist;
        TextView NameFriend;
        LinearLayout layout;

        public FriendExistsHolder(@NonNull View itemView) {
            super(itemView);

            FriendExist = itemView.findViewById(R.id.FriendImg_Exists);
            NameFriend = itemView.findViewById(R.id.NameFriend);
            layout = itemView.findViewById(R.id.LL_FriendExists);
        }
    }
}

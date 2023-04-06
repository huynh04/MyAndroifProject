package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.my_blog.Adapter.FriendAdapter;
import com.example.my_blog.Adapter.FriendExistsAdapter;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivityFriendMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendMessage extends AppCompatActivity {
    ActivityFriendMessageBinding binding;
    ArrayList<User>userArrayList;
    FriendAdapter friendAdapter;
    FriendExistsAdapter friendExistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userArrayList = new ArrayList<>();
        getFriend();
        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendMessage.this,MainActivity.class));
                finish();
            }
        });
        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendMessage.this,Settings.class));
            }
        });
        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendMessage.this,UserProfile.class));
            }
        });
    }
    public void getFriend(){
        userArrayList.clear();
        FirebaseDatabase
                .getInstance()
                .getReference("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid = dataSnapshot.getKey();
                    if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        userArrayList.add(dataSnapshot.getValue(User.class));
                    }
                }

                friendAdapter = new FriendAdapter(userArrayList,FriendMessage.this);
                binding.recyclerview2.setLayoutManager(new LinearLayoutManager(FriendMessage.this,LinearLayoutManager.VERTICAL,false));
                binding.recyclerview2.setAdapter(friendAdapter);

                friendExistsAdapter = new FriendExistsAdapter(userArrayList,FriendMessage.this);
                binding.recyclerView1.setLayoutManager(new LinearLayoutManager(FriendMessage.this,LinearLayoutManager.HORIZONTAL,false));
                binding.recyclerView1.setAdapter(friendExistsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
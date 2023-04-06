package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding binding;
    ArrayList<User>userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userArrayList = new ArrayList<>();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
                startActivity(new Intent(Settings.this,LoginAndSignUp.class));

            }
        });
        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,MainActivity.class));
            }
        });
        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,FriendMessage.class));
            }
        });
        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,UserProfile.class));
            }
        });
        getUser();
    }

    private void LogOut(){
        FirebaseAuth.getInstance().signOut();
    }

    private void getUser(){
        userArrayList.clear();
        FirebaseDatabase
                .getInstance()
                .getReference("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String uid = dataSnapshot.getKey();
                    if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        userArrayList.add(dataSnapshot.getValue(User.class));
                    }
                }

                for (int i = 0;i<userArrayList.size();i++){
                    binding.UserName.setText(userArrayList.get(i).getUsername());
                    if (userArrayList.get(i).getUserpicture().equals("")){
                        Picasso.with(Settings.this).load(R.mipmap.ic_launcher).into(binding.UserImg);
                    }
                    else{
                        Picasso.with(Settings.this).load(userArrayList.get(i).getUserpicture()).into(binding.UserImg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
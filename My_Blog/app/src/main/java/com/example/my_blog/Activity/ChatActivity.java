package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.my_blog.Adapter.ChatAdapter;
import com.example.my_blog.DoMain.Message;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    ArrayList<Message>messageArrayList;
    String UserName,UserEmail,ChatRoomId,UserPicture;
    ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messageArrayList = new ArrayList<>();

        binding.BackToMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this,FriendMessage.class));
            }
        });

        UserName = getIntent().getStringExtra("UserName");
        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPicture = getIntent().getStringExtra("UserImg");

        binding.FriendNameChat.setText(UserName);
        if (UserPicture.equals("")){
            Picasso.with(this).load(R.mipmap.ic_launcher).into(binding.FriendImgChat);
        }
        else{
            Picasso.with(this).load(UserPicture).into(binding.FriendImgChat);
        }

        chatAdapter = new ChatAdapter(ChatActivity.this,messageArrayList);
        binding.recyclerviewChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        binding.recyclerviewChat.setAdapter(chatAdapter);
        binding.sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.inputChat.equals("")){
                    Toast.makeText(ChatActivity.this, "Message Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase
                            .getInstance()
                            .getReference("message/"+ChatRoomId)
                            .push()
                            .setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),UserEmail,binding.inputChat.getText().toString()));
                }
                binding.inputChat.setText("");
            }

        });
        setUpChatRoom();



    }
    public void setUpChatRoom(){
        FirebaseDatabase
                .getInstance()
                .getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUserName = snapshot.getValue(User.class).getUsername();
                if (UserName.compareTo(myUserName)>0){
                    ChatRoomId = myUserName + "_" + UserName;
                }
                else if(UserName.compareTo(myUserName)==0){
                    ChatRoomId = myUserName + "_" + UserName;
                }
                else{
                    ChatRoomId = UserName + "_" + myUserName;
                }
                attachMessListener(ChatRoomId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void attachMessListener(String chatRoomId){
        FirebaseDatabase
                .getInstance()
                .getReference("message/"+chatRoomId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messageArrayList.add(dataSnapshot.getValue(Message.class));
                }
                chatAdapter.notifyDataSetChanged();
                binding.recyclerviewChat.scrollToPosition(messageArrayList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class UserProfile extends AppCompatActivity {
    ActivityUserProfileBinding binding;
    ArrayList<User> users;
    String imgProfile;
    Uri imgPath;
    boolean getImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        users = new ArrayList<>();

        getUserName();

        binding.userImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cardView.setVisibility(View.VISIBLE);
            }
        });
        binding.coverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cardView.setVisibility(View.VISIBLE);
            }
        });

        binding.CancelOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cardView.setVisibility(View.GONE);
            }
        });

        binding.optionUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoClick = new Intent();
                photoClick.setType("image/*");
                photoClick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoClick, 1);
                getImg = true;
            }
        });

        binding.optionUserCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoClick = new Intent();
                photoClick.setType("image/*");
                photoClick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoClick, 1);
                getImg = false;
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,MainActivity.class));
            }
        });
        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,FriendMessage.class));
            }
        });
        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,Settings.class));
            }
        });
    }
    public void getUserName(){
        users.clear();
        FirebaseDatabase
                .getInstance()
                .getReference("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String uid = dataSnapshot.getKey();
                            if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                users.add(dataSnapshot.getValue(User.class));
                            }
                        }

                        for (int i = 0; i< users.size();i++){
                            if (users.get(i).getUserpicture().equals("")){
                                Picasso.with(UserProfile.this).load(R.mipmap.ic_launcher).into(binding.userImgProfile);
                                Picasso.with(UserProfile.this).load(R.mipmap.ic_launcher).into(binding.coverPhoto);
                            }
                            else{
                                Picasso.with(UserProfile.this).load(users.get(i).getUserpicture()).into(binding.userImgProfile);
                                Picasso.with(UserProfile.this).load(users.get(i).getCoverImg()).into(binding.coverPhoto);
                            }

                            binding.username.setText(users.get(i).getUsername());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getUserProfile(){
        if (getImg == true){
            FirebaseStorage
                    .getInstance()
                    .getReference("userimage/"+ UUID.randomUUID().toString())
                    .putFile(imgPath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserProfile.this, "Update Completed", Toast.LENGTH_SHORT).show();
                                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        updateImgProfile(task.getResult().toString());
                                    }
                                });
                            }
                            else{
                                Toast.makeText(UserProfile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            FirebaseStorage
                    .getInstance()
                    .getReference("usercoverimage/"+ UUID.randomUUID().toString())
                    .putFile(imgPath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserProfile.this, "Update Completed", Toast.LENGTH_SHORT).show();
                                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        updateImgProfile(task.getResult().toString());
                                    }
                                });
                            }
                            else{
                                Toast.makeText(UserProfile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private void updateImgProfile(String url){
        if (getImg == true){
            FirebaseDatabase
                    .getInstance()
                    .getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/userpicture")
                    .setValue(url);
        }
        else{
            FirebaseDatabase
                    .getInstance()
                    .getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/coverImg")
                    .setValue(url);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!=null){
            imgPath = data.getData();
            getImageProfile();
            getUserProfile();

        }
    }

    private void getImageProfile(){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getImg == true){
            binding.userImgProfile.setImageBitmap(bitmap);
        }
        else{
            binding.coverPhoto.setImageBitmap(bitmap);
        }

    }
}
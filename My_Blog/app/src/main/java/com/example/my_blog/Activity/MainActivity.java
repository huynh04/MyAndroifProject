package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.my_blog.Adapter.StatusAdapter;
import com.example.my_blog.DoMain.Status;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String username,uploadId,userpicture;
    ArrayList<Status>statuses;
    Status status;
    ArrayList<User>users;
    Uri imgPath;
    StatusAdapter statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        statuses = new ArrayList<>();
        users = new ArrayList<>();
        binding.recyclerview.setHasFixedSize(true);

        statusAdapter=new StatusAdapter(statuses,MainActivity.this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.recyclerview.setAdapter(statusAdapter);

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserProfile.class));
            }
        });

        binding.btnAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg();

                StatusAdapter();
                startActivity(new Intent(MainActivity.this,MainActivity.class));

            }
        });

        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });
        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FriendMessage.class));
            }
        });
        getUsername();
        StatusAdapter();
    }
    public void getUsername(){
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
                        User user = dataSnapshot.getValue(User.class);
                        users.add(user);
                    }

                }
                for (int i = 0; i< users.size();i++){
                    username = users.get(i).getUsername().toString();
                    userpicture = users.get(i).getUserpicture().toString();
                    if(userpicture.equals("")){
                        Picasso.with(MainActivity.this).load(R.mipmap.ic_launcher).into(binding.userImage);
                    }
                    else{
                        Picasso.with(MainActivity.this).load(userpicture).into(binding.userImage);
                    }

                    binding.username.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgPath = data.getData();
            Picasso.with(this).load(imgPath).into(binding.picture);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void uploadImg(){
        if (imgPath != null){
            FirebaseStorage
                    .getInstance()
                    .getReference("picture/"+ System.currentTimeMillis()+"."+getFileExtension(imgPath))
                    .putFile(imgPath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Upload Completed", Toast.LENGTH_SHORT).show();
                                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        status = new Status(binding.userThinking.getText().toString(),task.getResult().toString(),username,userpicture);
                                        uploadId = FirebaseDatabase.getInstance().getReference("status").push().getKey();
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference("status/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uploadId).setValue(status);
                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            FirebaseDatabase
                    .getInstance()
                    .getReference("status/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .push()
                    .setValue(new Status(binding.userThinking.getText().toString(),"",username,userpicture));
        }

    }

    public void StatusAdapter(){
        FirebaseDatabase
                .getInstance()
                .getReference("status/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statuses.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    statuses.add(dataSnapshot.getValue(Status.class));
                }
                statusAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
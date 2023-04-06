package com.example.my_blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.my_blog.Adapter.UserExistAdapter;
import com.example.my_blog.DoMain.User;
import com.example.my_blog.R;
import com.example.my_blog.databinding.ActivityLoginAndSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginAndSignUp extends AppCompatActivity {
    ActivityLoginAndSignUpBinding binding;
    boolean check = true;
    String name,email,password;
    ArrayList<User>users;
    UserExistAdapter userExistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginAndSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = false;
                binding.username.setVisibility(View.VISIBLE);
                binding.login.setVisibility(View.VISIBLE);
                binding.btnLoginSignUp.setText("Sign Up");
                binding.signUp.setVisibility(View.GONE);

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                binding.username.setVisibility(View.GONE);
                binding.signUp.setVisibility(View.VISIBLE);
                binding.login.setVisibility(View.GONE);
                binding.btnLoginSignUp.setText("Login");

            }
        });



        binding.btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false){
                    name = binding.username.getText().toString();
                    email = binding.account.getText().toString();
                    password = binding.passwordToggle.getText().toString();
                    SignUp();
                }
                else{
                    email = binding.account.getText().toString();
                    password = binding.passwordToggle.getText().toString();
                    Login();
                }
            }
        });

        binding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.passwordToggle.getText().toString();
                binding.passwordToggle.setText(String.valueOf(password));
                binding.showPassword.setImageResource(R.drawable.show_on);
            }
        });

        if(FirebaseAuth.getInstance().getUid() != null){
            startActivity(new Intent(LoginAndSignUp.this,MainActivity.class));
            finish();
        }

        UserExist();

    }

    public void Login(){
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginAndSignUp.this, "Login Completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginAndSignUp.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginAndSignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SignUp(){
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new User(name,email,password,"",""));

                            startActivity(new Intent(LoginAndSignUp.this,MainActivity.class));
                            Toast.makeText(LoginAndSignUp.this, "Sign Up Completed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginAndSignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void UserExist(){
        users.clear();
        FirebaseDatabase
                .getInstance()
                .getReference("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    users.add(dataSnapshot.getValue(User.class));
                }
                userExistAdapter = new UserExistAdapter(LoginAndSignUp.this,users);
                binding.RRUserExist.setLayoutManager(new LinearLayoutManager(LoginAndSignUp.this,LinearLayoutManager.HORIZONTAL,false));
                binding.RRUserExist.setAdapter(userExistAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.newsdaily.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newsdaily.Model.Articles;
import com.example.newsdaily.R;
import com.example.newsdaily.databinding.ActivityDetailArticlesBinding;
import com.squareup.picasso.Picasso;

public class DetailArticles extends AppCompatActivity {
    ActivityDetailArticlesBinding binding;
    Articles articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        articles = (Articles) getIntent().getSerializableExtra("data");
        showDetailContent();

    }

    public void showDetailContent(){
        binding.newsDetailTitle.setText(articles.getTitle());
        if (!articles.getUrlToImage().equals("")){
            Picasso.get().load(articles.getUrlToImage()).into(binding.newsDetailImage);
        }
        binding.newsDetailAuthor.setText(articles.getAuthor());
        binding.newsDetailTime.setText(articles.getPublishedAt());
        binding.newsDetailContent.setText(articles.getContent());
    }
}
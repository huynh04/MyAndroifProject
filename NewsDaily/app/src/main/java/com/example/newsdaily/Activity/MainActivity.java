package com.example.newsdaily.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsdaily.Adapter.CategoryNewsAdapter;
import com.example.newsdaily.Adapter.HeadlineNewsAdapter;
import com.example.newsdaily.Listener.CategoryListener;
import com.example.newsdaily.Listener.OnFetchDataListener;
import com.example.newsdaily.Listener.SelectedListener;
import com.example.newsdaily.Model.APIResponse;
import com.example.newsdaily.Model.Articles;
import com.example.newsdaily.Model.RequestManager;
import com.example.newsdaily.R;
import com.example.newsdaily.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectedListener, CategoryListener {
    ActivityMainBinding binding;
    HeadlineNewsAdapter newsAdapter;
    ProgressDialog dialog;
    RequestManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles ...");
        dialog.show();

        manager = new RequestManager(MainActivity.this);
        manager.getArticles(listener,"general",null);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                manager.getArticles(listener,"general",null);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        showCategory();
        searchWords();
    }

    private final OnFetchDataListener<APIResponse> listener = new OnFetchDataListener<APIResponse>() {
        @Override
        public void onFetchData(List<Articles> articles, String message) {
            showNews(articles);
            dialog.dismiss();

        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<Articles> articles) {
        newsAdapter = new HeadlineNewsAdapter(articles,this,this);
        binding.headlineNewsMain.setHasFixedSize(true);
        binding.headlineNewsMain.setLayoutManager(new GridLayoutManager(this,1));
        binding.headlineNewsMain.setAdapter(newsAdapter);
    }

    @Override
    public void OnNewsClicked(Articles articles) {
        startActivity(new Intent(MainActivity.this,DetailArticles.class).
                putExtra("data",articles));

    }


    @Override
    public void OnClicked(String category) {
        manager.getArticles(listener,category,null);
    }

    public void showCategory(){
        String[] category = {"business", "entertainment","general","health","science","sports","technology"};
        List<String>category_list = new ArrayList<>();
        for (String x:category) {
            category_list.add(x);

        }
        CategoryNewsAdapter categoryNewsAdapter = new CategoryNewsAdapter(category_list,MainActivity.this,this);
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewCategory.setHasFixedSize(true);
        binding.recyclerViewCategory.setAdapter(categoryNewsAdapter);

    }

    public void searchWords(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching for "+ query);
                dialog.show();
                manager.getArticles(listener,null,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
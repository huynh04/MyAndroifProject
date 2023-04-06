package com.example.newsdaily.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdaily.Listener.SelectedListener;
import com.example.newsdaily.Model.Articles;
import com.example.newsdaily.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeadlineNewsAdapter extends RecyclerView.Adapter<HeadlineNewsAdapter.HeadlineNewsViewHolder> {
    List<Articles> articles;
    Context ctx;
    SelectedListener selectedListener;

    public HeadlineNewsAdapter(List<Articles> articles, Context ctx,SelectedListener selectedListener) {
        this.articles = articles;
        this.ctx = ctx;
        this.selectedListener = selectedListener;
    }

    @NonNull
    @Override
    public HeadlineNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_list_items,parent,false);
        return new HeadlineNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineNewsViewHolder holder, int position) {
        holder.text_title_news.setText(articles.get(position).getTitle());
        holder.text_source_news.setText(articles.get(position).getSource().getName());
        if (articles.get(position).getUrlToImage()!=null){
            Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.ImgNews);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedListener.OnNewsClicked(articles.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class HeadlineNewsViewHolder extends RecyclerView.ViewHolder{
        TextView text_title_news, text_source_news;
        ImageView ImgNews;
        CardView cardView;

        public HeadlineNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            text_source_news = itemView.findViewById(R.id.text_news_source);
            text_title_news = itemView.findViewById(R.id.text_news);
            ImgNews = itemView.findViewById(R.id.img_news);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

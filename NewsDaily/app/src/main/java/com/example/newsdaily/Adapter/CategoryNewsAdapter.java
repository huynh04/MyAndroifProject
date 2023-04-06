package com.example.newsdaily.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdaily.Listener.CategoryListener;
import com.example.newsdaily.R;

import java.util.List;

public class CategoryNewsAdapter extends RecyclerView.Adapter<CategoryNewsAdapter.CategoryNewsHolder> {
    List<String> category;
    Context ctx;
    CategoryListener categoryListener;

    public CategoryNewsAdapter(List<String> category, Context ctx,CategoryListener categoryListener) {
        this.category = category;
        this.ctx = ctx;
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public CategoryNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.list_category,parent,false);
        return new CategoryNewsHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryNewsHolder holder, int position) {
        holder.category_content.setText(category.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryListener.OnClicked(holder.category_content.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class CategoryNewsHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView category_content;

        public CategoryNewsHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.category_item);
            category_content = itemView.findViewById(R.id.category_item_text);
        }
    }
}

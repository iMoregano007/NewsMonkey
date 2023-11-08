package com.im_oregano007.newsmonkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends  RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{

    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_display,parent,false);
        return new NewsViewHolder(view);
    }

    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleTextV.setText(article.getTitle());
        holder.sourceTextV.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextV, sourceTextV;
        ImageView imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextV = itemView.findViewById(R.id.article_title);
            sourceTextV = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_img);
        }
    }
}

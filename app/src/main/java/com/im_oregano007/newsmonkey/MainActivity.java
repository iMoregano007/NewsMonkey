package com.im_oregano007.newsmonkey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerView);
        progressBar = findViewById(R.id.progressBar);

        setupRecyclerView();

        getNews();
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show){
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    void getNews(){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("c211f9a1e4bc49309fffee3a03d56343");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),

                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() ->{
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Got Failure", throwable.getMessage());

                    }
                }
        );
    }
}
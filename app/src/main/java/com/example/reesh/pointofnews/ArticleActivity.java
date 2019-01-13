package com.example.reesh.pointofnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        this.context=this;
        Intent intent=getIntent();
        final Article selectedArticle= (Article) intent.getExtras().get("article");
        ListView listView=(ListView)findViewById(R.id.listView);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        FetchEntityVerdictTask fetchEntityVerdictTask=new FetchEntityVerdictTask();
        fetchEntityVerdictTask.setLinearLayout(linearLayout);
        fetchEntityVerdictTask.execute(selectedArticle);


        GetArticlesTask getRelatedArticlesTask=new GetArticlesTask(GetArticlesTask.SEARCH_BY_ARTICLE);
        getRelatedArticlesTask.setListView(listView);
        getRelatedArticlesTask.setBaseArticle(selectedArticle);
        getRelatedArticlesTask.setCurrentContext(this);
        getRelatedArticlesTask.execute(new ArrayList<String>());

        TextView headlineTextView=(TextView)findViewById(R.id.headlineTextView);
        TextView previewTextView=(TextView)findViewById(R.id.previewTextView);
        ImageView sentimentIconImageView=(ImageView)findViewById(R.id.sentimentIconImageView);

        headlineTextView.setText(selectedArticle.getHeadline());
        previewTextView.setText(selectedArticle.getPreviewText());
        sentimentIconImageView.setBackgroundResource(selectedArticle.getVerdictLogoResourceId());

        headlineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL(selectedArticle.getUrl());
            }
        });

        previewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL(selectedArticle.getUrl());
            }
        });

        sentimentIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL(selectedArticle.getUrl());
            }
        });
    }

    public void openURL(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

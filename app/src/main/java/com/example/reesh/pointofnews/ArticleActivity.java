package com.example.reesh.pointofnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent=getIntent();
        Article selectedArticle= (Article) intent.getExtras().get("article");
        ListView listView=(ListView)findViewById(R.id.listView);

        GetArticlesTask getRelatedArticlesTask=new GetArticlesTask();
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
    }
}

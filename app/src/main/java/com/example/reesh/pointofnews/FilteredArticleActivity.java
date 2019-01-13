package com.example.reesh.pointofnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FilteredArticleActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_filtered);

        ListView listView=(ListView)findViewById(R.id.listView);

        Intent intent=getIntent();
        ArrayList query= (ArrayList) intent.getExtras().get("query");
        String filterType=intent.getStringExtra("filterType");
        GetArticlesTask getArticlesTask=new GetArticlesTask(GetArticlesTask.SEARCH_BY_FILTER);
        getArticlesTask.setCurrentContext(this);
        getArticlesTask.setListView(listView);
        getArticlesTask.setFilterType(filterType);

        getArticlesTask.execute(query);
    }
}

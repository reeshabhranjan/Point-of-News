package com.example.reesh.pointofnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private ArrayList<Article> articles;
    private GetArticlesTask getArticlesTask;
    private ArrayList<String> searchQuery;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        context=getApplicationContext();
        context=MainActivity.this;
        setContentView(R.layout.activity_main);
        final SearchView searchView=(SearchView)findViewById(R.id.searchView);
        final ListView listView=(ListView)findViewById(R.id.listView);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                searchQuery= new ArrayList<String> (Arrays.asList(searchView.getQuery().toString().split("\\+")));
//            }
//        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery= new ArrayList<String> (Arrays.asList(searchView.getQuery().toString().split("\\+")));
                getArticlesTask=new GetArticlesTask();
                getArticlesTask.setListView(listView);
                ArticleAdapter articleAdapter=new ArticleAdapter(MainActivity.getContext(),R.layout.news_item,articles);
                getArticlesTask.setArticleAdapter(articleAdapter);
                articles=getArticlesTask.getArticles();
                getArticlesTask.execute(searchQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

//        getArticlesTask=new GetArticlesTask();
//        getArticlesTask.setListView(listView);
//        articles=getArticlesTask.getArticles();
//        getArticlesTask.execute(searchQuery);


    }

    public void download(String url,String name){
        final String name2=name;
        Picasso.with(MainActivity.getContext())
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String root=Environment.getExternalStorageDirectory().toString();
                            File myDir=new File(root+"/PointOfNews");

                            if(!myDir.exists()){
                                myDir.mkdir();
                            }

//                            String name="hello2.jpg";
                            myDir=new File(myDir,name2);
                            FileOutputStream out=new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
                            out.flush();
                            out.close();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}

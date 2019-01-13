package com.example.reesh.pointofnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetArticlesTask extends AsyncTask<ArrayList,Void,ArrayList> {

//    private ArrayAdapter articleAdapter;
//
//    public void setArticleAdapter(ArrayAdapter articleAdapter) {
//        this.articleAdapter = articleAdapter;
//    }

    private Context currentContext;

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    private Article baseArticle=null;

    public void setBaseArticle(Article baseArticle) {
        this.baseArticle = baseArticle;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    private ListView listView;
    private ArrayList<Article> articles;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    protected ArrayList doInBackground(ArrayList... arrayLists) {
        ArrayList<String> query=arrayLists[0];

        if(baseArticle==null){
            articles=Utils.getArticles(query);
        }
        else{
            articles=Utils.getRelatedPages(baseArticle);
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        for (Article article:articles){
            switch (article.getSentimentVerdict()){

                case "positive":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_very_satisfied_24);
                    break;

                case "neutral":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_satisfied_24);
                    break;

                case "negative":
                    article.setVerdictLogoResourceId(R.drawable.baseline_sentiment_very_dissatisfied_24);
                    break;
            }

            MainActivity.download(article.getImageURL(),article.getImageName());
            System.out.println(article.getImageURL()+" "+article.getImageName());
        }

//        MainActivity.download("https://developer.chrome.com/extensions/examples/api/idle/idle_simple/sample-128.png","sample");

        ArticleAdapter articleAdapter=new ArticleAdapter(MainActivity.getContext(),R.layout.news_item,articles);

        listView.setAdapter(articleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String url=articles.get(position).getUrl();
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                MainActivity.getContext().startActivity(intent);

                Article article=articles.get(position);
                Intent intent=new Intent(currentContext,ArticleActivity.class);
                intent.putExtra("article",article);
                currentContext.startActivity(intent);

            }
        });
    }
}

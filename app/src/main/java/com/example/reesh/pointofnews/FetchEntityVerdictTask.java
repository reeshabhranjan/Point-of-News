package com.example.reesh.pointofnews;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.aylien.textapi.TextAPIException;

import java.util.ArrayList;

public class FetchEntityVerdictTask extends AsyncTask<Article,Void,ArrayList> {

    private LinearLayout linearLayout;

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    protected ArrayList<String> doInBackground(Article... articles) {
        Article article=articles[0];
        ArrayList<String> verdictStrings=null;
        try {
            verdictStrings=Utils.entityLevelSentimentAnalysis(article);
        } catch (TextAPIException e) {
            e.printStackTrace();
        }

        return verdictStrings;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {

        ArrayList<EntityVerdict> entityVerdicts=new ArrayList<>();
        for(String verdictString:(ArrayList<String>)arrayList){
            String name=verdictString.split(":")[0];
            String verdict=verdictString.split(":")[1];
            EntityVerdict entityVerdict=new EntityVerdict(name);

            if(verdict.equals("positive")){
                entityVerdict.setImageResourceId(R.drawable.up_arrow);
            }

            else {
                entityVerdict.setImageResourceId(R.drawable.down_arrow);
            }

            LayoutInflater layoutInflater=LayoutInflater.from(ArticleActivity.getContext());
            View verdictTileView=layoutInflater.inflate(R.layout.entity_sentiment_tile,null);
            linearLayout.addView(verdictTileView);

        }
    }
}

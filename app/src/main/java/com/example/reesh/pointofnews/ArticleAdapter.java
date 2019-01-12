package com.example.reesh.pointofnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter {

    private ArrayList objects;

    public ArticleAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.objects=(ArrayList) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View articleView=convertView;

        if(articleView==null){
            articleView=LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item,parent,false
            );
        }

        Article article=(Article) objects.get(position);

        TextView headlineTextView=articleView.findViewById(R.id.headlineTextView);
        TextView previewTextView=articleView.findViewById(R.id.previewTextView);

        headlineTextView.setText(article.getHeadline());
        previewTextView.setText(article.getPreviewText());
        //TODO set analysis icon

        return articleView;
    }
}

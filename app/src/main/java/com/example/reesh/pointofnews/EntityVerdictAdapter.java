package com.example.reesh.pointofnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EntityVerdictAdapter extends ArrayAdapter {

    ArrayList objects;

    public EntityVerdictAdapter(@NonNull Context context, int resource, @NonNull List objects){
        super(context, resource, objects);
        this.objects= (ArrayList) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View entityVerdictView=convertView;

        if(entityVerdictView==null){
            entityVerdictView=LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item,parent,false
            );
        }

        EntityVerdict entityVerdict= (EntityVerdict) objects.get(position);

        TextView nameTextView=entityVerdictView.findViewById(R.id.entityTextView);
        ImageView verdictIconImageView=entityVerdictView.findViewById(R.id.verdictIconImageView);

        nameTextView.setText(entityVerdict.getName());
        verdictIconImageView.setBackgroundResource(entityVerdict.getImageResourceId());

        return entityVerdictView;
    }
}

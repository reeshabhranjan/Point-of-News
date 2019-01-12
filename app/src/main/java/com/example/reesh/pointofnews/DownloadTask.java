package com.example.reesh.pointofnews;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.reesh.pointofnews.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... strings) {

        String url=strings[0];

        Picasso.with(MainActivity.getContext())
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String root=Environment.getExternalStorageDirectory().toString();
                            File myDir=new File(root+"/PoN");

                            if(!myDir.exists()){
                                myDir.mkdir();
                            }

                            String name="hello.jpg";
                            myDir=new File(myDir,name);
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

        return "Success";
    }

    @Override
    protected void onPostExecute(String s) {

    }
}

package com.example.reesh.pointofnews;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private String url;
    private String headline;
    private String previewText;
    private double sentimentPercent;
    private String sentimentVerdict;
    private String imageName;
    private String imageURL;
    private int verdictLogoResourceId;
    private String body;
    //TODO add image support

    public Article(String url, String headline, String previewText, double sentimentPercent, String sentimentVerdict, String imageURL, String imageName, String body) {
        this.url = url;
        this.headline = headline;
        this.previewText = previewText;
        this.sentimentPercent = sentimentPercent;
        this.sentimentVerdict = sentimentVerdict;
        this.imageURL=imageURL;
        this.imageName=imageName;
        this.body=body;
    }

    protected Article(Parcel in) {
        url = in.readString();
        headline = in.readString();
        previewText = in.readString();
        sentimentPercent = in.readDouble();
        sentimentVerdict = in.readString();
        imageName = in.readString();
        imageURL = in.readString();
        verdictLogoResourceId = in.readInt();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getBody(){
        return body;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getVerdictLogoResourceId() {
        return verdictLogoResourceId;
    }

    public void setVerdictLogoResourceId(int verdictLogoResourceId) {
        this.verdictLogoResourceId = verdictLogoResourceId;
    }

    //    public void setImgPath(String imageName) {
//        this.imageName = imageName;
//    }

    public String getUrl() {
        return url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPreviewText() {
        return previewText;
    }

    public double getSentimentPercent() {
        return sentimentPercent;
    }

    public String getSentimentVerdict() {
        return sentimentVerdict;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(headline);
        dest.writeString(previewText);
        dest.writeDouble(sentimentPercent);
        dest.writeString(sentimentVerdict);
        dest.writeString(imageName);
        dest.writeString(imageURL);
        dest.writeInt(verdictLogoResourceId);
    }
}

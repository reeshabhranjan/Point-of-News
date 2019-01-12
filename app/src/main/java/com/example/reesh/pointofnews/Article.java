package com.example.reesh.pointofnews;

public class Article {
    private String url;
    private String headline;
    private String previewText;
    private double sentimentPercent;
    private String sentimentVerdict;
    private String imgPath;
    //TODO add image support

    public Article(String url, String headline, String previewText, double sentimentPercent, String sentimentVerdict) {
        this.url = url;
        this.headline = headline;
        this.previewText = previewText;
        this.sentimentPercent = sentimentPercent;
        this.sentimentVerdict = sentimentVerdict;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

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
}

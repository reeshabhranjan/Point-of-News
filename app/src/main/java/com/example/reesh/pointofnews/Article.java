package com.example.reesh.pointofnews;

public class Article {
    private String url;
    private String headline;
    private String previewText;
    private double sentimentPercent;
    private String sentimentVerdict;
    private String imageName;
    private String imageURL;
    //TODO add image support

    public Article(String url, String headline, String previewText, double sentimentPercent, String sentimentVerdict, String imageURL, String imageName) {
        this.url = url;
        this.headline = headline;
        this.previewText = previewText;
        this.sentimentPercent = sentimentPercent;
        this.sentimentVerdict = sentimentVerdict;
        this.imageURL=imageURL;
        this.imageName=imageName;
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
}

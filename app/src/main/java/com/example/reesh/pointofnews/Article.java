package com.example.reesh.pointofnews;

public class Article {
    private String url;
    private String headline;
    private String previewText;
    private double positivePercentage;
    private double neutralPercentage;
    private double negativePercentage;
    private String sentimentVerdict;
    //TODO add image support

    public String getUrl() {
        return url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPreviewText() {
        return previewText;
    }

    public double getPositivePercentage() {
        return positivePercentage;
    }

    public double getNeutralPercentage() {
        return neutralPercentage;
    }

    public double getNegativePercentage() {
        return negativePercentage;
    }

    public String getSentimentVerdict() {
        return sentimentVerdict;
    }
}

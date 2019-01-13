package com.example.reesh.pointofnews;

import com.aylien.newsapi.*;
import com.aylien.newsapi.auth.*;
import com.aylien.newsapi.models.*;
import com.aylien.newsapi.parameters.*;
import com.aylien.newsapi.api.DefaultApi;
import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.*;
import com.aylien.textapi.responses.EntitiesSentiment;
import com.aylien.textapi.responses.EntitiySentiments;
import com.aylien.textapi.responses.EntitySentiment;

import org.json.*;
//import org.json.JSONObject;
//import org.json.simple.parser.*;
import java.lang.reflect.Array;
import java.util.*;
/**
 * Created by patan on 12-01-2019.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}
public class Utils {
    private final static String TextAppId="50531da7";
    private final static String TextAppKey="139fb706befc1f6de68e428151d2dfab";

    private final static String NewsAppId="2332d5e9";
    private final static String NewsAppKey="6f0faba6688630b79da379b8dfe4a418";

    public static void main(String[] args)throws IOException {
        // TODO Auto-generated method stub
        Reader.init(System.in);
        ArrayList<String> entities = new ArrayList<>();
        entities.add("Narendra Modi");
        getArticles(entities);
    }
//    private static void temp(String title)
//    {
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//
//        // Configure API key authorization: app_id
//        ApiKeyAuth app_id = (ApiKeyAuth) defaultClient.getAuthentication("app_id");
//        app_id.setApiKey(appId);
//
//        // Configure API key authorization: app_key
//        ApiKeyAuth app_key = (ApiKeyAuth) defaultClient.getAuthentication("app_key");
//        app_key.setApiKey(appKey);
//
//        DefaultApi apiInstance = new DefaultApi();
//        TimeSeriesParams.Builder timeSeriesBuilder = TimeSeriesParams.newBuilder();
////        timeSeriesBuilder.setEntitiesBodyText(keywords);
//        timeSeriesBuilder.setText(title);
//        timeSeriesBuilder.setLanguage(Arrays.asList("en"));
////        storiesBuilder.setNotLanguage(Arrays.asList("es", "it"));
//        timeSeriesBuilder.setPublishedAtStart("NOW-7DAYS");
//        timeSeriesBuilder.setPublishedAtEnd("NOW");
//        try {
//            TimeSeriesList result = apiInstance.listTimeSeries(timeSeriesBuilder.build());
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling DefaultApi#listTimeSeries");
//            e.printStackTrace();
//        }
//    }
    public static ArrayList<String> entityLevelSentimentAnalysis(String articleBody) throws TextAPIException {
        TextAPIClient client = new TextAPIClient(TextAppId, TextAppKey);
        EntityLevelSentimentParams.Builder builder = EntityLevelSentimentParams.newBuilder();
        builder.setText(articleBody);
        EntitiesSentiment elsa = client.entityLevelSentiment(builder.build());
        // System.out.println(elsa.getText());
        ArrayList<String> output = new ArrayList();
        for (EntitiySentiments entitySentiment: elsa.getEntitiySentiments()) {
            // System.out.println(entitySentiment.getTypes());
            if (entitySentiment.getTypes().equals("Person") || entitySentiment.getTypes().equals("Organisation")){
                String outstring = entitySentiment.getMentions()[0].getText() + ":" + entitySentiment.getMentions()[0].getSentiment().getPolarity() + ":" + entitySentiment.getMentions()[0].getSentiment().getConfidence();
                System.out.println(outstring);
                output.add(outstring);
            }
        }
        return output;

    }
    public static ArrayList<Article> getArticlesBySentiment(ArrayList<String> keywords, String polarity)
    {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: app_id
        ApiKeyAuth app_id = (ApiKeyAuth) defaultClient.getAuthentication("app_id");
        app_id.setApiKey(String.valueOf(NewsAppId));

        // Configure API key authorization: app_key
        ApiKeyAuth app_key = (ApiKeyAuth) defaultClient.getAuthentication("app_key");
        app_key.setApiKey(String.valueOf(NewsAppKey));

        DefaultApi apiInstance = new DefaultApi();

        StoriesParams.Builder storiesBuilder = StoriesParams.newBuilder();

//        storiesBuilder.setText("trump");
        storiesBuilder.setEntitiesBodyText(keywords);
        storiesBuilder.setSortBy("hotness");
        storiesBuilder.setLanguage(Arrays.asList("en"));
//        storiesBuilder.setNotLanguage(Arrays.asList("es", "it"));
        storiesBuilder.setPublishedAtStart("NOW-7DAYS");
        storiesBuilder.setPublishedAtEnd("NOW");
        storiesBuilder.setSentimentBodyPolarity(polarity);
//        storiesBuilder.setEntitiesBodyLinksDbpedia(Arrays.asList(
//                "http://dbpedia.org/resource/Donald_Trump",
//                "http://dbpedia.org/resource/Hillary_Rodham_Clinton"
//        ));
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            Stories result = apiInstance.listStories(storiesBuilder.build());
            for (Iterator i = result.getStories().iterator(); i.hasNext();){
                Story story = (Story) i.next();
//                System.out.println(story.getLinks().getPermalink()+" / "+story.getTitle()+" / "+story.getSummary().getSentences().get(0)+" / "+story.getSentiment().getBody().getScore()+" / "+story.getSentiment().getBody().getPolarity().toString()+" / "+story.getMedia().get(0).getUrl()+" / "+System.currentTimeMillis()+"");
                Article article = new Article(story.getLinks().getPermalink(),story.getTitle(),story.getSummary().getSentences().get(0),story.getSentiment().getBody().getScore(),story.getSentiment().getBody().getPolarity().toString(),story.getMedia().get(0).getUrl(),System.currentTimeMillis()+"");
//                articles.add(article);
                getRelatedPages(article);
//                System.out.println(story.getTitle()+story.getSummary().getSentences()+"\n\n\n\n");
//                System.out.println(story);
                break;
                //                System.out.println(story.getTitle()+" / "+story.getSentiment());
//                System.out.println(story.getTitle() + " / " + story.getSource().getName() +" / "+ story.getMedia().get(0),story.getSentiment().getBody().getPolarity());
//                System.out.println(story);
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#listTimeSeries");
            e.printStackTrace();
        }
        return articles;
    }
    public static ArrayList<Article> getArticles(ArrayList<String> keywords) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: app_id
        ApiKeyAuth app_id = (ApiKeyAuth) defaultClient.getAuthentication("app_id");
        app_id.setApiKey(String.valueOf(NewsAppId));

        // Configure API key authorization: app_key
        ApiKeyAuth app_key = (ApiKeyAuth) defaultClient.getAuthentication("app_key");
        app_key.setApiKey(String.valueOf(NewsAppKey));

        DefaultApi apiInstance = new DefaultApi();

        StoriesParams.Builder storiesBuilder = StoriesParams.newBuilder();

//        storiesBuilder.setText("trump");
        storiesBuilder.setEntitiesBodyText(keywords);
        storiesBuilder.setSortBy("hotness");
        storiesBuilder.setLanguage(Arrays.asList("en"));
//        storiesBuilder.setNotLanguage(Arrays.asList("es", "it"));
        storiesBuilder.setPublishedAtStart("NOW-7DAYS");
        storiesBuilder.setPublishedAtEnd("NOW");
//        storiesBuilder.setEntitiesBodyLinksDbpedia(Arrays.asList(
//                "http://dbpedia.org/resource/Donald_Trump",
//                "http://dbpedia.org/resource/Hillary_Rodham_Clinton"
//        ));
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            Stories result = apiInstance.listStories(storiesBuilder.build());
            for (Iterator i = result.getStories().iterator(); i.hasNext();){
                Story story = (Story) i.next();
                System.out.println(story.getLinks().getPermalink()+" / "+story.getTitle()+" / "+story.getSummary().getSentences().get(0)+" / "+story.getSentiment().getBody().getScore()+" / "+story.getSentiment().getBody().getPolarity().toString()+" / "+story.getMedia().get(0).getUrl()+" / "+System.currentTimeMillis()+"");
                Article article = new Article(story.getLinks().getPermalink(),story.getTitle(),story.getSummary().getSentences().get(0),story.getSentiment().getBody().getScore(),story.getSentiment().getBody().getPolarity().toString(),story.getMedia().get(0).getUrl(),System.currentTimeMillis()+"");
                articles.add(article);
//                getRelatedPages(article);
                //                System.out.println(story.getTitle()+" / "+story.getSentiment());
//                System.out.println(story.getTitle() + " / " + story.getSource().getName() +" / "+ story.getMedia().get(0),story.getSentiment().getBody().getPolarity());
//                System.out.println(story);
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#listStories");
            e.printStackTrace();
        }
        return articles;
    }
    public static ArrayList<Article> getRelatedPages(Article article)
    {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: app_id
        ApiKeyAuth app_id = (ApiKeyAuth) defaultClient.getAuthentication("app_id");
        app_id.setApiKey(String.valueOf(NewsAppId));

        // Configure API key authorization: app_key
        ApiKeyAuth app_key = (ApiKeyAuth) defaultClient.getAuthentication("app_key");
        app_key.setApiKey(String.valueOf(NewsAppKey));

        DefaultApi apiInstance = new DefaultApi();

        RelatedStoriesParams.Builder storiesBuilder = RelatedStoriesParams.newBuilder();

//        storiesBuilder.setText("trump");
//        storiesBuilder.setEntitiesBodyText(keywords);
//        storiesBuilder.setSortBy("hotness");
        storiesBuilder.setLanguage(Arrays.asList("en"));
//        storiesBuilder.setNotLanguage(Arrays.asList("es", "it"));
        storiesBuilder.setPublishedAtStart("NOW-30DAYS");
        storiesBuilder.setPublishedAtEnd("NOW");
        storiesBuilder.setStoryTitle(article.getHeadline());
        storiesBuilder.setStoryBody(article.getPreviewText());
//        storiesBuilder.setEntitiesBodyLinksDbpedia(Arrays.asList(
//                "http://dbpedia.org/resource/Donald_Trump",
//                "http://dbpedia.org/resource/Hillary_Rodham_Clinton"
//        ));
        ArrayList<Article> relatedArticles = new ArrayList<Article>();
        try {
            RelatedStories result = apiInstance.listRelatedStories(storiesBuilder.build());
            for (Iterator i = result.getRelatedStories().iterator(); i.hasNext();){
                Story story = (Story) i.next();
                System.out.println(story.getLinks().getPermalink()+" / "+story.getTitle()+" / "+story.getSummary().getSentences().get(0)+" / "+story.getSentiment().getBody().getScore()+" / "+story.getSentiment().getBody().getPolarity().toString()+" / "+story.getMedia().get(0).getUrl()+" / "+System.currentTimeMillis()+"");
                System.out.println("\n");
                Article relatedArticle = new Article(story.getLinks().getPermalink(),story.getTitle(),story.getSummary().getSentences().get(0),story.getSentiment().getBody().getScore(),story.getSentiment().getBody().getPolarity().toString(),story.getMedia().get(0).getUrl(),System.currentTimeMillis()+"");
                relatedArticles.add(relatedArticle);
                //                System.out.println(story.getTitle()+" / "+story.getSentiment());
//                System.out.println(story.getTitle() + " / " + story.getSource().getName() +" / "+ story.getMedia().get(0),story.getSentiment().getBody().getPolarity());
//                System.out.println(story);
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#listRelatedStories");
            e.printStackTrace();
        }
//        try {
//            Stories result = apiInstance.listStories(storiesBuilder.build());
//            for (Iterator i = result.getStories().iterator(); i.hasNext();){
//                Story story = (Story) i.next();
//                Article article = new Article(story.getLinks().getPermalink(),story.getTitle(),story.getSummary().getSentences().get(0),story.getSentiment().getBody().getScore(),story.getSentiment().getBody().getPolarity().toString(),story.getMedia().get(0).getUrl(),keywords.get(0));
//                articles.add(article);
//                //                System.out.println(story.getTitle()+" / "+story.getSentiment());
////                System.out.println(story.getTitle() + " / " + story.getSource().getName() +" / "+ story.getMedia().get(0),story.getSentiment().getBody().getPolarity());
////                System.out.println(story);
//            }
//        } catch (ApiException e) {
//            System.err.println("Exception when calling DefaultApi#listStories");
//            e.printStackTrace();
//        }
        return relatedArticles;
    }
//    public static void getEntities(String query)
//    {
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//
//        // Configure API key authorization: app_id
//        ApiKeyAuth app_id = (ApiKeyAuth) defaultClient.getAuthentication("app_id");
//        app_id.setApiKey(String.valueOf(appId));
//
//        // Configure API key authorization: app_key
//        ApiKeyAuth app_key = (ApiKeyAuth) defaultClient.getAuthentication("app_key");
//        app_key.setApiKey(String.valueOf(appKey));
//
//        DefaultApi apiInstance = new DefaultApi();
//
//        String type = "dbpedia_resources";
//        String term = query;
//        String language = "en";
//        Integer perPage = 10;
//
//        AutocompletesParams.Builder autocompletesBuilder = AutocompletesParams.newBuilder();
//
//        autocompletesBuilder.setType(type);
//        autocompletesBuilder.setTerm(term);
//        autocompletesBuilder.setLanguage(language);
//        autocompletesBuilder.setPerPage(perPage);
//
//        try {
//            Autocompletes result = apiInstance.listAutocompletes(autocompletesBuilder.build());
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling DefaultApi#listAutocompletes");
//            e.printStackTrace();
//        }
//    }
}

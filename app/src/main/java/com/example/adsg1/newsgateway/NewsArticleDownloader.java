package com.example.adsg1.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by adsg1 on 5/6/2017.
 */

public class NewsArticleDownloader extends AsyncTask< String, Void, String> {

    NewsService newsServiceRefrence;
    private static final String TAG = "NewsArticleDownloader";
    private final String newsURL = "https://newsapi.org/v1/articles?source=";
    private final String apiKey = "&apiKey=0dc06b9cdcbb4549aae74d60f07a4f0e";
    public  static ArrayList<Article> articleArrayList = new ArrayList<Article>();

    String chanName=null;

    NewsService newsService = new NewsService();



    Uri newsURI = null;
    String urlNews;
    ArrayList<NewsBean> newsArticleList = new ArrayList<>();


    NewsArticleDownloader(NewsService newsServiceRefrence) {
        this.newsServiceRefrence = newsServiceRefrence;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(newsServiceRefrence, "Loading NewsAricle Data Async Method...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected String doInBackground(String... params) {



        urlNews = null;
        newsURI = null;

        urlNews = newsURL+params[0]+apiKey;
        chanName = params[1];

        StringBuffer stringBuffer = new StringBuffer();

        String line, s11;

        try {
            URL url = new URL(urlNews);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line).append('\n');
            }
            Log.d(TAG, "SB string: " + stringBuffer.toString());
        } catch (FileNotFoundException e) {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);
        } catch (Exception e) {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);

        }
        return stringBuffer.toString();
    }


    private ArrayList<Article> parseJSON(String string)
    {

        try
        {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArrayObj = jsonObject.getJSONArray("articles");
            for(int i =0; i<jsonArrayObj.length(); i++)
            {
                String author;
                String title;
                String description;
                String urlToImage;
                String publishedAt;
                String url;

                author = null;
                title = null;
                description = null;
                urlToImage = null;
                publishedAt = null;
                url = null;



                author = jsonArrayObj.getJSONObject(i).getString("author");
                title  = jsonArrayObj.getJSONObject(i).getString("title");
                description =  jsonArrayObj.getJSONObject(i).getString("description");
                urlToImage = jsonArrayObj.getJSONObject(i).getString("urlToImage");
                publishedAt = jsonArrayObj.getJSONObject(i).getString("publishedAt");
                url = jsonArrayObj.getJSONObject(i).getString("url");

                        Article article = new Article();
                        article.setAuthor(author);
                        article.setTitle(title);
                        article.setDescription(description);
                        article.setUrtlToImage(urlToImage);
                        article.setPublishedAt(publishedAt);
                        article.setUrl(url);
                        article.setChannelName(chanName);

                        articleArrayList.add(article);


                }


            }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
            return articleArrayList;

        }


    @Override
        protected void onPostExecute (String string){

        Toast.makeText(newsServiceRefrence, "Loading Stock Data..", Toast.LENGTH_SHORT).show();

        articleArrayList = parseJSON(string);

        if(articleArrayList.size() > 0)
        {
                newsService.setArticles(articleArrayList);

        }


    }

    }


package com.example.adsg1.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by adsg1 on 5/5/2017.
 */

public class NewsSourceDownloader extends AsyncTask< String, Void, String> {


    MainActivity mainActivity;

    private static final String TAG = "NewsSourceDownloader";
    private final String newsURL = "https://newsapi.org/v1/sources?language=en&country=us&category=";
    private final String apiKey = "&apiKey=0dc06b9cdcbb4549aae74d60f07a4f0e";

    Uri newsURI = null;
    String urlNews;
    ArrayList<NewsBean> newsResourceList = new ArrayList<>();
    ArrayList<String> newsCategory = new ArrayList<>();
    ArrayList<String> newsCategoryToBeSent = new ArrayList<>();

    NewsSourceDownloader(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity, "Loading NewsSource Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {



       urlNews = null;
       newsURI = null;

        if(params[0].equals("all"))
        {
            newsURI = Uri.parse(newsURL + apiKey);
            urlNews = newsURI.toString();
        }
        else
        {
            newsURI = Uri.parse(newsURL+params[0]+apiKey);
            urlNews = newsURI.toString();
        }

        StringBuffer stringBuffer = new StringBuffer();

        String line, s11;

        try
        {
            URL url = new URL(urlNews);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            while ((line = reader.readLine()) != null)
            {
                stringBuffer.append(line).append('\n');
            }
            Log.d(TAG, "SB string: " + stringBuffer.toString());
        }
        catch (FileNotFoundException e)
        {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);
        }
        catch (Exception e)
        {
            ArrayList<String> newssource = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(newssource);
        }

        ArrayList<NewsBean> newssource = parseJSON(stringBuffer.toString());
        return String.valueOf(newssource);

    }

    private ArrayList<NewsBean> parseJSON(String string)
    {
        String channelId = null, channelName = null, channelUrl = null, channelCategory = null;
        try
        {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArrayObj = jsonObject.getJSONArray("sources");
            Log.d(TAG, "response1 Length: " +jsonArrayObj.length());

            //NewsResource data
            for(int i =0; i<jsonArrayObj.length(); i++)
            {
                channelId = null;
                channelName = null;
                channelUrl = null;
                channelCategory = null;
                {
                    JSONObject jb1 = jsonArrayObj.getJSONObject(i);
                    channelId = jb1.getString("id");
                    Log.d("[" + i + "]" + "Channelid:", channelId);
                    channelName = jb1.getString("name");
                    Log.d("[" + i + "]" + "Channelname:", channelName);
                    channelUrl = jb1.getString("url");
                    Log.d("[" + i + "]" + "ChannelURL:", channelUrl);
                    channelCategory = jb1.getString("category");
                    Log.d("[" + i + "]" + "ChannelCategory:", channelCategory);
                }
                NewsBean newsBean = new NewsBean();
                newsBean.setChannelId(channelId);
                newsBean.setChannelName(channelName);
                newsBean.setChannelCategory(channelCategory);

                newsResourceList.add(newsBean);
                newsCategory.add(channelCategory);
            }
            for(int k = 0; k<newsCategory.size(); k++)
            {
                Log.d(TAG, "ResourceList: [" + k + "]" + newsCategory);
            }
            Set<String> hashSet = new HashSet<>();
            hashSet.addAll(newsCategory);
            newsCategory.clear();
            newsCategoryToBeSent.addAll(hashSet);

            Log.d(TAG, "NewResourceList: ["+ "]" + newsCategoryToBeSent);


            for(int k = 0; k<newsResourceList.size(); k++)
            {
                Log.d(TAG, "ResourceList: [" + k + "]" + newsResourceList.get(k).getChannelId());
                Log.d(TAG, "ResourceList: [" + k + "]" + newsResourceList.get(k).getChannelName());
                Log.d(TAG, "ResourceList: [" + k + "]" + newsResourceList.get(k).getChannelUrl());
                Log.d(TAG, "ResourceList: [" + k + "]" + newsResourceList.get(k).getChannelCategory());
            }

            return newsResourceList;

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(String string)
    {
        Toast.makeText(mainActivity, "Loading Stock Data..", Toast.LENGTH_SHORT).show();
        if(newsResourceList.size() > 0)
        {
            mainActivity.setSources(newsResourceList, newsCategoryToBeSent);

        }
    }

}

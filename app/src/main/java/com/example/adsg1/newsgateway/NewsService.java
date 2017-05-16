package com.example.adsg1.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.adsg1.newsgateway.MainActivity.ACTION_MSG_TO_SERVICE;
import static com.example.adsg1.newsgateway.MainActivity.ACTION_NEWS_STORY;

public class NewsService extends Service {

    private int position11;

    private static final String TAG = "NewsService";
    String resourceId = null;
    private boolean running = true;
    public NewsBean sourceBeanFromMain;
    private ServiceReciever serviceReceiver;

    public static ArrayList<Article>    storyList = new ArrayList<>();


    public NewsService() {

        Log.d("RABBI PARTAAP :", "Chal rahi aa main ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("A_Man", "New service started:");

        serviceReceiver = new ServiceReciever();

        IntentFilter filterActionMsgToSrvc = new IntentFilter(ACTION_MSG_TO_SERVICE);
        registerReceiver(serviceReceiver, filterActionMsgToSrvc);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {

                        /*while (storyList.size() == 0) {

                            Log.d("Inside Service "," storyList zero");

                            Thread.sleep(250);
                        }

                        Log.d("INSIDE RUN RABBI :"," ACTION_NEWS_STORY");

                        Intent intentActionNewsStory = new Intent();
                        intentActionNewsStory.setAction(ACTION_NEWS_STORY);
                        intentActionNewsStory.putExtra("storyList", storyList);
                        Log.d("12345hello", storyList.get(0).getAuthor());
                        sendBroadcast(intentActionNewsStory);
                        storyList.removeAll(storyList);*/

                        try{
                            Log.d("HI a","Before while"+storyList.size());
                            while(storyList.isEmpty())
                            {
                                Thread.sleep(200);
                                Log.d("HI a","Aman"+storyList.size());

                            }
                            if(!storyList.isEmpty())
                            {
                                Intent intent = new Intent();
                                intent.setAction(ACTION_NEWS_STORY);
                                intent.putExtra("storyList",storyList);
                                sendBroadcast(intent);
                                storyList.clear();
                                Thread.interrupted();
                            }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        return Service.START_STICKY;
    }

    private void sendMessage(String msg) {
        Intent intent = new Intent();

        sendBroadcast(intent);
    }

    public void setArticles( ArrayList<Article> articleArrayList)
    {

        storyList.clear();
        storyList.addAll(articleArrayList);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        running = false;
        super.onDestroy();
    }


    private class ServiceReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_MSG_TO_SERVICE:
                    if (intent.hasExtra("newsBean")) {
                        sourceBeanFromMain = (NewsBean) intent.getSerializableExtra("newsBean");

                        String sourceFromMain = null;
                        String chanName = null;
                        sourceFromMain = sourceBeanFromMain.getChannelId();
                        chanName = sourceBeanFromMain.getChannelName();

                        new NewsArticleDownloader(NewsService.this).execute(sourceFromMain,chanName);


                        Log.d("RABBI PARTAAP 2 :",sourceBeanFromMain.getChannelName());

                        Log.d("ImageUrl:", "1");
                        Log.d("ID", sourceBeanFromMain.getChannelId());

                    }
            }

        }
    }
}
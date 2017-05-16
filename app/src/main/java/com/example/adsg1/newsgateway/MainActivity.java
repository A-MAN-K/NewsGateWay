package com.example.adsg1.newsgateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter drawerAdapter;
    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<NewsBean> resourceList = new ArrayList<>();
    HashMap<String, NewsBean> newsHashMap = new HashMap<String, NewsBean>();
    Article article = new Article();
    ArrayList<Article> articleArrayList = new ArrayList<Article>();
    ServiceReciever serviceReciever;

    String categorySelector;

    private MyPageAdapter pageAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager pager;

    private Menu optionsMenu;


    static final String ACTION_MSG_TO_SERVICE = "ACTION_MSG_TO_SERVICE";
    static final String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerAdapter = new ArrayAdapter<>(this, R.layout.drawer_list_item, items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        Intent intent = new Intent(MainActivity.this, NewsService.class);
        startService(intent);

        serviceReciever = new ServiceReciever();

        IntentFilter filterActionNewStory = new IntentFilter(ACTION_NEWS_STORY);
        registerReceiver(serviceReciever, filterActionNewStory);


        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        selectItem(position);
                        pager.setBackground(null);

                        for( NewsBean newsBean : resourceList )
                    //    for(int k = 0; k< resourceList.size(); k++)
                        {
                            if(items.get(position).equals(newsBean.getChannelName()))
                            {
                                Intent intentActionMsgToSrvc = new Intent();
                                Log.d("Position", items.get(position));
                                intentActionMsgToSrvc.putExtra("newsBean", newsBean);
                                //Broadcast the intent
                                intentActionMsgToSrvc.setAction(ACTION_MSG_TO_SERVICE);
                                sendBroadcast(intentActionMsgToSrvc);

                                mDrawerLayout.closeDrawer(mDrawerList);
//                                startService(intent);
                            }
                        }

                    }
                }
        );

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        fragments = getFragments();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);


        new NewsSourceDownloader(MainActivity.this).execute("all");




    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);

        optionsMenu = menu;


        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        optionsMenu.clear();

        Log.d("Category List: ",categoryList.toString());

        int i =1;
        menu.add(0, 0, 0, "all");
        for( String string : categoryList)
        {

            menu.add(0, i, 0, string);
            i++;
        }




        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }

        if (item.toString().equals("all"))
        {
            String string = "all";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }
        else if (item.toString().equals("music"))
        {
            String string = "music";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }
        else if (item.toString().equals("science-and-nature"))
        {
            String string = "science-and-nature";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }
        else if (item.toString().equals("gaming"))
        {
            String string = "gaming";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }
        else if (item.toString().equals("politics"))
        {
            String string = "politics";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }

        else if (item.toString().equals("technology"))
        {
            String string = "technology";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }

        else if (item.toString().equals("sport"))
        {
            String string = "sport";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }

        else if (item.toString().equals("business"))
        {
            String string = "business";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }

        else if (item.toString().equals("entertainment"))
        {
            String string = "entertainment";
            new NewsSourceDownloader(MainActivity.this).execute(string);
        }


        return true;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        return fList;
    }

    public void setSources(ArrayList<NewsBean> newsResourseList, ArrayList<String> newsResourseCategory)
    {
        newsHashMap.clear();
        items.clear();
        resourceList.clear();
        resourceList.addAll(newsResourseList);
        Log.d(TAG, "StockList" + resourceList.toString());
        Log.d(TAG, String.valueOf(items.size()));
        Log.d(TAG, String.valueOf(resourceList.size()));
        //clear the list of source name

        //Fill the list of sources


        for( NewsBean newsGetSet : resourceList)
        {
            items.add(newsGetSet.getChannelName());
            newsHashMap.put(newsGetSet.getChannelName(), newsGetSet);

        }
        drawerAdapter.notifyDataSetChanged();
        categoryList.addAll(newsResourseCategory);

        invalidateOptionsMenu();

        Log.d(TAG, String.valueOf(items.size()));


        //drawerAdapter.notifyDataSetChanged();



    }



    private void selectItem(int position) {
       // ((TextView) findViewById(R.id.textView)).setText("You picked " + items[position]);
        Toast.makeText(this, "Selected " + items.get(position), Toast.LENGTH_SHORT).show();
        setTitle(items.get(position));
      //  reDoFragments(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

  /*  private void reDoFragments(int idx) {

        for (int i = 0; i < pageAdapter.getCount(); i++)
            pageAdapter.notifyChangeInPosition(i);

        fragments.clear();
        String src = items.get(idx);
        int count = (int) (Math.random() * 8 + 2);

        for (int i = 0; i < count; i++) {
            fragments.add(MyFragment.newInstance(src + " Headline #" + (i+1)));
            //pageAdapter.notifyChangeInPosition(i);
        }

        pageAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);
    }*/

    private void reDoFragments(ArrayList<Article> article)
    {
        for (int i = 0; i < pageAdapter.getCount(); i++)
        {
            pageAdapter.notifyChangeInPosition(i);
        }
        fragments.clear();

        FragmentManager manager = getSupportFragmentManager() ;
        //FragmentTransaction fragmentTransaction = manager.beginTransaction();

        for (int i = 0; i < article.size(); i++)
        {
            Log.d("DEEP", article.get(i).getTitle());
            //pageAdapter.notifyChangeInPosition(i);

          //  fragmentTransaction.add(R.layout.myfragment_layout, MyFragment.newInstance(article.get(i).getTitle(), article.get(i).getUrtlToImage(), article.get(i).getAuthor(), article.get(i).getDescription(), article.get(i).getPublishedAt(), article.get(i).getUrl(), " Page " + (i+1) + " of" + article.size()));
            fragments.add(MyFragment.newInstance(article.get(i).getTitle(), article.get(i).getUrtlToImage(), article.get(i).getAuthor(), article.get(i).getDescription(), article.get(i).getPublishedAt(), article.get(i).getUrl(), " Page " + (i+1) + " of" + article.size(),article.get(i).getChannelName()));

            }




        pageAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(serviceReciever);
        Intent intent = new Intent(MainActivity.this, NewsService.class);
        stopService(intent);
        super.onDestroy();
    }


    private class ServiceReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_NEWS_STORY:
                    if (intent.hasExtra("storyList")) {
                    //    articleArrayList =  intent.getSerializableExtra("storyList");
                        Log.d("Redo Framgments", "Aseem"+intent.getSerializableExtra("storyList"));
                        reDoFragments((ArrayList<Article>) intent.getSerializableExtra("storyList"));
                        Log.d("FromService ", "Aseem"+intent.getSerializableExtra("storyList"));
//                        Log.d("ID", article.getTitle());

                    }
            }

        }
    }


    public class MyPageAdapter extends FragmentPagerAdapter {

        private long baseId = 0;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
          return  fragments.size();
        }
        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }


}

/*



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final MyFragment newInstance(String message)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
        messageTextView.setText(message);

        return v;
    }

}*/


package com.example.adsg1.newsgateway;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adsg1.newsgateway.R;
import com.squareup.picasso.Picasso;
import android.net.NetworkInfo;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MyFragment extends Fragment
{
    final String TAG = "MyFragment";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_MESSAGE1 = "EXTRA_MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "EXTRA_MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "EXTRA_MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "EXTRA_MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "EXTRA_MESSAGE5";
    public static final String EXTRA_MESSAGE6 = "EXTRA_MESSAGE6";
    public static final String EXTRA_MESSAGE7 = "EXTRA_MESSAGE7";
    NetworkInfo activeNetworkInfo;

    public static MyFragment newInstance(String message, String message1,String message2,String message3,String message4, String message5, String message6, String message7)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(6);
        bdl.putString(EXTRA_MESSAGE, message);
        bdl.putString(EXTRA_MESSAGE1, message1);
        bdl.putString(EXTRA_MESSAGE2, message2);
        bdl.putString(EXTRA_MESSAGE3, message3);
        bdl.putString(EXTRA_MESSAGE4, message4);
        bdl.putString(EXTRA_MESSAGE5, message5);
        bdl.putString(EXTRA_MESSAGE6, message6);
        bdl.putString(EXTRA_MESSAGE7, message7);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final String message = getArguments().getString(EXTRA_MESSAGE);
        String message1 = getArguments().getString(EXTRA_MESSAGE1);
        String message2 = getArguments().getString(EXTRA_MESSAGE2);
        String message3 = getArguments().getString(EXTRA_MESSAGE3);
        String message4 = getArguments().getString(EXTRA_MESSAGE4);
        final String message5 = getArguments().getString(EXTRA_MESSAGE5);
        String message6 = getArguments().getString(EXTRA_MESSAGE6);
        String message7 = getArguments().getString(EXTRA_MESSAGE7);
        final String url = message6;
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        TextView authornametext = (TextView)v.findViewById(R.id.authorName);
        final ImageView image = (ImageView) v.findViewById(R.id.imageViewBig);
        TextView title = (TextView)v.findViewById(R.id.Title);
        TextView description = (TextView)v.findViewById(R.id.Description);
        TextView pageno = (TextView)v.findViewById(R.id.pageNumber);
        TextView publishedAt = (TextView)v.findViewById(R.id.publishedDate);
        if(message2 != null) {
            authornametext.setText(message2+","+message7);
        }
        else {
            authornametext.setText("No Information Available!");
        }
        Log.d("imageview", message1);
        if (message1 != null) {

            final String imageurl = message1;
            Log.d("ImageUrl:", message1);
            Log.d("1", "1");

            Picasso picasso = new Picasso.Builder(this.getContext()).listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                { // Here we try https if the http image attempt failed
                    final String changedUrl = imageurl.replace("http:", "https:");
                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder).into(image);
                }
            }).build();
            picasso.load(imageurl).error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder).into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Uri uri = Uri.parse(message5);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
        if(message != null) {
            title.setText(message);
            title.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Uri uri = Uri.parse(message5);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
//
        }
        else {
            title.setText("No Information Available!");
        }
        if(message3 != null) {
            description.setText(message3);
            description.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Uri uri = Uri.parse(message5);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
        else {
            description.setText("No Information Available!");
        }
        if(message5 != null){

               String publishedAtInner = changeDateFormat(message4);
               publishedAt.setText(publishedAtInner);
        }

        pageno.setText(message6);
        return v;
    }

    private String changeDateFormat(String publishedat) {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try{
            date = format.parse(publishedat);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat format1= new SimpleDateFormat("MMM dd, yyyy HH:mm");
        String date1 = format1.format(date);
        return date1;
    }

}
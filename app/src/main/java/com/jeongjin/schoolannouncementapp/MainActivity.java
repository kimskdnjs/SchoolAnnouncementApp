package com.jeongjin.schoolannouncementapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends ListActivity {

    List<String> arrLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref;

        String sample = "Hello world! this is an android program written in java! wow!";
        //substring
        //indexOf
        Log.d("debugging", "sub1:" + sample.substring(0, 5));
        Log.d("debugging", "sub2:" + sample.substring(5));
        String temp = sample.substring(5, 10);
        Log.d("debugging", "index:" + sample.indexOf("!"));
        Log.d("debugging", "subindex:" + sample.substring(sample.indexOf("!")));
        Log.d("debugging", "index2:" + sample.indexOf("!", (sample.indexOf("!") + 1)));
        Log.d("debugging", "index3:" + sample.lastIndexOf("!"));

        try {
            String result = new RetriveFeedTask().execute().get();
            Log.d("debugging", result);
            String boardList = result.substring(1,result.indexOf("]"));
            String linkList = result.substring(result.indexOf("[", 2)+1, result.length()-1);
            List<String> arr = Arrays.asList(boardList.split(","));
            arrLink = Arrays.asList(linkList.split(","));
            Log.d("debugging", "links:"+arrLink);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arr);
            setListAdapter(adapter);

        } catch (Exception e) {

        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Activity fromActivity = MainActivity.this;
        Class toActivity = WebActivity.class;
        Intent intent = new Intent(fromActivity, toActivity);
        intent.putExtra("link", arrLink.get(position).replaceAll("\\s+",""));
        startActivity(intent);

    }
}
class RetriveFeedTask extends AsyncTask<String, Void, String>{
    private Exception exception;
    ArrayList<String> arrayList;
    ArrayList<String> arrayLink;

    protected String doInBackground(String...urls){
        try {
//            String code = getHtml("http://www.wonmyong.se.kr/index/index.do");
            arrayList = new ArrayList<String>();
            arrayLink = new ArrayList<String>();
            String code = getHtml("http://www.wonmyong.es.kr/member/login.do?&des=693351804a28d49a4e035c4f9094222bbc01dbe53c49bebaf21220b198293ffda6e9a7b15645b437d099e599416eebf5eedf1dcf0b2570ad43187a38f80b3b5624ced04a59e5d21e5eeed1860255bead");
            Log.d("debugging", "code:"+code);
            Log.d("debugging", ""+arrayList);
            return arrayList.toString() + arrayLink.toString();
        } catch(IOException e){

        }
        return "ERROR";
    }

    public String getHtml(String url) throws IOException {
        //Build and set timeout values for the request
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        //Read and store the result line by line then return the entire string
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        String source = html.toString();
        String board = source.substring(source.indexOf("class=\"boardList\""));
        String temp = board;
        for (int i = 0; i < 10; i = i + 1) {
            temp = temp.substring(temp.indexOf("<li><h1><a href=")+17);
            String item2 = temp.substring(0,temp.indexOf("\""));
            arrayLink.add(item2);

            temp = temp.substring(temp.indexOf("style=\"float:left; padding-top:3px;\">") + 37);
            // 여름방학 ... 부터 안내<까지 잘라주기
//        temp = board.substring((board.indexOf(">")));
//        temp = board.substring((board.indexOf(">") + 21));
            String item = temp.substring(0, temp.indexOf("<"));
            arrayList.add(item);
        }
        return temp;

    }
}

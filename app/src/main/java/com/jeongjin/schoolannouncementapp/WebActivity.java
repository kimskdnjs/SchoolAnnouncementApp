package com.jeongjin.schoolannouncementapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    String redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle extras = getIntent().getExtras();

        redirect = "http://www.wonmyong.es.kr/notice/board.do?bcfNo=611584";
        if (extras != null){
            redirect = "http://www.wonmyong.es.kr" + extras.getString("link");
        }

//        String url = "http://www.wonmyong.es.kr/index/index.do#pop_header";
        String url = "http://www.wonmyong.es.kr/member/login.do?&des=693351804a28d49a4e035c4f9094222bbc01dbe53c49bebaf21220b198293ffda6e9a7b15645b437d099e599416eebf5eedf1dcf0b2570ad43187a38f80b3b5624ced04a59e5d21e5eeed1860255bead";
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                if(url.equals("http://www.wonmyong.es.kr/index/index.do")){
                    view.loadUrl(redirect);
                }
            }
        });
        myWebView.loadUrl (url);
    }
}

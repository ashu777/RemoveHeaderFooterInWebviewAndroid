package com.dash.removeheaderfooterinwebviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        url="your URL here...";
        new MyAsynTask().execute();

    }

    private class MyAsynTask extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... voids) {

            Document document = null;
            try {
                document = Jsoup.connect(url).get();
                document.getElementsByClass("navbar header-nav navbar-expand-lg").remove();// add the class names which you want to remove from WebView.
                document.getElementsByClass("footer footer-dark").remove();
                document.getElementsByClass("floating-bottom-mobile-menu-overlay").remove();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            webView.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", "");
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
        }
    }

}
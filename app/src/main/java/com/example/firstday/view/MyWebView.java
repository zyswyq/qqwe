package com.example.firstday.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.firstday.util.IMyWeb;

/**
 * sadsadas
 */
public class MyWebView extends WebView {
    private IMyWeb myWeb;

    public void setMyWeb(IMyWeb myWeb) {
        this.myWeb = myWeb;
    }

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultWebSettings();
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new GIWebViewClient());
    }


    @SuppressLint("SetJavaScriptEnabled")
    public WebSettings initDefaultWebSettings() {
        WebSettings webSettings = null;
        if (!isInEditMode()) {
            webSettings = getSettings();
            webSettings.setSavePassword(false);
            webSettings.setSaveFormData(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
        }
        return webSettings;
    }

    //监听加载过程
    class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (myWeb != null) {
                myWeb.getProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (myWeb != null) {
                myWeb.getTitle(title);
            }
        }
    }

    class GIWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (myWeb != null)
                myWeb.errorLoad(errorCode, description);
        }
    }


}

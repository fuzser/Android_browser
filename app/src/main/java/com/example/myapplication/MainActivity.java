package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private String currentUrl, url, searchEngine;
    private EditText editText;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    private LinearLayout linearLayout2;
    private ConstraintLayout constraintLayout;
    private ArrayList<String> list, urlList;

    private ListView listView;

    private boolean listViewCheck,loadCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient());
        searchEngine = "www.google.com/search?q=";
    listViewCheck=true;
        editText = findViewById(R.id.editText1);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        linearLayout2 = findViewById(R.id.linear2);
        constraintLayout = findViewById(R.id.constraintLayout);
        listView = findViewById(R.id.listView1);
        list = new ArrayList<>();
        urlList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        //edit text input
        String head = "https://";

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {

                    url = editText.getText().toString();

                    if (!url.isEmpty()) {
                        if (!url.contains(".")) {
                            webView.loadUrl(head + searchEngine + url);
                        } else {

                            if (url.matches("^[0-9.]+$")) {
                                webView.loadUrl(head + searchEngine + url);
                            } else {
                                if (url.toLowerCase().contains(head)) {

                                    webView.loadUrl(url);
                                    editText.setText(null);
                                } else {
                                    webView.loadUrl(head + url);
                                }
                            }
                        }
                    }
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    editText.setText(null);
                }
                return false;
            }
        });


        //button function
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listViewCheck==true){
                    ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                    layoutParams.height=constraintLayout.getHeight()/2;
                    listView.setLayoutParams(layoutParams);
                    listViewCheck=false;
                }else {
                    ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                    layoutParams.height=1;
                    listView.setLayoutParams(layoutParams);
                    listViewCheck=true;
                }


            }
        });

        //web view function
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (error.getDescription().toString().contains("ERR_NAME_NOT_RESOLVED")) {
                    webView.loadUrl(head + searchEngine + url);
                }
            }



        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress >= 80) {
                    if(loadCheck==true){
                        list.add(view.getTitle().toString());
                        urlList.add(view.getUrl().toString());
                        loadCheck=false;
                        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                        listView.setLayoutParams(layoutParams);
                    }

                }else {
                    loadCheck=true;
                }
            }
        });

        //list view function

        listView.setOnItemClickListener((parent,view,loction,id)->{

            url = urlList.get(loction);
            webView.loadUrl(url);
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                    layoutParams.height=1;
                    listView.setLayoutParams(layoutParams);
                    listViewCheck=true;

                }

                return false;
            }
        });
        webView.loadUrl("https://www.google.com");



        ViewTreeObserver viewTreeObserver = constraintLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int screenOrientation = getResources().getConfiguration().orientation;
                if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {


                    ViewGroup.LayoutParams layoutParams0 = linearLayout2.getLayoutParams();
                    layoutParams0.height = constraintLayout.getHeight() / 20;
                    linearLayout2.setLayoutParams(layoutParams0);

                } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {


                    ViewGroup.LayoutParams layoutParams0 = linearLayout2.getLayoutParams();
                    layoutParams0.height = constraintLayout.getWidth() / 20;
                    linearLayout2.setLayoutParams(layoutParams0);
                }
                constraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        if (savedInstanceState != null) {
            currentUrl = savedInstanceState.getString("currentUrl");
        } else {
            webView.loadUrl("https://www.google.com");
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        if (hasFocus) {


            if(listViewCheck==false){
                ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                layoutParams.height=constraintLayout.getHeight()/2;
                listView.setLayoutParams(layoutParams);
            }
            int margin = (int) (linearLayout2.getHeight() * 0.2);
            ViewGroup.LayoutParams layoutParams1 = imageButton1.getLayoutParams();
            layoutParams1.width = (int) (linearLayout2.getHeight() * 0.8);
            layoutParams1.height = (int) (linearLayout2.getHeight() * 0.8);

            imageButton1.setLayoutParams(layoutParams1);
            imageButton2.setLayoutParams(layoutParams1);
            imageButton3.setLayoutParams(layoutParams1);
            imageButton4.setLayoutParams(layoutParams1);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageButton1.getLayoutParams();
            layoutParams.setMargins(margin / 2, margin, margin / 2, margin);
            imageButton1.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams2 = editText.getLayoutParams();
            layoutParams2.width = linearLayout2.getWidth() - (linearLayout2.getHeight() * 4);
            layoutParams2.height = (int) (linearLayout2.getHeight() * 0.9);
            editText.setLayoutParams(layoutParams2);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentUrl", currentUrl);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentUrl = savedInstanceState.getString("currentUrl");
        webView.loadUrl(currentUrl);
    }
}
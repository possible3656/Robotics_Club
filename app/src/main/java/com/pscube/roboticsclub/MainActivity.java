package com.pscube.roboticsclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    WebView webview;
    ProgressBar progressBar;
    FrameLayout frameLayout;

    //for gesture
    GestureDetectorCompat gestureDetector;
    public static final String TAG = "Swipe position";
     public float X1 , X2 , Y1, Y2;
     public static  int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         webview = findViewById(R.id.webViewRoboclub);
         progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        getWebSite();
        frameLayout=findViewById(R.id.mainContainer);

        gestureDetector=new GestureDetectorCompat(MainActivity.this,this );



    }

    private void getWebSite() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });


        webview.loadUrl("https://roboclubrtu.com/");

    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack();
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent e){
        super.dispatchTouchEvent(e);
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       boolean result = true;
       float diffY= e2.getY()-e1.getY();
       float diffX= e2.getX()-e1.getX();
        Log.d(TAG, "onFling: ");

       if (Math.abs(diffX)>Math.abs(diffY)){
           if (Math.abs(diffX)>100 && Math.abs(velocityX)>100) {

               if (diffX < 0) {
                  infoFragment infoFragment = new infoFragment();
                   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                           .add(R.id.mainContainer, infoFragment, null);

                   fragmentTransaction.commit();

               //    Toast.makeText(this, "swipe right", Toast.LENGTH_SHORT).show();
               } else {
                  //startActivity(new Intent(MainActivity.this,MainActivity.class));
                   BlankFragment infoFragment = new BlankFragment();
                   getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,infoFragment).commit();
                   //Toast.makeText(this, "swipe left", Toast.LENGTH_SHORT).show();

               }
               result = true;


           }



       }


       return result;
    }
}

package com.demo.http01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;


public class MainActivity extends Activity {

	private WebView wv;
	private ImageView iv;
	
	private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        wv = (WebView) findViewById(R.id.webview);
        iv = (ImageView) findViewById(R.id.imageView);
        
        new Http_Thread("http://t10.baidu.com/it/u=4043754453,763123347&fm=32&img.jpg", iv, handler).start();
    
    
    }

   
}

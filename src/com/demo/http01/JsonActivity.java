package com.demo.http01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class JsonActivity extends Activity {

	private ListView lv;
	private JsonAdapter adapter;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json_xml);
		//初始化listView
		lv = (ListView)findViewById(R.id.listView);
		//初始化JsonAdapter适配器
		adapter = new JsonAdapter(this);
		//服务器传回的json数据地址(应打开服务器)
		String url = "http://192.168.199.229:8080/web/JsonServerServlet"; 
		//开启Httpjson子线程解析Json，将初始化后的适配器adapter传入子线程
		new HttpJson(url,lv,adapter,handler).start();
		
		
		
	}
}

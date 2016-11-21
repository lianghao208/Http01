package com.demo.http01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends Activity{
	
	private EditText name;
	private EditText age;
	private Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		name = (EditText) findViewById(R.id.name);
		age = (EditText) findViewById(R.id.age);
		
		bt = (Button) findViewById(R.id.regist);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://192.168.199.229:8080/web/MyServerlet";
				new httpThread1(url,name.getText().toString(),age.getText().toString()).start();
			}
		});
		
	}

}

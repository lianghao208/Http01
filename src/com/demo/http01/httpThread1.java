package com.demo.http01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httpThread1 extends Thread{
	
	private String url;
	private String name;
	private String age;
	
	public httpThread1(String url,String name,String age){
		this.url = url;
		this.name = name;
		this.age = age;
	}
	
	private void doGet(){
		url = url+"?name="+name+"&age="+age;
		
		try {
			//创建网页（包含用户提交的数据url）
			URL httpUrl = new URL(url);
			//连接网址
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//把网站数据用BufferReader读取下来保存在reader里
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			//reader数据转换成字符缓存StringBuffer
			StringBuffer sb = new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			
			System.out.println("result"+sb.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void doPost(){

		
		try {
			//创建网页（不包含用户数据，是一个注册页面）
			URL httpUrl = new URL(url);
			//打开网址
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//设置输出流
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			OutputStream out = conn.getOutputStream();
			//把用户提交的数据保存在content里，写入输出流更新网站数据
			String content="name="+name+"&age="+age;
			out.write(content.getBytes());
			//读出更新后的网址数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String str;
			
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println("result"+sb.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//doGet();
		doPost();
	}
}

package com.demo.http01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

//创建一个http子线程
public class Http_Thread extends Thread{
	//创建url地址
	private String url;
	//创建webView
	private WebView webView;
	//创建handler用于向主线程传递数据
	private Handler handler;
	//创建ImageView下载图片
	private ImageView imageView;
	//含参构造器1
	public Http_Thread(String url, WebView webView, Handler handler){
		this.webView = webView;
		this.url = url;
		this.handler = handler;
	}
	
	
	//含参构造器2
		public Http_Thread(String url,ImageView imageView, Handler handler){
			this.url = url;
			this.handler = handler;
			this.imageView = imageView;
		}
	
	@Override
	public void run() {
		
		try {
			//1.用URL标识网址信息
			URL httpUrl = new URL(url);
			//2.用HttpURLConnection打开网址
			HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
			//3.设置打开网页的超时等待时间
			conn.setReadTimeout(5000);
			//4.用GET方式请求访问网页
			conn.setRequestMethod("GET");
			//转换成输入流
			conn.setDoInput(true);
			InputStream in = conn.getInputStream();
			//文件名为当前系统时间，避免重名
			String fileName = String.valueOf(System.currentTimeMillis());
			
			FileOutputStream fos = null;
			File downLoadFile = null;
			//将文件写入SD卡
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//得到SD卡的目录
				File parent = Environment.getExternalStorageDirectory();
				//指定下载文件的路径和文件名
				downLoadFile = new File(parent,fileName);
				
				//将文件输出到默认SD卡目录
				fos = new FileOutputStream(downLoadFile);
			}
			//如果SD卡不为空，则文件输入流不为空，写入数据
			//1.初始化一个字节2k
			byte[] b =new byte[2*1024];
			//设置长度作为一个字节变量
			int len;
			//写入文件
			if(fos!=null){
				Log.i("info", "ok！");
				//read方法返回读到的字节的长度，并将读到的字节保存在数组b中，读到末尾返回-1
				while((len=in.read(b))!=-1){
					//文件输出流写入读到的b数组，从0开始都，读到总共的字节长度停止
					fos.write(b,0,len);
				}
			}
			
			final Bitmap bitmap = BitmapFactory.decodeFile(downLoadFile.getAbsolutePath());
			//向主线程中发送消息队列
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.i("info", "成功！");
					imageView.setImageBitmap(bitmap);
				}
			});
			
			
			/*
			//5.创建字符缓存用于存字符串缓存
			final StringBuffer sb = new StringBuffer();
			//6.获取conn网页的页面信息输入流，传入BufferedReader缓存读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str; 
			//7.一行行将reader的缓存流读入字符串str，直到读完最后一行
			while((str=reader.readLine())!= null){
				sb.append(str);
			}
			
			//post方法向主线程队列发送Runnable对象
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					//相webView中导入数据，第一个参数为字符串数据，第二个参数为mime类型（通过查看网页源码得知）
					//第三个参数为encoding编码类型为空null
					//webView.loadData(sb.toString(), "text/html;charset:utf-8", null);
					webView.loadDataWithBaseURL(url,sb.toString(), "text/html","utf-8",null);
				}
			});
			
			*/
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

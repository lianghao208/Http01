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

//����һ��http���߳�
public class Http_Thread extends Thread{
	//����url��ַ
	private String url;
	//����webView
	private WebView webView;
	//����handler���������̴߳�������
	private Handler handler;
	//����ImageView����ͼƬ
	private ImageView imageView;
	//���ι�����1
	public Http_Thread(String url, WebView webView, Handler handler){
		this.webView = webView;
		this.url = url;
		this.handler = handler;
	}
	
	
	//���ι�����2
		public Http_Thread(String url,ImageView imageView, Handler handler){
			this.url = url;
			this.handler = handler;
			this.imageView = imageView;
		}
	
	@Override
	public void run() {
		
		try {
			//1.��URL��ʶ��ַ��Ϣ
			URL httpUrl = new URL(url);
			//2.��HttpURLConnection����ַ
			HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
			//3.���ô���ҳ�ĳ�ʱ�ȴ�ʱ��
			conn.setReadTimeout(5000);
			//4.��GET��ʽ���������ҳ
			conn.setRequestMethod("GET");
			//ת����������
			conn.setDoInput(true);
			InputStream in = conn.getInputStream();
			//�ļ���Ϊ��ǰϵͳʱ�䣬��������
			String fileName = String.valueOf(System.currentTimeMillis());
			
			FileOutputStream fos = null;
			File downLoadFile = null;
			//���ļ�д��SD��
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//�õ�SD����Ŀ¼
				File parent = Environment.getExternalStorageDirectory();
				//ָ�������ļ���·�����ļ���
				downLoadFile = new File(parent,fileName);
				
				//���ļ������Ĭ��SD��Ŀ¼
				fos = new FileOutputStream(downLoadFile);
			}
			//���SD����Ϊ�գ����ļ���������Ϊ�գ�д������
			//1.��ʼ��һ���ֽ�2k
			byte[] b =new byte[2*1024];
			//���ó�����Ϊһ���ֽڱ���
			int len;
			//д���ļ�
			if(fos!=null){
				Log.i("info", "ok��");
				//read�������ض������ֽڵĳ��ȣ������������ֽڱ���������b�У�����ĩβ����-1
				while((len=in.read(b))!=-1){
					//�ļ������д�������b���飬��0��ʼ���������ܹ����ֽڳ���ֹͣ
					fos.write(b,0,len);
				}
			}
			
			final Bitmap bitmap = BitmapFactory.decodeFile(downLoadFile.getAbsolutePath());
			//�����߳��з�����Ϣ����
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.i("info", "�ɹ���");
					imageView.setImageBitmap(bitmap);
				}
			});
			
			
			/*
			//5.�����ַ��������ڴ��ַ�������
			final StringBuffer sb = new StringBuffer();
			//6.��ȡconn��ҳ��ҳ����Ϣ������������BufferedReader�����ȡ
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str; 
			//7.һ���н�reader�Ļ����������ַ���str��ֱ���������һ��
			while((str=reader.readLine())!= null){
				sb.append(str);
			}
			
			//post���������̶߳��з���Runnable����
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					//��webView�е������ݣ���һ������Ϊ�ַ������ݣ��ڶ�������Ϊmime���ͣ�ͨ���鿴��ҳԴ���֪��
					//����������Ϊencoding��������Ϊ��null
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

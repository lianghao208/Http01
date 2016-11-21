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
			//������ҳ�������û��ύ������url��
			URL httpUrl = new URL(url);
			//������ַ
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//����վ������BufferReader��ȡ����������reader��
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			//reader����ת�����ַ�����StringBuffer
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
			//������ҳ���������û����ݣ���һ��ע��ҳ�棩
			URL httpUrl = new URL(url);
			//����ַ
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//���������
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			OutputStream out = conn.getOutputStream();
			//���û��ύ�����ݱ�����content�д�������������վ����
			String content="name="+name+"&age="+age;
			out.write(content.getBytes());
			//�������º����ַ����
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

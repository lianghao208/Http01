package com.demo.http01;

public class HttpClientThread extends Thread {

	private String url;
	
	public HttpClientThread(String url){
		this.url = url;
	}
	
	private void dohttpClientGet(){
		//HttpGet httpGet = new HttpGet(url);
		//HttpClient client = new DefaultHttpClient();
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}

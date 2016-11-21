package com.demo.http01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

public class HttpJson extends Thread{

	private String url;
	
	private Context context;
	
	private ListView listView;
	
	private JsonAdapter adapter;
	
	private Handler handler;
	
	public HttpJson(String url,ListView listView,JsonAdapter adapter,Handler handler){
		this.url = url;
		
		this.listView = listView;
		this.adapter = adapter;
		this.handler = handler;
	}
	@Override
	public void run() {
		//把服务器传回的json数据解析成流的形式 
		try {
			URL HttpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)HttpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			//解析完后保存到data的List中
			final List<Person> data = parseJson(sb.toString());
			//保存完后给主线程发送消息
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					//用JsonAdapter的setData方法将解析后的List类型数据data传入适配器中
					adapter.setData(data);
					//启动适配器
					listView.setAdapter(adapter);
				}
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//解析Json数据
	private List<Person> parseJson(String json){
		//要解析的json数据：{"result":1,"personData":[{"name":"nate","age":12,"schoolInfo":[{"school_name":"人大"},{"school_name":"大大"}]},{"name":"Jack","age":24}]}
		
		try {
			//创建json object保存json object，入口参数为传入的json格式数据
			JSONObject object = new JSONObject(json);
			
			List<Person> persons = new ArrayList<Person>();
			
			int result = object.getInt("result");
			//是否为正确的Json object
			if(result == 1){
				
				//创建Json Array保存Json Array[]中的json object
				JSONArray personData = object.getJSONArray("personData");
				
				//遍历json array
				for(int i = 0; i<personData.length();i++){
					Person personObject = new Person();
					
					JSONObject person  = personData.getJSONObject(i);
					String name = person.getString("name");
					int age = person.getInt("age");
					JSONArray schoolInfo = person.getJSONArray("schoolInfo");
					//填充json中名字，年龄信息
					personObject.setName(name);
					personObject.setAge(age);
					//创建schoolInfo的List集合
					List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
					
					for(int j=0;j<schoolInfo.length();j++){
						JSONObject school = schoolInfo.getJSONObject(j);
						String schoolName = school.getString("school_name");
						//填充学校信息
						SchoolInfo info = new SchoolInfo();
						info.setSchool_name(schoolName);
			
						schoolInfoList.add(info);
						personObject.setSchoolInfo(schoolInfoList);
					}
					persons.add(personObject);
					
				}
				//返回解析后List<Person>类型的数据
				return persons;
				
			}else{
				Toast.makeText(context, "Error", 1).show();
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
}

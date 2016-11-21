package com.demo.http01;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JsonAdapter extends BaseAdapter{

	private List<Person> list;
	private Context context;
	
	private LayoutInflater inflater;
	//需要使用的一个参数的构造方法
	public JsonAdapter(Context context){
		this.context = context;
		//初始化布局
		inflater=LayoutInflater.from(context);
	}
	public JsonAdapter(Context context, List<Person> list){
		
		this.context=context;
		this.list = list;
		//设置布局
		inflater=LayoutInflater.from(context);
	}
	
	//设置公有方法，将传入的data赋值给属性list
	public void setData(List<Person> data){
		this.list = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		//是否已经加载布局
		if(convertView==null){
			convertView = inflater.inflate(R.layout.item, null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
		
		}else{
			holder = (Holder) convertView.getTag();
		}
		Person person = list.get(position);
		holder.name.setText(person.getName());
		holder.age.setText(""+person.getAge());
		
		List<SchoolInfo> schools = person.getSchoolInfo();
		SchoolInfo schoolInfo1=schools.get(0);
		SchoolInfo schoolInfo2=schools.get(1);
		
		holder.school1.setText(schoolInfo1.getSchool_name());
		holder.school2.setText(schoolInfo2.getSchool_name());
	
		return convertView;
	}

	class Holder{
		private TextView name;
		private TextView age;
		private TextView school1;
		private TextView school2;
		private ImageView imageView;
		//创建Holder导入布局
		public Holder(View view){
			name=(TextView)view.findViewById(R.id.name);
			age=(TextView)view.findViewById(R.id.age);
			school1=(TextView)view.findViewById(R.id.school1);
			school2=(TextView)view.findViewById(R.id.school2);
			imageView=(ImageView)view.findViewById(R.id.imageView);
		}
	}
}

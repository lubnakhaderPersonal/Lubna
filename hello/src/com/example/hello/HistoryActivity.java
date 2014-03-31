package com.example.hello;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;

public class HistoryActivity extends Activity implements AdapterView.OnItemClickListener{
	
	private ListView spin;
	private DatabaseHelper dbHelper;
	private TextView nameTextView;
	private TextView leftTextView;
	private TextView rightTextView;
	private ArrayList<String> dates;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_layout);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		dates = dbHelper.getDateAndTime();
		String[] dateString = new String[dates.size()];
		dates.toArray(dateString);
		spin=(ListView)findViewById(R.id.spinner);
	    spin.setOnItemClickListener(this) ;
	    
	    nameTextView = (TextView)findViewById(R.id.history__name_Text_View );
	    leftTextView = (TextView)findViewById(R.id.historyLeftPowerDisplay);
	    rightTextView = (TextView)findViewById(R.id.historyRightPowerDisplay);
	    
	    ArrayAdapter<String> aa=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                dateString);  
	    aa.setDropDownViewResource(
			      android.R.layout.simple_list_item_1);
			    spin.setAdapter(aa);
	}

	 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		   String dateAndTime = dates.get(arg2);
		   String[] date = dateAndTime.split(" ");
		   
		   String name = dbHelper.getName(date[0],date[1]);
		   String leftPower = dbHelper.getLeftPower(date[0],date[1]);
		   String rightPower = dbHelper.getRightPower(date[0],date[1]);
		   
		   nameTextView.setText(name);
		   leftTextView.setText(leftPower);
		   rightTextView.setText(rightPower);
		
	}
}

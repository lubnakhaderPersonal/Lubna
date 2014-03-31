package com.example.hello;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class LogActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView textView;
	private DatabaseHelper dbHelper ;
	private Calendar dateAndTime = Calendar.getInstance();
	private TextView dateTextView;
	private TextView timeTextView;
	private double[] results;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_activity);
		
		Bundle bundle = getIntent().getExtras();
		results = bundle.getDoubleArray("results");
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		ArrayList<String> namesArray  = dbHelper.getNames();
		String[] names = new String[namesArray.size()];
		namesArray.toArray(names);
		 textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_textbox);
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names);
		  textView.setAdapter(adapter);
		
		TextView leftTextView = (TextView)findViewById(R.id.leftTextBox);
		TextView rightTextView = (TextView)findViewById(R.id.rightTextBox);
		leftTextView.setText(leftTextView.getText() + " " + String.valueOf(results[0]));
		rightTextView.setText(rightTextView.getText() + " " + String.valueOf(results[1]));
		
		dateTextView = (TextView)findViewById(R.id.dateTextBox);
		timeTextView = (TextView)findViewById(R.id.timeTextBox);
		//start listening to the button click of saveresults
		Button saveButton = (Button)findViewById(R.id.savelogbutton);
		saveButton.setOnClickListener(this);
		
				
		//start listening to the button click of datebutton
		Button dateButton = (Button)findViewById(R.id.datebutton);
		dateButton.setOnClickListener(this);
		
		//start listening to the button click of history button
		Button historyButton = (Button)findViewById(R.id.historybutton);
		historyButton.setOnClickListener(this);
		updateLabel();
				
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.savelogbutton:
			if(textView.getText().toString()== null)
			{
				Toast.makeText(this,"Please type your name.",Toast.LENGTH_LONG).show();
			}
			else if (dateTextView.getText().toString()==null)
			{
				Toast.makeText(this,"Please set the date.",Toast.LENGTH_LONG).show();
			}
			else if (timeTextView.getText().toString() == null)
			{
				Toast.makeText(this,"Please set the time.",Toast.LENGTH_LONG).show();
			}
			else
			{
			 dbHelper.addData(textView.getText().toString(),dateTextView.getText().toString(),timeTextView.getText().toString(),String.valueOf(results[0]),String.valueOf(results[1]));
			}
			Toast.makeText(this,"Results saved.",Toast.LENGTH_LONG).show();
			break;
				
		case R.id.datebutton:
			new DatePickerDialog(this, d,
					dateAndTime.get(Calendar.YEAR),
					dateAndTime.get(Calendar.MONTH),
					dateAndTime.get(Calendar.DAY_OF_MONTH))
					.show();
			break;
		case R.id.timebutton:
			new TimePickerDialog(this, t,
					dateAndTime.get(Calendar.HOUR_OF_DAY),
					dateAndTime.get(Calendar.MINUTE),
					true)
					.show();
			break;
		case R.id.historybutton:
			Intent i = new Intent(this,HistoryActivity.class);
			startActivity(i);
			break;
			
		default:
			break;
			
		}
				
	}
	
	private void updateLabel() {
		dateTextView
		.setText(DateUtils
				.formatDateTime(this,
						dateAndTime.getTimeInMillis(),
						DateUtils.FORMAT_NUMERIC_DATE )
		);
		timeTextView.setText(DateUtils.formatDateTime(this,dateAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_TIME));
	
		}
	
	DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
		int dayOfMonth) {
		dateAndTime.set(Calendar.YEAR, year);
		dateAndTime.set(Calendar.MONTH, monthOfYear);
		dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		updateLabel();
}
	};
	
	TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay,
		int minute) {
		dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
		dateAndTime.set(Calendar.MINUTE, minute);
		updateLabel();
		}
		};
}
		
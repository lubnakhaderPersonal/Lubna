package com.example.hello;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static DatabaseHelper mInstance = null;
  private static final String DATABASE_NAME="constants.db";
  private static final int SCHEMA=1;
  static final String NAME="name";
  static final String DAY="day";
  static final String MONTH="month";
  static final String YEAR = "year";
  static final String HOUR = "hour";
  static final String MINUTES = "minutes";
    static final String LEFTPOWER = "leftpower";
  static final String RIGHTPOWER = "rightpower";
  //static final String VALUE="value";
  static final String TABLE="constants";

  public static DatabaseHelper getInstance(Context ctx) {
      /** 
       * use the application context as suggested by CommonsWare.
       * this will ensure that you dont accidentally leak an Activitys
       * context (see this article for more information: 
       * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
       */
      if (mInstance == null) {
          mInstance = new DatabaseHelper(ctx.getApplicationContext());
      }
      return mInstance;
  }
  
  private DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE constants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, day TEXT,month TEXT,year TEXT,hour TEXT,minutes TEXT, leftpower TEXT, rightpower TEXT);");
   
  }

  public void addData(String nameString, String dateString,
		  				String timeString, String leftPowerString,
		  										String rightPowerString )
  {
	  String[] dates = dateString.split("/");
	  String[] times = timeString.split(":");
	  ContentValues cv=new ContentValues();

	  	
	    cv.put(NAME, nameString);
	    cv.put(DAY, dates[0]);
	    cv.put(MONTH, dates[1]);
	    cv.put(YEAR, dates[2]);
	    cv.put(HOUR, times[0]);
	    cv.put(MINUTES, times[1]);
	    cv.put(LEFTPOWER, leftPowerString);
	    cv.put(RIGHTPOWER, rightPowerString);
	    
	    getWritableDatabase().insert(TABLE, NAME, cv);
	    //getWritableDatabase().insert(TABLE,DATE, cv);
	    
	   
	    //getWritableDatabase().insert(TABLE, TIME, cv);
	  
	    
	    //getWritableDatabase().insert(TABLE, LEFTPOWER, cv);
	    
	    
	    //getWritableDatabase().insert(TABLE, RIGHTPOWER, cv);
	    
  }
  
  public ArrayList<String> getNames()
  {
	  ArrayList<String> searchItems = new ArrayList<String>();
	 // Cursor result=
			//  getReadableDatabase().rawQuery("SELECT  TITLE FROM TABLE ", null);
	  
	  String query=
		        String.format("SELECT %s FROM %s",
		                      NAME,TABLE);

	Cursor result =  getReadableDatabase().rawQuery(query, null);
			  while (result.moveToNext()) {
			  String name = result.getString(0);
			  searchItems.add(name);
			  // do something useful with these
			  }
			  result.close();
			  return searchItems;
  }
  
  public ArrayList<String> getDateAndTime()
  {
	  ArrayList<String> searchItems = new ArrayList<String>();
		 // Cursor result=
				//  getReadableDatabase().rawQuery("SELECT  TITLE FROM TABLE ", null);
		  
		  String query=
			        String.format("SELECT %s,%s,%s,%s,%s FROM %s",
			                      DAY,MONTH,YEAR,HOUR,MINUTES,TABLE);

		Cursor result =  getReadableDatabase().rawQuery(query, null);
				  while (result.moveToNext()) {
				  if(result.getString(0) != null && result.getString(3)!= null)
				  {
				  String date1 = result.getString(0)+"/"+ result.getString(1)+ "/"+ result.getString(2);
				  String time1 = result.getString(3) + ":" + result.getString(4);
				  String name = date1 + " "+ time1;
				  searchItems.add(name);
				  }
				  // do something useful with these
				  }
				  result.close();
				  return searchItems;
  }
  
  public String getName(String date,String time)
  {
	   // Cursor result=
				//  getReadableDatabase().rawQuery("SELECT  TITLE FROM TABLE ", null);
		  
	  String[] dates = date.split("/");
	  String[] times = time.split(":");
		  String query=
			        String.format("SELECT %s FROM %s WHERE %s=%s AND %s=%s AND %s=%s AND %s=%s AND %s=%s",
			                      NAME,TABLE,DAY,dates[0],MONTH,dates[1],YEAR,dates[2],HOUR,times[0],MINUTES,times[1]);

		Cursor result =  getReadableDatabase().rawQuery(query, null);
		result.moveToLast();		  
	    String name = result.getString(0);
	 
	    result.close();
		 return name;
  }
  public String getLeftPower(String date,String time)
  {
	   	  
	  String[] dates = date.split("/");
	  String[] times = time.split(":");
		  String query=
			        String.format("SELECT %s FROM %s WHERE %s=%s AND %s=%s AND %s=%s AND %s=%s AND %s=%s",
			                      LEFTPOWER,TABLE,DAY,dates[0],MONTH,dates[1],YEAR,dates[2],HOUR,times[0],MINUTES,times[1]);



		Cursor result =  getReadableDatabase().rawQuery(query, null);
		result.moveToLast();  
	    String name = result.getString(0);
	 
	    result.close();
		 return name;
  }
  
  public String getRightPower(String date,String time)
  {
	   	  
	  String[] dates = date.split("/");
	  String[] times = time.split(":");
		  String query=
			        String.format("SELECT %s FROM %s WHERE %s=%s AND %s=%s AND %s=%s AND %s=%s AND %s=%s",
			                      RIGHTPOWER,TABLE,DAY,dates[0],MONTH,dates[1],YEAR,dates[2],HOUR,times[0],MINUTES,times[1]);

		Cursor result =  getReadableDatabase().rawQuery(query, null);
		result.moveToLast();	  
	    String name = result.getString(0);
	 
	    result.close();
		 return name;
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion,
                        int newVersion) {
    throw new RuntimeException("not for this project");
  }
}


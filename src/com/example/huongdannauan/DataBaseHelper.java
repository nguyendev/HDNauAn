package com.example.huongdannauan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	 //The Android's default system path of your application database.
	 private static String DB_PATH = "/data/data/com.example.huongdannauan/databases/";
	 private static String DB_NAME = "ListMonAn.db";
	 private SQLiteDatabase myDataBase; 
	 private final Context myContext;
	 /**
	     * Constructor
	     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	     * @param context
	 * @throws IOException 
	     */
	 public DataBaseHelper(Context context) throws IOException {
		 
	    	super(context, DB_NAME, null, 1);
	    	this.myContext=context;
	        boolean dbexist = checkDataBase();
	        if(dbexist)
	        {
	            //System.out.println("Database exists");
	            openDataBase(); 
	        }
	        else
	        {
	            System.out.println("Database doesn't exist");
	            createDataBase();
	        }

	}	
	 /**
	     * Check if the database already exist to avoid re-copying the file each time you open the application.
	     * @return true if it exists, false if it doesn't
	     */
	 private boolean checkDataBase(){
		// boolean dbexist = checkDataBase();
		 File dbFile = myContext.getDatabasePath(DB_NAME);
		 return dbFile.exists();
	    }
	 /**
	     * Creates a empty database on the system and rewrites it with your own database.
	     * */
	 public void createDataBase() throws IOException{
		 
	    	boolean dbExist = checkDataBase();
	 
	    	if(dbExist){
	    		//do nothing - database already exist
	    	}else{
	 
	    		//By calling this method and empty database will be created into the default system path
	               //of your application so we are gonna be able to overwrite that database with our database.
	        	this.getReadableDatabase();
	 
	        	copyDataBase();
	    	}
	 
	 }
	 /**
	     * Copies your database from your local assets-folder to the just created empty database in the
	     * system folder, from where it can be accessed and handled.
	     * This is done by transfering bytestream.
	     * */
	private void copyDataBase() throws IOException{
		// TODO Auto-generated method stub
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
	}
	public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
	public String getAppCategorydetail(){
		 String Table_Name="ListMonAn";
		    SQLiteDatabase db = this.getReadableDatabase();
	    String selectQuery = "SELECT NguyenLieu FROM " + Table_Name;
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    String data = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	data = cursor.getString(cursor.getColumnIndex("NguyenLieu"));
	        } while (cursor.moveToNext());
	        db.close();
	    }
	    cursor.close();
	    return data;    	
	}
	public List<String> getTypeList(){
		List<String> Type = new ArrayList<String>();
		String Table_Name="ListMonAn";
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT Loai FROM " + Table_Name;
		Cursor cursor = db.rawQuery(selectQuery,null);
		if(cursor.moveToFirst()){
			do{
				Type.add(cursor.getString(cursor.getColumnIndex("Loai")));
			} while (cursor.moveToNext());
			db.close();
		}
		cursor.close();
		return Type;
	}
	 @Override
	public synchronized void close() {
	 
	   if(myDataBase != null)
		    myDataBase.close();
	    	super.close();
	 
	 }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}

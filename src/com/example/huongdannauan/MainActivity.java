package com.example.huongdannauan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private RelativeLayout Type_Of_Food; 
	
	//private List<Button> TypeList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CreateLayout();
		setContentView(Type_Of_Food);
	}
	private void CreateLayout(){
		DataBaseHelper dbHelper=null;
		try {
				dbHelper = new DataBaseHelper(this);
			} 	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		dbHelper.openDataBase();
		Type_Of_Food = new RelativeLayout(this);
		Type_Of_Food.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		List<String> Type= dbHelper.getTypeList();
		Type = dbHelper.getTypeList();
		dbHelper.close();
		for (String Item : Type) {
			Button T = new Button(this);
			T.setWidth(LayoutParams.MATCH_PARENT);
			T.setText(Item);
			Type_Of_Food.addView(T);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

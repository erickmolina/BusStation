package com.moviles.bus_station;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.moviles.bus_station.netlibrary.JSONParser;



public class HorarioActivity extends Activity{
	 
	private static AutoCompleteTextView _TextListRutas;
	private static ListView _ListView;
	private Button _Button;
	private static SimpleAdapter _AdapterH;
	private static ArrayList<HashMap<String,String>> _ListaHorarios;
	private static String _RutaNombre;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horario);
		ActionBar actionBar = getActionBar();
				
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		_ListView = (ListView) findViewById(R.id.listView1);
		
		_TextListRutas = (AutoCompleteTextView) findViewById(R.id.autoCompleteRutas);
		_TextListRutas.setAdapter(MainActivity._Adapter);
		_TextListRutas.setThreshold(1);
		_TextListRutas.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				_RutaNombre = _TextListRutas.getText().toString();
				executeAsyncTask();
			}
		});
		_Button = (Button) findViewById(R.id.button1);
		_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_TextListRutas.setText("");
				_ListView.setAdapter(null);
			}
		});
		
	}
	
	public void executeAsyncTask(){
		MyOTask task = new MyOTask(this);
		task.execute();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent upIntent = getIntent();
	            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                TaskStackBuilder.from(this).addNextIntent(upIntent).startActivities();
	                finish();
	            } 
	            else {
	                NavUtils.navigateUpTo(this, upIntent);
	            }
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	static class MyOTask extends AsyncTask<String, Void, Void> {
        WeakReference<HorarioActivity> pcontext;
        public MyOTask(HorarioActivity activity) {
        	pcontext = new WeakReference<HorarioActivity>(activity);
        }
 
        @Override
        protected void onPreExecute() {
           
        }
 
        @Override
        protected Void doInBackground(String... params) {
            JSONParser _mJsonParser = new JSONParser();
            _ListaHorarios = _mJsonParser.obtenerHorarioRuta(_RutaNombre);
            
			return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	_AdapterH = new SimpleAdapter(pcontext.get(),_ListaHorarios,R.layout.horarios_layout,new String[] {"title","item1","item2"},new int[] {R.id.text1,R.id.text2, R.id.text3}); 
        	_ListView.setAdapter(_AdapterH);
        }
    }
}

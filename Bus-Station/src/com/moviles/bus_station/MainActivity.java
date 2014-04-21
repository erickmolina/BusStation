package com.moviles.bus_station;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.TextView;
import android.widget.TextView;

import com.moviles.bus_station.library.DetectorConexion;
import com.moviles.bus_station.library.JSONParser;

public class MainActivity extends Activity {

	 private MyLongTask _Task ;
	 private DetectorConexion _DConexion;
	 private TextView _TxtWelcome;
	 private ImageView _Imagen;
	 private static List<String> _ListaRutas;
	 private static AutoCompleteTextView _TextListRutas;
	 private static ArrayAdapter<String> _Adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		  // Font path
        
 
        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.txt_welcom);
 
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bol.ttf");
 
        // Applying font
        txtGhost.setTypeface(tf);
		
		
		_Task = new MyLongTask(this);
		_TextListRutas = (AutoCompleteTextView) findViewById(R.id.autoCompleteRutas);
		_TxtWelcome = (TextView) findViewById(R.id.txt_welcom);
		_Imagen = (ImageView) findViewById(R.id.imageView1);
		_TextListRutas.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent info = new Intent(getApplicationContext(),InfoActivity.class);
				info.putExtra("Nombre_Ruta", _TextListRutas.getText().toString());
				startActivity(info);
			}
		});
		
		 _DConexion = new DetectorConexion(getApplicationContext());
		 
		 if(_DConexion.tieneConexion() == true){
			 _Task.execute();
			 
		 }
		 else if (_DConexion.tieneConexion() == false){
			verificar();
		 }
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public void verificar(){
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Sin conexión");  
        dialogo1.setMessage("Active la conexión a Internet");
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Intentar nuevamente", new DialogInterface.OnClickListener() {  
            @Override
			public void onClick(DialogInterface dialogo1, int id) {  
            	if(_DConexion.tieneConexion() == true){
            		_Task.execute();
            		
       		 	}
       		 	else if (_DConexion.tieneConexion() == false){
       		 		verificar();
       		 	}
            }  
        });        
        dialogo1.show();
	}
	
	static class MyLongTask extends AsyncTask<String, Void, Void> {
        WeakReference<MainActivity> context;
 
        public MyLongTask(MainActivity activity) {
            context = new WeakReference<MainActivity>(activity);
        }
 
        @Override
        protected void onPreExecute() {
        }
 
        @Override
        protected Void doInBackground(String... params) {
        	JSONParser _JsonParse  = new JSONParser();
        	_ListaRutas = _JsonParse.cargarRutas();
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	_Adapter = new ArrayAdapter<String>(context.get(),android.R.layout.simple_spinner_dropdown_item, _ListaRutas);	
			_TextListRutas.setAdapter(_Adapter);
			_TextListRutas.setThreshold(1);
        }
    }
	
}

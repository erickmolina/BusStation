package com.moviles.bus_station;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moviles.bus_station.adapters.TitleNavigationAdapter;
import com.moviles.bus_station.netlibrary.DetectorConexion;
import com.moviles.bus_station.netlibrary.JSONParser;
import com.moviles.bus_station.objetos.Bus;
import com.moviles.bus_station.objetos.Parada;
import com.moviles.bus_station.objetos.Ruta;
import com.moviles.bus_station.objetos.SpinnerNavItem;


public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

	
	 private Menu _Menu;
	 private static GoogleMap _Map;
	 private ActionBar _ActionBar;
	 private ArrayList<SpinnerNavItem> _NavSpinner;
	 private static  AutoCompleteTextView  _autoComplete;
	 private TitleNavigationAdapter _NavigationAdapter;
	 
	 private DetectorConexion _DConexion;
	 private static Ruta _Ruta;
	 private static int _opcion;
	 public static String _NombreRuta;

	 
	 public static List<String> _ListaRutas;
	 public static ArrayAdapter<String> _Adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_opcion = 1;
		_ActionBar = getActionBar();
		_ActionBar.setDisplayShowTitleEnabled(false);
		_ActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		_NavSpinner = new ArrayList<SpinnerNavItem>();
		_NavSpinner.add(new SpinnerNavItem("Guia", R.drawable.ic_my_places));
		_NavSpinner.add(new SpinnerNavItem("Acerca de", R.drawable.ic_latitude));
		_NavigationAdapter = new TitleNavigationAdapter(getApplicationContext(),_NavSpinner);
		_ActionBar.setListNavigationCallbacks(_NavigationAdapter, this);
		
		 _DConexion = new DetectorConexion(this);
		 if(_DConexion.tieneConexion() == true){
			 loadMap();
			 executeAsyncTask();	 
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
            		loadMap();
            		executeAsyncTask();
       		 	}
       		 	else if (_DConexion.tieneConexion() == false){
       		 		verificar();
       		 	}
            }  
        });        
        dialogo1.show();
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public void loadMap()
    {
		_Map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		_Map.setMyLocationEnabled(true);
		_Map.getUiSettings().setMyLocationButtonEnabled(true);
		_Map.getUiSettings().setZoomControlsEnabled(true);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(criteria.ACCURACY_COARSE);
		LocationManager mLocationManager  = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		String provider = mLocationManager.getBestProvider(criteria, true);
		Location myLocation  = mLocationManager.getLastKnownLocation(provider);	
		_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( myLocation.getLatitude(),myLocation.getLongitude()), 12));		    
    }
	
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
		
		case 0:
			 return true;
		case 1:
			Intent acerca = new Intent(getApplicationContext(),AcercaActivity.class);
			startActivity(acerca);
			return true;
		
		}
		return true;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
        MenuItem searchItem = (MenuItem) menu.findItem(R.id.autocomplete_textview);
        _autoComplete = (AutoCompleteTextView) searchItem.getActionView();
        _autoComplete.setThreshold(1);
        _autoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				_NombreRuta = _autoComplete.getText().toString();
				_opcion = 2;
				 MenuItem stationItem = (MenuItem) _Menu.findItem(R.id.action_ver_paradas);
				 stationItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				 _autoComplete.setText("");
				 Toast toast = Toast.makeText( getApplicationContext(), "Ruta: "+_NombreRuta, Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
				 toast.show();
				 InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			     inputMethodManager.hideSoftInputFromWindow(_autoComplete.getWindowToken(), 0);
				 executeAsyncTask();
			}
		});
        
        _Menu = menu;
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
			case R.id.autocomplete_textview:
				_autoComplete.setText("");
				 MenuItem stationItem = (MenuItem) _Menu.findItem(R.id.action_ver_paradas);
				 stationItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
				 return true;
			case R.id.action_ver_horario:
				Intent horarios = new Intent(getApplicationContext(),HorarioActivity.class);
				startActivity(horarios);
				return true;
			case R.id.action_ver_paradas:
				mostrarInformacionParadas();
				return true;
			case R.id.action_hibrid:
				_Map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				return true;
			case R.id.action_satellite:
				_Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				return true;
			case R.id.action_terrain:
				_Map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				return true;
			case R.id.action_normal:
				_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				return true;
		}
		return true;
	}
	
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public void mostrarInformacionParadas(){
		ArrayList<HashMap<String,String>> ListaParadas = new ArrayList<HashMap<String,String>>();
		List<Parada> paradas= _Ruta.get_Paradas();
		for(int i=0;i<paradas.size();i++){
			Parada parada = paradas.get(i);
			HashMap<String,String> map = new HashMap<String,String>();
         	map.put("title",parada.get_Informacion());
         	map.put("item1",parada.get_Detalle());
         	ListaParadas.add(map); 
		}
		
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),ListaParadas,R.layout.horarios_layout,new String[] {"title","item1",},new int[] {R.id.text1,R.id.text2,}); 
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.alert_listview, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(_NombreRuta);
        ListView lv = (ListView) convertView.findViewById(R.id.listView2);
        lv.setAdapter(adapter);
        alertDialog.show();
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public void executeAsyncTask(){
		MyLongTask  task = new MyLongTask(MainActivity.this);
		 task.execute();
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
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
        	if(_opcion == 1){
	        	_ListaRutas = _JsonParse.cargarListaRutas();
	        	_Ruta = _JsonParse.getAllInformation();
        	}
        	else{
        		_Ruta = _JsonParse.obtenerInformacionRuta(_NombreRuta);
        		
        	}
        	return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	if(_opcion == 1){
	        	_Adapter = new ArrayAdapter<String>(context.get(),android.R.layout.simple_list_item_1, _ListaRutas);	
	        	_autoComplete.setAdapter(_Adapter);
	        	agregarMarcadores();
	    	}
        	else{
        		_Map.clear();
        		agregarMarcadores();
        	}
        }
        
        public  void agregarMarcadores(){
    		List<Parada> paradas  = _Ruta.get_Paradas();
        	List<Bus> unidades =  _Ruta.get_Unidades();
    		for(int cont = 0; cont<paradas.size();cont++){
    			Parada parada = paradas.get(cont);
    			double latitude = Double.parseDouble(parada.get_Latitud());
    			double longitude = Double.parseDouble(parada.get_Longitud());
    			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(parada.get_Informacion());
    			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    			_Map.addMarker(marker);
    		}
    		for(int contu = 0; contu<unidades.size();contu++){
    			Bus unidad = unidades.get(contu);
    			double latitude = Double.parseDouble(unidad.get_Latitud());
    			double longitude = Double.parseDouble(unidad.get_Longitud());
    			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Estado: "+unidad.get_Estado());
    			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    			_Map.addMarker(marker);
    		}
        }
    }
	
}

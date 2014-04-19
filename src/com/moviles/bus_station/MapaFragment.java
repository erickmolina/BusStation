package com.moviles.bus_station;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moviles.bus_station.library.JSONParser;
import com.moviles.bus_station.objetos.Bus;
import com.moviles.bus_station.objetos.Parada;
import com.moviles.bus_station.objetos.Ruta;

public class MapaFragment extends Fragment {
	
	private static GoogleMap _Map;
	private  static Ruta mRuta;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		setHasOptionsMenu(true);
		_Map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.frag_map)).getMap();
		_Map.setMyLocationEnabled(true);
		MyTask task = new MyTask(this);
		task.execute();
		return rootView;
	}
	
	static class MyTask extends AsyncTask<String, Void, Void> {
        WeakReference<MapaFragment> context;
        public MyTask(MapaFragment activity) {
            context = new WeakReference<MapaFragment>(activity);
        }
 
        @Override
        protected void onPreExecute() {
           
        }
 
        @Override
        protected Void doInBackground(String... params) {
            JSONParser _mJsonParser = new JSONParser();
			// Aquí hacemos una tarea laaarga
        	mRuta = _mJsonParser.obtenerInformacionRuta(InfoActivity._NombreRuta);
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	List<Parada> paradas  = mRuta.get_Paradas();
        	List<Bus> unidades =  mRuta.get_Unidades();
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
    			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Identificador: " +unidad.get_Identificador()+"\nEstado: "+unidad.get_Estado());
    			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    			_Map.addMarker(marker);
    		}
    		
    		//Location myLocation = _Map.getMyLocation();
    		//_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( myLocation.getLatitude(),myLocation.getLongitude()), 17));
        
    	}
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.main, menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
	
	
	
}

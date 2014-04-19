package com.moviles.bus_station.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moviles.bus_station.objetos.*;


import android.util.Log;

public class JSONParser {
	private Ruta _InformacionRuta = new Ruta();
	private ServiceHandler _Serviceh = new ServiceHandler();
	private String _Tarifa;
	private String url_unidades = "http://busstation-electivamoviles.rhcloud.com/controllers/unidades.php";
	private String url_rutas = "http://busstation-electivamoviles.rhcloud.com/controllers/rutas.php";
	private String url_horarios = "http://busstation-electivamoviles.rhcloud.com/controllers/horarios.php";
	private String url_paradas = "http://busstation-electivamoviles.rhcloud.com/controllers/paradas.php";
	
	public JSONParser(){
		
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	
	public Ruta obtenerInformacionRuta(String pNombreRuta){
		
		_InformacionRuta.set_Paradas(obtenerParadas(pNombreRuta));
		_InformacionRuta.set_Unidades(obtenerUnidades(pNombreRuta));
		_InformacionRuta.set_Horario(obtenerHorario(pNombreRuta));
		_InformacionRuta.set_Nombre(pNombreRuta);
		_InformacionRuta.set_Tarifa(_Tarifa);
		 Log.d("Ruta: ", "> " + _InformacionRuta.toString());
		return _InformacionRuta; 
		
	}
	
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public JSONObject obtenerJSON(String URL, List<NameValuePair> params ){
		
	    String jsonStr = _Serviceh.makeServiceCall(URL, ServiceHandler.GET,params);
	 
        Log.d("Response: ", "> " + jsonStr);
 
        if (jsonStr != null) {
        	try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                 return jsonObj;
                
        
        	} 
        	catch (JSONException e) {
        		e.printStackTrace();
        	}
        } 
        else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
		return null;
		
	}
	
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public List<Parada> obtenerParadas(String pNombreRuta){
		
		List<Parada> ListaParadas = new ArrayList<Parada>();
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("tag","obtenerParadas"));
	    params1.add(new BasicNameValuePair("Nombre_Ruta",pNombreRuta));
    	try {
            JSONObject jsonObj = obtenerJSON(url_paradas,params1);
             
            // Getting JSON Array node
            JSONArray paradas = jsonObj.getJSONArray("Paradas");
 
                
            for (int i = 0; i < paradas.length(); i++) {
                JSONObject InfParada = paradas.getJSONObject(i);
                 
                String informacion = InfParada.getString("Informacion");
                String latitud = InfParada.getString("Latitud");
                String longitud = InfParada.getString("Longitud");
                Parada parada = new Parada(informacion,latitud,longitud);
                ListaParadas.add(parada);            
            }
    	} 
    	catch (JSONException e) {
    		e.printStackTrace();
        	}
        return ListaParadas; 
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public List<Bus> obtenerUnidades(String pNombreRuta){
		
		List<Bus> ListaUnidades = new ArrayList<Bus>();
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("tag","obtenerUnidades"));
	    params1.add(new BasicNameValuePair("Nombre_Ruta",pNombreRuta));
    	try {
            JSONObject jsonObj = obtenerJSON(url_unidades,params1);
             
            // Getting JSON Array node
            JSONArray unidades = jsonObj.getJSONArray("Unidades");
 
                
            for (int i = 0; i < unidades.length(); i++) {
                JSONObject InfUnidad = unidades.getJSONObject(i);
                 
                String identificador = InfUnidad.getString("Identificador");
                String latitud = InfUnidad.getString("Latitud");
                String longitud = InfUnidad.getString("Longitud");
                String estado =InfUnidad.getString("Estado");
                
                Bus unidad = new Bus(identificador,latitud,longitud,estado);
                ListaUnidades.add(unidad);            
            }
    	} 
    	catch (JSONException e) {
    		e.printStackTrace();
        	}
        return ListaUnidades; 
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public List<Horario> obtenerHorario(String pNombreRuta){
		
		List<Horario> ListaHorario = new ArrayList<Horario>();
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("tag","obtenerHorario"));
	    params1.add(new BasicNameValuePair("Nombre_Ruta",pNombreRuta));
    	try {
            JSONObject jsonObj = obtenerJSON(url_horarios,params1);
             
            // Getting JSON Array node
            JSONArray horarios = jsonObj.getJSONArray("Horarios");
 
                
            for (int i = 0; i < horarios.length(); i++) {
                JSONObject InfHorario = horarios.getJSONObject(i);
                 
                String dias = InfHorario.getString("Dias");
                String hora = InfHorario.getString("HoraSalida");
                _Tarifa = InfHorario.getString("Tarifa");
                Horario horario = new Horario(dias,hora);
                ListaHorario.add(horario);            
            }
    	} 
    	catch (JSONException e) {
    		e.printStackTrace();
        	}
        return ListaHorario; 
	}
	
	/*
	* Descripcion:
	* Entradas:
	* Salidas:
	* Reestricciones:
	*/
	public  List<String> cargarRutas(){
		
		List<String> ListaRutas = new ArrayList<String>();
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	    params1.add(new BasicNameValuePair("tag", "obtenerListaRutas"));
	    
        JSONObject jsonObj = obtenerJSON(url_rutas,params1);
                 
        try{
        	
	        JSONArray rutas = jsonObj.getJSONArray("Rutas");
	                
	        for (int i = 0; i < rutas.length(); i++) {
	            JSONObject InfRuta = rutas.getJSONObject(i);
	             
	            String nombre = InfRuta.getString("Nombre_Ruta");
	            ListaRutas.add(nombre);            
	        }
        }
        catch (JSONException e) {
    		e.printStackTrace();
    	}
                
        
        return ListaRutas;
	}
	
}

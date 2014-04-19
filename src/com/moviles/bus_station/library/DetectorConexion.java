package com.moviles.bus_station.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DetectorConexion {
	
	private Context _Context;
    
    public DetectorConexion(Context pContext){
        _Context = pContext;
    }
 
    public boolean tieneConexion(){
    	try{
	        ConnectivityManager conectividad = (ConnectivityManager) _Context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo[] info = conectividad.getAllNetworkInfo();
	        if (info != null) {
	            for (int i = 0; i < info.length; i++){
	            	if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                {
	            		return true;
	                }
	            }
	            return false;
	        }
	    }
    	catch(Exception e){
    		return false;
    	}
		return false;
    }
}

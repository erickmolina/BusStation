package com.moviles.bus_station.objetos;



/*
* Descripcion:
*
*
*/
public class Parada {
	
	private String _Informacion;
	private String _Latitud;
	private String _Longitud;
	
	
	public Parada(){
		
	}
	
	public Parada(String pInformacion,String pLatitud,String pLongitud){
		_Informacion = pInformacion;
		_Latitud = pLatitud;
		_Longitud = pLongitud;
	}
	
	public String get_Informacion() {
		return _Informacion;
	}
	public void set_Informacion(String _Informacion) {
		this._Informacion = _Informacion;
	}
	public String get_Latitud() {
		return _Latitud;
	}
	public void set_Latitud(String _Latitud) {
		this._Latitud = _Latitud;
	}
	public String get_Longitud() {
		return _Longitud;
	}
	public void set_Longitud(String _Longitud) {
		this._Longitud = _Longitud;
	}

	@Override
	public String toString() {
		return "Parada [ Informacion= " + _Informacion + " Latitud= "
				+ _Latitud + " Longitud= " + _Longitud + " ]";
	}
	
}

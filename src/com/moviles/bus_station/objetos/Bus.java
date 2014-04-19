package com.moviles.bus_station.objetos;



/*
* Descripcion:
*
*
*/
public class Bus {
	
	private String _Identificador;
	private String _Latitud;
	private String _Longitud;
	private String _Estado;
	
	public Bus(){
		
	}
	
	public Bus(String pIdentificador,String pLatitud,String pLongitud, String pEstado){
		_Identificador = pIdentificador;
		_Latitud= pLatitud;
		_Longitud = pLongitud;
		_Estado = pEstado;
	}

	public String get_Identificador() {
		return _Identificador;
	}

	public void set_Identificador(String _Identificador) {
		this._Identificador = _Identificador;
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

	public String get_Estado() {
		return _Estado;
	}

	public void set_Estado(String _Estado) {
		this._Estado = _Estado;
	}

	@Override
	public String toString() {
		return "Bus [ Idenficador= " +_Identificador+ " Latitud= " + _Latitud + ", Longitud= " + _Longitud
				+ ", Estado= " + _Estado + "]";
	}
	
	

}

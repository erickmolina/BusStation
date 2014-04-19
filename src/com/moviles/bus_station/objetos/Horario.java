package com.moviles.bus_station.objetos;


/*
* Descripcion:
*
*
*/

public class Horario {
	
	private String _Dias;
	private String _Hora;
	
	public Horario(){
		
	}
	
	public Horario(String pDias, String pHora){
		_Dias = pDias;
		_Hora = pHora;
	}

	public String get_Dias() {
		return _Dias;
	}

	public void set_Dias(String _Dias) {
		this._Dias = _Dias;
	}

	public String get_Hora() {
		return _Hora;
	}

	public void set_Hora(String _Hora) {
		this._Hora = _Hora;
	}

	@Override
	public String toString() {
		return "Horario [ Dias= " + _Dias + " Hora= " + _Hora + " ]";
	}
	
	

}

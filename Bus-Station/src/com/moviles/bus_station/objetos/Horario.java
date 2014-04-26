package com.moviles.bus_station.objetos;


/*
* Descripcion:
*
*
*/

public class Horario {
	
	private String _Dia;
	private String _HoraInicio;
	private String _HoraFinal;
	private String _RangoSalida;
	public Horario(){
		
	}
	
	public Horario(String pDia, String pHoraInicio, String pHoraFinal, String pRangoSalida){
		_Dia = pDia;
		_HoraInicio = pHoraInicio;
		_HoraFinal = pHoraFinal;
		_RangoSalida = pRangoSalida;
	}

	public String get_Dia() {
		return _Dia;
	}

	public void set_Dia(String _Dia) {
		this._Dia = _Dia;
	}

	public String get_HoraInicio() {
		return _HoraInicio;
	}

	public void set_HoraInicio(String _HoraInicio) {
		this._HoraInicio = _HoraInicio;
	}

	public String get_HoraFinal() {
		return _HoraFinal;
	}

	public void set_HoraFinal(String _HoraFinal) {
		this._HoraFinal = _HoraFinal;
	}

	public String get_RangoSalida() {
		return _RangoSalida;
	}

	public void set_RangoSalida(String _RangoSalida) {
		this._RangoSalida = _RangoSalida;
	}
	
}

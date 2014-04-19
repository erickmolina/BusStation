package com.moviles.bus_station.objetos;

import java.util.List;

/*
* Descripcion:
*
*
*/
public class Ruta {
	
	private List<Parada> _Paradas;
	private List<Horario> _Horario;
	private List<Bus> _Unidades;
	private String _Nombre;
	private String _Tarifa;
	
	public Ruta (){
		
	}
	
	public Ruta(String pNombre, List<Parada> pParadas, List<Horario> pHorario, List<Bus> pUnidades, String pTarifa){
		_Nombre = pNombre;
		_Paradas= pParadas;
		_Horario = pHorario;
		_Unidades= pUnidades;
		_Tarifa =pTarifa;
	}

	public List<Parada> get_Paradas() {
		return _Paradas;
	}

	public String get_Tarifa() {
		return _Tarifa;
	}

	public void set_Tarifa(String _Tarifa) {
		this._Tarifa = _Tarifa;
	}

	public void set_Paradas(List<Parada> _Paradas) {
		this._Paradas = _Paradas;
	}

	public List<Horario> get_Horario() {
		return _Horario;
	}

	public void set_Horario(List<Horario> _Horario) {
		this._Horario = _Horario;
	}

	public List<Bus> get_Unidades() {
		return _Unidades;
	}

	public void set_Unidades(List<Bus> _Unidades) {
		this._Unidades = _Unidades;
	}

	public String get_Nombre() {
		return _Nombre;
	}

	public void set_Nombre(String _Nombre) {
		this._Nombre = _Nombre;
	}
	
	@Override
	public String toString(){
		
		String Informacion = "Nombre: "+_Nombre+"\nTarifa: "+_Tarifa+"\nUnidades "+
				 "\n"+_Unidades.toString();
		
		
		return Informacion;	
	}
}

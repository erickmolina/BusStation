package com.moviles.bus_station;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.moviles.bus_station.library.JSONParser;
import com.moviles.bus_station.objetos.Horario;
import com.moviles.bus_station.objetos.Ruta;

public class HorarioFragment extends Fragment {
	
	private static TextView mNombreRuta;
	private static TextView mTarifa;
	private static GridView mGridv;
	private static List<String> mHorariolist;
	private static View rview;
	private static Ruta _Ruta;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rview = inflater.inflate(R.layout.fragment_horario, container, false);
		mNombreRuta = (TextView) rview.findViewById(R.id.txtname_ruta);
		mTarifa = (TextView) rview.findViewById(R.id.text_tarifa);
		mGridv = (GridView) rview.findViewById(R.id.gridView1);
		mHorariolist = new ArrayList<String>();
		MyOTask mtask = new MyOTask(this);
		mtask.execute();
		
		
		return rview;
	}
	
	static class MyOTask extends AsyncTask<String, Void, Void> {
        WeakReference<HorarioFragment> pcontext;
        public MyOTask(HorarioFragment activity) {
        	pcontext = new WeakReference<HorarioFragment>(activity);
        }
 
        @Override
        protected void onPreExecute() {
           
        }
 
        @Override
        protected Void doInBackground(String... params) {
            JSONParser _mJsonParser = new JSONParser();
			// Aquí hacemos una tarea laaarga
        	_Ruta = _mJsonParser.obtenerInformacionRuta(InfoActivity._NombreRuta);
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	mNombreRuta.setText("Ruta: "+_Ruta.get_Nombre());
    		mTarifa.setText("Tarifa: "+_Ruta.get_Tarifa());
    		List<Horario> horarios = _Ruta.get_Horario();
    		for(int cont = 0; cont<horarios.size();cont++){
    			Horario horario = horarios.get(cont);
    			mHorariolist.add(horario.get_Dias()+"\n"+horario.get_Hora());
    		}
    		mGridv.setAdapter(new ArrayAdapter<String>(rview.getContext(),android.R.layout.simple_list_item_1, mHorariolist));
        }
    }

}

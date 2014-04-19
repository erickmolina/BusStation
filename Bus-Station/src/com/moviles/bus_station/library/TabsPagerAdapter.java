package com.moviles.bus_station.library;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moviles.bus_station.HorarioFragment;
import com.moviles.bus_station.MapaFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new MapaFragment();
		case 1:
			return new HorarioFragment();
		}
		return null;
		
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}

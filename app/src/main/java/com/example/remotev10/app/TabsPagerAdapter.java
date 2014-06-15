package com.example.remotev10.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fm;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
        this.fm = fm;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
            //Fragment controlfragment = new ControlFragment();
            //fm.beginTransaction().add(controlfragment,"control");
			return new ControlFragment();
		case 1:
			// Games fragment activity
			return new InfoFragment();
		case 2:
			// Movies fragment activity
			return new PropertiesFragment();
		}


		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}

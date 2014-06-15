package com.example.remotev10.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PropertiesFragment extends Fragment {
    TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_properties, container, false);

		return rootView;
	}

    public void changeData(String data){
        textView.setText(data);
    }

}

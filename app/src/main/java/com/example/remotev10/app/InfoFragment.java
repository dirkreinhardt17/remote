package com.example.remotev10.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class InfoFragment extends Fragment {
    Button infobutton;
    Communicator comm;
    int count = 0;
    SimpleAdapter adapter;
    ListView listView;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);


        comm = (Communicator) getActivity();

		return rootView;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();


        adapter = new SimpleAdapter(mainActivity, mainActivity.info.data,
                android.R.layout.simple_list_item_2,
                new String[] {"name", "value"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        listView.setAdapter(adapter);


    }

    public void changeData(){
        adapter.notifyDataSetChanged();
    }

    public interface Communicator{
        public void respond(String data);
    }
}

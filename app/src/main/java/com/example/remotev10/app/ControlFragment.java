package com.example.remotev10.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ControlFragment extends Fragment {

    Button button1;
    Button button2;
    TextView textView1;
    TextView textView2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_control, container, false);

		return rootView;
	}


    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        textView1 = (TextView) getActivity().findViewById(R.id.textview1);
        textView2 = (TextView) getActivity().findViewById(R.id.textview2);

        button1 = (Button) getActivity().findViewById(R.id.buttonMod1);
        button2 = (Button) getActivity().findViewById(R.id.buttonMod2);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Variablen.message[3] = (byte) Variablen.functionID_request;
                Variablen.message[4] = 0;

                //wenn in textView1 0 steht, also wenn Modus1 0 ist
                if(textView1.getText().equals(String.valueOf(0))){
                    Variablen.message[5] = 1;
                }
                else if(textView1.getText().equals(String.valueOf(1))){
                    Variablen.message[5] = 0;
                }

                String text = String.format("Setze Modus 1 auf %d",Variablen.message[5]);

                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

                Variablen.flag1 = true;


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Variablen.message[3] = (byte) Variablen.functionID_request;
                Variablen.message[4] = 1;

                //wenn in textView1 0 steht, also wenn Modus1 0 ist
                if(textView2.getText().equals(String.valueOf(0))){
                    Variablen.message[5] = 1;
                }
                else if(textView2.getText().equals(String.valueOf(1))){
                    Variablen.message[5] = 0;
                }

                String text = String.format("Setze Modus 2 auf %d",Variablen.nachricht2[5]);

                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

                Variablen.flag2 = true;

            }
        });

    }

}

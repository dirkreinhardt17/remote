package com.example.remotev10.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by dirk on 02.06.14.
 */
public class ThreadRunnable implements Runnable {
    Handler mhandler;
    Bundle bundle;
    Message msg;

    //buffer for storing received bytes via DatagramPacket
    byte[] buffer = new byte[1024];

    //socket for receiving and sending data
    DatagramSocket socket;

    //Datagrampacket for receiving data
    DatagramPacket receive_packet = new DatagramPacket(buffer, buffer.length);

    //Datagrampacket for sending data
    DatagramPacket send_packet;

    //idx to identify mode
    int idx;

    //state that the indexed modes is to be set to
    int state;

    //if info package has been received, the name of the info is stored in info_name
    String info_name;

    //if info package has been received, the value of the info is stored in info_value
    String info_value;

    //für das Parsen dann nach Empfang der Nachricht
    ByteArrayInputStream byteArrayInputStream;

    //um Infoname und Infovalue zu parsen
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

    public ThreadRunnable(Handler handler){
        this.mhandler = handler;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket = new DatagramSocket(Variablen.lport);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //send connection message before entering while loop
        Variablen.message[4] = (byte) Variablen.functionID_connect;
        send_packet = new DatagramPacket(Variablen.message,Variablen.message.length,Variablen.server_address,Variablen.server_port);

        try {
            socket.send(send_packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){

            msg = new Message();
            msg = mhandler.obtainMessage();

            bundle = new Bundle(2);

            //Log.d("ThreadRunnable","vor receive");
            try {
                socket.receive(receive_packet);
            } catch (IOException e) {
                e.printStackTrace();
            }



            //if button1 is clicked, flag is set true and message is set accordingly
            if(Variablen.flag1)
            {
                send_packet = new DatagramPacket(Variablen.message, Variablen.message.length, Variablen.server_address, Variablen.server_port);
                try {
                    socket.send(send_packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Variablen.flag1 = false;
            }

            //if button2 is clicked, flag is set true
            if(Variablen.flag2)
            {
                send_packet = new DatagramPacket(Variablen.message, Variablen.message.length, Variablen.server_address, Variablen.server_port);
                try {
                    socket.send(send_packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Variablen.flag2 = false;
            }


            byteArrayInputStream = new ByteArrayInputStream(receive_packet.getData());
            try {
                byteArrayOutputStream.write(receive_packet.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            Log.d("ThreadRunnable - receive_packet", String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())
            +String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())
            +String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read())+String.valueOf(byteArrayInputStream.read()));
            byteArrayInputStream.reset();
            */

            //Nachricht parsen
            if(byteArrayInputStream.read() == Variablen.kennung1 && byteArrayInputStream.read() == Variablen.kennung2 &&
                    byteArrayInputStream.read() == Variablen.senderID_server){

                if(byteArrayInputStream.read() == Variablen.functionID_modus){
                    idx = byteArrayInputStream.read();
                    state = byteArrayInputStream.read();

                    bundle.putInt("idx", idx);
                    bundle.putInt("state", state);
                    bundle.putString("typ","mode");
                }
                else{
                    int data_name_len = 0;
                    int data_value_len = 0;
                    while(byteArrayInputStream.read() != 0){
                        data_name_len++;
                    }
                    while(byteArrayInputStream.read() != 0){
                        data_value_len++;
                    }

                    byteArrayOutputStream.write(receive_packet.getData(),4, data_name_len);
                    ByteArrayOutputStream data_name_stream = new ByteArrayOutputStream(data_name_len);
                    ByteArrayOutputStream data_value_stream = new ByteArrayOutputStream(data_value_len);
                    data_name_stream.write(receive_packet.getData(),4,data_name_len);
                    data_value_stream.write(receive_packet.getData(),4+data_name_len+1,data_value_len);

                    info_name = data_name_stream.toString();
                    info_value = data_value_stream.toString();
                    Log.i("info_name: ",info_name);
                    Log.i("info_value: ",info_value);
                    bundle.putString("name", info_name);
                    bundle.putString("value", info_value);
                    bundle.putString("typ","info");
                }

                byteArrayInputStream.reset();

                msg.setData(bundle);
                mhandler.sendMessage(msg);

                //Log.d("dirk", "thread hat was gemacht");
            }

        }




    }

        /*
        bundle.putString("name","Threadinfo"+"3");
        bundle.putString("value","threadvalue");

        msg.setData(bundle);

        mhandler.sendMessage(msg);

        Log.d("dirk", "thread hat was gemacht");
        */

}




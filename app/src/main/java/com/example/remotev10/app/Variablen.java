package com.example.remotev10.app;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dirk on 09.05.14.
 */
public final class Variablen {
    public static String tollerstring;
    public static int bla;
    public static int mod1_stat;
    public static int kennung1;
    public static int kennung2;
    public static int senderID_server;
    public static int senderID_client1;
    public static int functionID_connect;
    public static int functionID_request;
    public static int functionID_modus;
    public static int functionID_info;
    public static boolean listen;
    public static boolean flag1;
    public static boolean flag2;
    public static boolean verbunden;
    //public static DatagramSocket socket;

    //Adresse vom Server
    public static String ipHost;
    public static int lport;
    public static int server_port;
    public static InetAddress server_address;

    public static byte[] nachricht1 = {60,1,1,2,0,1};
    public static byte[] nachricht2 = {60,1,1,2,1,1};

    //dummy message, see protocol
    public static byte[] message = {60,1,1,0,0,0};
    {
        tollerstring = "Hallo";
        bla = 10000;
        listen = true;
        mod1_stat = 0;
        kennung1 = 60;
        kennung2 = 1;
        senderID_server = 0;
        senderID_client1 = 0x01;
        functionID_connect = 0x00;
        functionID_modus = 0x01;
        functionID_request = 0x02;
        functionID_info = 3;
        flag1 = false;
        flag2 = false;
        ipHost = "10.0.2.2";
        lport = 5554;
        server_port = 2001;


        try {
            server_address = InetAddress.getByName(ipHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        


    }
}


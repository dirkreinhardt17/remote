package com.example.remotev10.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Info {

    ArrayList<Map<String, String>> data;
    ArrayList<String> keys;
    ArrayList<Semaphore> semaphores;
    //SimpleAdapter infoAdapter;

    private static Info sInstance = null;

    static {
        sInstance = new Info();
    }

    Info(){
        data = new ArrayList<Map<String, String>>();
        semaphores = new ArrayList<Semaphore>();
        keys = new ArrayList<String>();
        //infoAdapter = simpleAdapter;
    }

    /**
     * Returns the Info object
     * @return The global Info object
     */
    public static Info getInstance() {
        return sInstance;
    }


    public void setValue(String name, String value){

        int idx = keys.indexOf(name);


        try {
            this.semaphores.get(idx).acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data.get(idx).put("value", value);
        this.semaphores.get(idx).release();

    }

    public String getValue(String name){
        int idx = keys.indexOf(name);

        try {
            this.semaphores.get(idx).acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String tempvalue = new String(data.get(idx).get("value"));
        this.semaphores.get(idx).release();

        return tempvalue;
    }

    //Methode um zu schauen, ob die Info schon in der Liste ist
    public boolean compare(String name){
        for(String key_entry : keys)
            if(key_entry.equals(name)){
                return true;
            }
        return false;
    }

    public void add(String name, String value){

        HashMap<String,String> temp = new HashMap<String,String>();
        temp.put("name",name);
        temp.put("value", value);
        data.add(temp);
        keys.add(name); //um in der liste suchen zu können
        semaphores.add(new Semaphore(1, true));
        //this.semaphores.add(new Semaphore(1, true));
    }
}



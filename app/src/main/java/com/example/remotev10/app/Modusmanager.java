package com.example.remotev10.app;

import java.util.Vector;

/**
 * Created by dirk on 11.05.14.
 */
public class Modusmanager {

    Vector<Integer> moduslist;
    Modusmanager(Integer N, Integer M){
        moduslist = new Vector<Integer>(N,M);
        moduslist.add(0);
        moduslist.add(0);
    }

    int GetState(Integer idx) {
        return moduslist.get(idx);
    }

    void SetState(Integer idx, Integer state) {
        moduslist.set(idx, state);
    }
}

//c++ Äquivalent von client.cpp
/*
class Modusmanager{

    public:

    std::vector<int> moduslist;
    Modusmanager(int N) : moduslist(N){};

    int GetState(char idx) {
        return moduslist[idx];
    }


    void SetState(char idx, char state) {
        moduslist[idx] = state;
    }
};
*/
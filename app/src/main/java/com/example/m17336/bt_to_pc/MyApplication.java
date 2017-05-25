package com.example.m17336.bt_to_pc;

import android.app.Application;

import com.example.m17336.bt_to_pc.bluetooth_specifics.CommunicationThread;

/**
 * Created by m17336 on 2/12/2016.
 */
public class MyApplication extends Application {
    private CommunicationThread com;

    public CommunicationThread getCom() {
        return com;
    }

    public void setCom(CommunicationThread com) {
        this.com = com;
    }
}

package com.neuroevolution.robot.simulation.core;

import android.app.Application;

import com.neuroevolution.robot.simulation.bluetooth_specifics.CommunicationThread;

public class MyApplication extends Application {
    private CommunicationThread com;

    public CommunicationThread getCom() {
        return com;
    }

    public void setCom(CommunicationThread com) {
        this.com = com;
    }
}

package com.neuroevolution.robot.simulation;

import android.app.Application;

import com.neuroevolution.robot.simulation.bluetooth_specifics.CommunicationThread;

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

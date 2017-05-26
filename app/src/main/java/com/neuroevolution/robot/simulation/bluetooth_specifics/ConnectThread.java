package com.neuroevolution.robot.simulation.bluetooth_specifics;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;


public class ConnectThread extends Thread {
    private BluetoothSocket socket;
    private Handler handler;
    private String address;

    public ConnectThread(BluetoothDevice device, Handler handler){
        this.handler = handler;
        this.address = device.getAddress();

        BluetoothSocket tmp = null;

        Method m = null;
        try {
            m = device.getClass().getMethod("createInsecureRfcommSocket", new Class[] { int.class });
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            handler.obtainMessage(EventsHandeling.ERROR_RFCOMM).sendToTarget();
            Log.e("err", "1");
        }

        try {
            tmp = (BluetoothSocket)m.invoke(device, Integer.valueOf(1));
        } catch (Exception e) {
            e.printStackTrace();
            handler.obtainMessage(EventsHandeling.ERROR_RFCOMM).sendToTarget();
            Log.e("err", "2");
        }

        Log.v("SOCKET SUCCESS", "HAST SOCKET");
        socket = tmp;


    }

    public void run() {
        try {
            socket.connect();
            manageConnectedSocket();
        } catch (IOException e) {
            handler.obtainMessage(EventsHandeling.ERROR_CONNECT).sendToTarget();
            e.printStackTrace();
        }
    }

    private void manageConnectedSocket() {
        CommunicationThread com = new CommunicationThread(socket, handler, address);
        handler.obtainMessage(EventsHandeling.SOCKET_CONNECTED, com).sendToTarget();
    }
}

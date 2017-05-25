package com.example.m17336.bt_to_pc.bluetooth_specifics;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class CommunicationThread extends Thread {
    public BluetoothSocket socket;
    public Handler handler;
    private InputStream is;
    private OutputStream os;
    public String address;

    public CommunicationThread(BluetoothSocket socket, Handler handler, String address) {
        this.socket = socket;
        this.handler = handler;
        this.address = address;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            handler.obtainMessage(EventsHandeling.ERROR_OBTAIN_STREAMS).sendToTarget();
        }
    }

    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while( true ){
            try {
                String data = br.readLine();
                handler.obtainMessage(EventsHandeling.DATA_RECEIVED, data).sendToTarget();
            } catch (IOException e){
                handler.obtainMessage(EventsHandeling.ERROR_COMMUNICATION).sendToTarget();
            }
        }
    }

    public void write(String message){
        Log.d("Sending", message);
        String m = message + '\n';
        Log.d("sending", m);
        byte[] msgBuffer = m.getBytes();
        try {
            os.write(msgBuffer);
        } catch (IOException e) {}
    }

    public void write_command(String message){
        Log.d("sending", message);
        byte[] msgBuffer = message.getBytes();
        try {
            os.write(msgBuffer);
        } catch (IOException e) {}
    }
}

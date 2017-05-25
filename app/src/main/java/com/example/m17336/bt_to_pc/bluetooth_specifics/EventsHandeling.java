package com.example.m17336.bt_to_pc.bluetooth_specifics;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.Snackbar;

import com.example.m17336.bt_to_pc.ControlsActivity;
import com.example.m17336.bt_to_pc.DeviceList_Activity;
import com.example.m17336.bt_to_pc.MyApplication;

public class EventsHandeling extends android.os.Handler {

    public static final int SOCKET_CONNECTED = 1;
    public static final int DATA_RECEIVED = 2;
    public static final int ERROR_CONNECT = 3, ERROR_COMMUNICATION = 4, ERROR_RFCOMM = 5;
    public static final int ERROR_OBTAIN_STREAMS = 6;

    private Snackbar snackbar;
    private Activity activity;

    public EventsHandeling(Snackbar snackbar, Activity activity){
        this.snackbar = snackbar;
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SOCKET_CONNECTED: {
                snackbar.setText("Socket connectat").show();

                CommunicationThread com = (CommunicationThread) msg.obj;

                MyApplication app = (MyApplication) activity.getApplicationContext();
                app.setCom((CommunicationThread)msg.obj);

                Intent i = new Intent(activity, ControlsActivity.class);
                activity.startActivity(i);
                this.activity.finish();
                break;
            }

            case DATA_RECEIVED: {
                break;
            }

            case ERROR_CONNECT: {
                snackbar.setText("Eroare de conectare").show();
                DeviceList_Activity da = (DeviceList_Activity) this.activity;
                da.discover();
                break;
            }

            case ERROR_COMMUNICATION: {
                snackbar.setText("Eroare la comunicare").show();
                break;
            }

            case ERROR_RFCOMM: {
                snackbar.setText("Eroare la deschiderea canalului").show();
                break;
            }

            case ERROR_OBTAIN_STREAMS: {
                snackbar.setText("Eroare in obtinrea strea-urilor").show();
                break;
            }
        }
    }
}

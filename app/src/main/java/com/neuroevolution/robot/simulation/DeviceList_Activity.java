package com.neuroevolution.robot.simulation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.neuroevolution.robot.simulation.bluetooth_specifics.ConnectThread;
import com.neuroevolution.robot.simulation.bluetooth_specifics.EventsHandeling;

public class DeviceList_Activity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<BluetoothDevice> devices;

    private BroadcastReceiver mReceiver;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start_bt();
        discover();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void discover() {
        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.device_list_item, R.id.text1);
        list_init();

        devices = new ArrayList<BluetoothDevice>();
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

                    devices.add(device);
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    private void start_bt() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            mBluetoothAdapter.startDiscovery();
        }
    }

    private void list_init() {
        ListView lv = (ListView) findViewById(R.id.bt_devices);
        lv.setAdapter(mArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = devices.get(position);

                Snackbar snackbar = Snackbar
                        .make(view, "Welcome", Snackbar.LENGTH_LONG);

                Toast.makeText(DeviceList_Activity.this, "Connecting, please wait...", Toast.LENGTH_LONG).show();

                mBluetoothAdapter.cancelDiscovery();

                ConnectThread con = new ConnectThread(device, new EventsHandeling(snackbar, DeviceList_Activity.this));
                con.start();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_ENABLE_BT )
            mBluetoothAdapter.startDiscovery();

    }

    public void startNextActivity(View view) {
        Intent originating_intent = getIntent();
        Intent i;
        switch (originating_intent.getIntExtra(MenuActivity.NEXT_ACTIVITY, 0)) {
            case MenuActivity.MANUAL_CONTROL_ACTIVITY:
                i = new Intent(this, ManualControlActivity.class);
                break;
            case MenuActivity.AUTO_CONTROL_ACTIVITY:
                i = new Intent(this, AutoControlActivity.class);
                break;
            default:
                i = new Intent(this, ManualControlActivity.class);
                break;
        }

        startActivity(i);
        finish();
    }
}

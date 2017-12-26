package com.eric.android.mqttdemo;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Eric on 2017/12/20.
 */

public class MQTTClient {
    private static final String TAG = "MQTTClient";
    private MqttClient client;
    private String clientId = "client";
    private String username = "Eric";
    private String password = "123456";
    private String [] topics = {"topic1", "topic2", "topic3"};
    private String mServerIP = "";
    private int [] qos = {0, 1, 2};
    private static MQTTClient mInstance = null;

    public static MQTTClient getInstance() {
        if (mInstance == null) {
            mInstance = new MQTTClient();
        }
        return mInstance;
    }

    public boolean connectMQTTServer () {
        try {
            client = new MqttClient(mServerIP, clientId, null);
            client.setCallback(new CallBack());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName(username);
            connectOptions.setPassword(password.toCharArray());
            connectOptions.setCleanSession(false);
            client.connect(connectOptions);
            client.subscribe(topics, qos);
        } catch (MqttException e) {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public static void release() {
        try {
            if (mInstance != null) {
                mInstance.close();
                mInstance = null;
            }
        }catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public boolean close() {
        try {
            client.disconnect();
        }catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean publishMsg(String topic, String msg) {
        MqttMessage message = new MqttMessage(msg.getBytes());
        try {
            client.publish(topic, message);
        }catch (MqttException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private class CallBack implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}

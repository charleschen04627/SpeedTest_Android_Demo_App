package com.foxconn.charles_speed_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

public class ConnectivityUtils
{
    public static WifiInfo getWifiInfo(@NonNull Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager != null) {

            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if ((networkInfo != null) && (networkInfo.isConnected())) {
                return (wifiManager.getConnectionInfo());
            }
        }

        return null;
    }

    public static String getAllWifiInfo(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.toString();
        }

        return "wifiInfo null";
    }

    public static String getSSID(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getSSID();
        }

        return "unknown ssid";
    }

    public static String getBSSID(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getBSSID();
        }

        return "null BSSID";
    }

    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getMacAddress();
        }

        return "null MacAddress";
    }

    public static SupplicantState getSupplicantState(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getSupplicantState();
        }

        return (SupplicantState.UNINITIALIZED);
    }

    public static int getRSSI(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getRssi();
        }

        return (-127);
    }

    public static int getLinkSpeed(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getLinkSpeed();
        }

        return (-1);
    }

    public static int getFrequency(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getFrequency();
        }

        return (-1);
    }

    public static int getNetworkID(Context context)
    {
        WifiInfo wifiInfo = getWifiInfo(context);
        if(wifiInfo != null)
        {
            return wifiInfo.getNetworkId();
        }

        return (-1);
    }
}




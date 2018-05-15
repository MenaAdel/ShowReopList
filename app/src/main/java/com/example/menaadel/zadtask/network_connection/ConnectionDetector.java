package com.example.menaadel.zadtask.network_connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MenaAdel on 10/13/2017.
 */

public class ConnectionDetector {
    private Context context;

    public ConnectionDetector(Context context) {
        this.context=context;
    }
    public boolean IsConnectingToInternet(){
        ConnectivityManager connectivity= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity!=null){
            NetworkInfo[]info=connectivity.getAllNetworkInfo();
            if(info!=null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;
        }
        return false;
    }
}

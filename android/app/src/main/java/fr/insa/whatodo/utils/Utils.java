package fr.insa.whatodo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Benjamin on 30/04/2015.
 */
public class Utils {

    public static boolean checkConnectivity(Context ctx)
    {
        if(ctx == null)
        {
            return false;
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isWifiConn || isMobileConn) {
            return true;
        } else {
           return false;
        }
    }
}

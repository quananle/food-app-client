package com.leanhquan.deliveryfoodver2.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.leanhquan.deliveryfoodver2.Model.User;

public class Common {
    public static User currentUser;

    public static final String DELETE =    "DELETE";
    public static final String USER_KEY =  "User";
    public static final String PASS_KEY =  "Password";

    public static String convertCodeToStatus(String code) {
            if(code.equals("0"))
                return"Đã đặt!";
            else if(code.equals("1"))
                return"Đang giao";
            else if(code.equals("2"))
                return"Đã hoàn thành";
            else {return "";}
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}

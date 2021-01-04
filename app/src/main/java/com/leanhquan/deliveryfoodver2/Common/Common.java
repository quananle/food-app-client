package com.leanhquan.deliveryfoodver2.Common;

import com.leanhquan.deliveryfoodver2.Model.User;

public class Common {
    public static User currentUser;

        public static String convertCodeToStatus(String code)
        {
            if(code.equals("0"))
                return"Đã đặt!";
            else if(code.equals("1"))
                return"Đang giao";
            else if(code.equals("2"))
                return"Đã hoàn thành";
            else {return "";}
        }
}

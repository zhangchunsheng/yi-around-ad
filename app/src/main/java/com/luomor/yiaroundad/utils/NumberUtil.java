package com.luomor.yiaroundad.utils;

/**
 * Created by Peter on 2018/06/16 14:35
 * 1097692918@qq.com
 */

public class NumberUtil {
    public static String converString(int num) {
        if (num < 100000) {
            return String.valueOf(num);
        }
        String unit = "万";
        double newNum = num / 10000.0;
        String numStr = String.format("%." + 1 + "f", newNum);
        return numStr + unit;
    }
}

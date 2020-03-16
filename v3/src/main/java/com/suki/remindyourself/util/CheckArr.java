package com.suki.remindyourself.util;

public class CheckArr {
    /**
     * 判断arr里面所有元素是否等于target
     * @param arr
     * @param targrt
     * @return
     */
    public static boolean checkArr(int[] arr, int targrt) {
        if (arr.length == 0 || arr == null) {
            return false;
        }
        for (int item : arr) {
            if (item != targrt) return false;
        }
        return true;
    }
}

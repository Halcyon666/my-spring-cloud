package com.whalefall.test;

import java.util.Collections;

/**
 * @Author: WhaleFall541
 * @Date: 2021/4/4 16:08
 */
public class Test {
    public static void main(String[] args) {
        int[] arr = {1,2,3,-10,100,255};
//        System.out.println(sort(arr));
        System.out.println("hh");
    }

    static String sort(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            int min_index = i;
            for (int j = i+1; j < arr.length-1; j++) {
                if (arr[j] < arr[i]) {
                    min_index = j;
                    sb.append("j" + min_index);
                }
            }
            int x = arr[i];
            arr[i] = arr[min_index];
            arr[min_index] = x;
        }
        return arr.toString()+"||" +sb.toString();
    }
}

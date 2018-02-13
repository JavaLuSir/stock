package com.luxinx.task;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Stock {
    public static boolean HASSENDED=false;

    public static Queue<Map<String,String>> STOCK_CODE_FOCUS = new ConcurrentLinkedDeque<>();

    public static Map<String,String> STOCK_CODE_ALL = new ConcurrentHashMap<>();

    public static Queue<String> EMAIL_QUEUE = new ConcurrentLinkedDeque<>();


}

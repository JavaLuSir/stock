package com.luxinx.task;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

public class Stock {

    public static List<Map<String,Object>> STOCK_CODE_FOCUS = new CopyOnWriteArrayList<>();

    public static Map<String,String> STOCK_CODE_ALL = new ConcurrentHashMap<>();

    public static Queue<String> EMAIL_QUEUE = new ConcurrentLinkedDeque<>();


}

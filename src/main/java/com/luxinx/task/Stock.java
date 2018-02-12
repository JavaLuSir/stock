package com.luxinx.task;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Stock {

    public static List<Map<String,Object>> STOCK_CODE_FOCUS = new CopyOnWriteArrayList<>();

    public static Map<String,String> STOCK_CODE_ALL = new ConcurrentHashMap<>();


}

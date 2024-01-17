package com.example.sender.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class getNowTime {
    public static String getNowTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

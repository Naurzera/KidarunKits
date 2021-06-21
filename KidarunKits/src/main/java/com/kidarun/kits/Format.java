package com.kidarun.kits;

import java.util.List;

public class Format {
    public static List<String> color(List<String> stringList){
        List<String> lore = stringList;
        for (int i = 0;i<lore.size();i++){
            lore.set(i,lore.get(i).replaceAll("&","§"));
        }
        return lore;
    }
    public static String color (String string){
        return string.replaceAll("&","§");
    }
}

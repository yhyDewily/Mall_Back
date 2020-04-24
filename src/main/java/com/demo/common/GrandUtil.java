package com.demo.common;

import java.util.ArrayList;
import java.util.List;

public enum GrandUtil {

    NIKE(1, "nike", "耐克"),
    ADIDAS(2, "adidas", "阿迪达斯"),
    HENGYUANXIANG(3, "恒源祥", "恒源祥"),
    PLAYBOY(4,"playboy", "花花公子"),
    UNIQLO(5, "uniqlo", "优衣库"),
    METERS(6,"meters", "美特斯邦威"),
    ZARA(7, "zara", "飒拉");

    private final Integer grandId;
    private final String grandName;
    private final String grandCName;

    GrandUtil(Integer grandId, String grandName, String grandCName) {
        this.grandId = grandId;
        this.grandName = grandName;
        this.grandCName = grandCName;
    }

    public Integer getGrandId() {
        return grandId;
    }

    public String getGrandName() {
        return grandName;
    }

    public String getGrandCName() {
        return grandCName;
    }

    public static List<String> getGrandList(){
        List<String> list = new ArrayList<>();
        list.add(NIKE.getGrandName());
        list.add(ADIDAS.getGrandName());
        list.add(HENGYUANXIANG.getGrandName());
        list.add(PLAYBOY.getGrandName());
        list.add(UNIQLO.getGrandName());
        list.add(METERS.getGrandName());
        list.add(ZARA.getGrandName());
        return list;
    }

    public static List<String> getGrandCList(){
        List<String> list = new ArrayList<>();
        list.add(NIKE.getGrandCName());
        list.add(ADIDAS.getGrandCName());
        list.add(HENGYUANXIANG.getGrandCName());
        list.add(PLAYBOY.getGrandCName());
        list.add(UNIQLO.getGrandCName());
        list.add(METERS.getGrandCName());
        list.add(ZARA.getGrandCName());
        return list;
    }
}

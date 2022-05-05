package com.example.mysprout.recycler;

import java.io.Serializable;

public class RecyclerItemHabit implements Serializable {
    //체크박스의 텍스트뷰에 들어갈 것은 title, 전기(0)/교통(1)/자원(2)로 분류한 것은 sort
    String name;
    float carbonSave;
    String tag;

    public RecyclerItemHabit(String n, float s, String t){
        this.name = n;
        this.carbonSave = s;
        this.tag = t;
    }

    public String getName(){
        return name;
    }

    public String getTag(){
        return tag;
    }

    public float getCarbon() {
        return carbonSave;
    }

    public void setTitle(String t){
        this.name = t;
    }
}

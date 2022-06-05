package com.example.mysprout.data;

import com.example.mysprout.recycler.RecyclerItemFood;

import java.io.Serializable;

public class FoodPassData implements Serializable {
    private RecyclerItemFood item;
    private int unit;
    private String time;

    public FoodPassData(RecyclerItemFood item, int unit, String time) {
        this.item = item;
        this.unit = unit;
        this.time = time;
    }

    public RecyclerItemFood getItem(){
        return item;
    }

    public int getUnit(){
        return unit;
    }

    public String getTime() {
        return time;
    }
}

package com.example.mysprout.data;

import com.example.mysprout.recycler.RecyclerItemFood;

import java.io.Serializable;

public class FoodPassData implements Serializable {
    private RecyclerItemFood item;
    private int unit;

    public FoodPassData(RecyclerItemFood item, int unit) {
        this.item = item;
        this.unit = unit;
    }

    public RecyclerItemFood getItem(){
        return item;
    }

    public int getUnit(){
        return unit;
    }
}

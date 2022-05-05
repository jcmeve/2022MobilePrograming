package com.example.mysprout.recycler;

import java.io.Serializable;

public class RecyclerItemFood implements Serializable {
    String foodName;
    float carbonEmiss;
    String tag;

    public RecyclerItemFood(String foodName, float carbon, String t){
        this.foodName = foodName;
        this.carbonEmiss = carbon;
        this.tag = t;
    }

    public String getName(){
        return foodName;
    }

    public float getCarbon(){
        return carbonEmiss;
    }

    public String getTag() {
        return tag;
    }

    public void setFoodName(String name){
        this.foodName = name;
    }
}

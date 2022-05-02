package com.example.mysprout.recycler;

public class RecyclerItemFood {
    String foodName;
    float carbonEmiss;

    public RecyclerItemFood(String foodName, float carbon){
        this.foodName = foodName;
        this.carbonEmiss = carbon;
    }

    public String getFoodName(){
        return foodName;
    }

    public float getCarbonEmiss(){
        return carbonEmiss;
    }

    public void setFoodName(String name){
        this.foodName = name;
    }
}

package com.example.mysprout.recycler;

public class RecyclerItemFood {
    String foodName;

    public RecyclerItemFood(String foodName){
        this.foodName = foodName;
    }

    public String getFoodName(){
        return foodName;
    }

    public void setFoodName(String name){
        this.foodName = name;
    }
}

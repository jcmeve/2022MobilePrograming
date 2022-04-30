package com.example.mysprout.recycler;

public class RecyclerItemHabit {
    //라디오 버튼의 텍스트뷰에 들어갈 것은 title, 전기(0)/교통(1)/자원(2)로 분류한 것은 sort
    String title;
    int sort;

    public RecyclerItemHabit(){

    }

    public RecyclerItemHabit(String t, int s){
        this.title = t;
        this.sort = s;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String t, int s){
        this.title = t;
        this.sort = s;
    }
}

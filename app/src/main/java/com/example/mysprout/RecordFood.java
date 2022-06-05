package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.FoodData;
import com.example.mysprout.data.FoodPassData;
import com.example.mysprout.fragment.BottomSheetDialogFoodRecord;
import com.example.mysprout.recycler.RecyclerCustomAdapterFood;
import com.example.mysprout.recycler.RecyclerItemFood;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RecordFood extends AppCompatActivity
                        implements RecyclerCustomAdapterFood.OnFoodItemListener,
                        DB.getFoodListCallBack, BottomSheetDialogFoodRecord.FoodRecordDataPassListener {
    RecyclerCustomAdapterFood adapter;
    ArrayList<RecyclerItemFood> foods;
    ArrayList<FoodPassData> selects;

    RecyclerItemFood tempItem;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_food);
        SearchView searchView = findViewById(R.id.searchView);

        foods = new ArrayList<>();
        selects = new ArrayList<>();

        CheckTodayRecord();

        setUpRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //아래 꺼 씀
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    void CheckTodayRecord(){
        DB.getInstance().GetUserFoodHistory(
                result -> {
                    Calendar curr = Calendar.getInstance();
                    for(int i = 0; i< result.length; i++) {//종류
                        for (int j = 0; j < result[i].food_history.getCount().size(); j++) {//각 기록
                            long miles = result[i].food_history.getDate().get(j).getSecondsSinceEpoch()*1000;
                            long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                            if(days == 0) {
                                Log.i("FOOD RECORD","이미 기록 있음");
                                //TODO: 부탁해요

                            }
                        }
                    }
                }
        );
    }

    void setUpRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_food);
        recyclerView.setHasFixedSize(true);

        if(foods.isEmpty()){
            getDBData();
        }

        adapter = new RecyclerCustomAdapterFood(this,
                foods, R.layout.recycler_itemview_food);

        adapter.setOnItemListner(new RecyclerCustomAdapterFood.OnFoodItemListener() {
            @Override
            public void onItemClicked(View view, int position, RecyclerItemFood item) {
                Toast.makeText(getApplicationContext(), "아이템 클릭", Toast.LENGTH_SHORT).show();
                tempItem = item;
                //Log.d("아이템 클릭", item.getName());
                //Log.d("아이템 클릭", tempItem.getName());
                howMuchEat(view, item.getName(), item.getCarbon()); //아이템에서 각각 음식 이름과 탄소배출량 가져올 수 있게
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void getDBData(){
        if(foods.isEmpty()){
            DB.getFoodListCallBack foodListCallBack = new DB.getFoodListCallBack() {
                @Override
                public void callback(FoodData[] foodData) {
                    for (FoodData foodDatum : foodData) {
                        if(foodDatum != null){
                            RecyclerItemFood item = new RecyclerItemFood(foodDatum.getName(),
                                    foodDatum.getCarbonPerUnit(), foodDatum.getId());
                            //Log.d("recordfood", item.getFoodName());
                            //Log.d("반복문", "아이템 들어가는 중");
                            foods.add(item);
                        }
                        else{
                            break;
                        }
                    }
                }
            };

            DB.getInstance().GetFoodList(foodListCallBack);

            //Wait for making List
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void howMuchEat(View view, String foodName, float carbonEmiss){
        BottomSheetDialogFoodRecord bottomFragment = BottomSheetDialogFoodRecord.newInstance(foodName, carbonEmiss);
        bottomFragment.setOnDataPassListener(new BottomSheetDialogFoodRecord.FoodRecordDataPassListener() {
            @Override
            public void onRecordDataPass(View view, String name, int unit, String time) {
                //아이템과 이름 대조, 같은 이름인 아이템 리스트에 저장
                if(tempItem != null && tempItem.getName().equals(name)){
                    selects.add(new FoodPassData(tempItem, unit, time));
                    //Log.d("리스트에 아이템 추가", tempItem.getName());
                    tempItem = null;
                }
            }
        });
        bottomFragment.show(getSupportFragmentManager(), "bottomSheetDialog");
    }

    public void onClickfN1(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Intent intent = new Intent(RecordFood.this, RecordComplete.class);
        intent.putExtra("tag", "Food"); //어느 액티비티에서 왔는지 알려주는 태그
        intent.putExtra("selectsList", selects);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(View view, int position, RecyclerItemFood item) {
    }

    @Override
    public void callback(FoodData[] foodData) {
    }

    @Override
    public void onRecordDataPass(View view, String name, int unit, String time) {
    }


    public void onClick_r_f_back(View v) {
        finish();
    }
}

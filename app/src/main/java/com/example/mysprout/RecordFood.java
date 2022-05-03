package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysprout.fragment.BottomSheetDialogFoodRecord;
import com.example.mysprout.recycler.RecyclerCustomAdapterFood;
import com.example.mysprout.recycler.RecyclerItemFood;

import java.util.ArrayList;

public class RecordFood extends AppCompatActivity implements RecyclerCustomAdapterFood.OnItemListener {

    Button bottomsheet;
    private RecyclerCustomAdapterFood adapter;
    private RecyclerCustomAdapterFood.OnItemListener listener;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_food);
        SearchView searchView = findViewById(R.id.searchView);

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

    private void setUpRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_food);
        recyclerView.setHasFixedSize(true);

        //임시 데이터 리스트, 나중에 DB에서 데이터 받는 메소드로 수정 필요
        ArrayList<RecyclerItemFood> foods = new ArrayList<>();
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("쌀밥", 500.0f));
        foods.add(new RecyclerItemFood("비빔밥", 500.0f));
        foods.add(new RecyclerItemFood("잡곡밥", 1100.0f));
        foods.add(new RecyclerItemFood("제육볶음", 1900.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));
        foods.add(new RecyclerItemFood("곰탕", 9736.0f));

        adapter = new RecyclerCustomAdapterFood(this,
                foods, R.layout.recycler_itemview_food);

        adapter.setOnItemListner(new RecyclerCustomAdapterFood.OnItemListener() {
            @Override
            public void onItemClicked(View view, int position, RecyclerItemFood item) {
                Toast.makeText(getApplicationContext(), "아이템 클릭", Toast.LENGTH_SHORT).show();
                howMuchEat(view, item.getFoodName(), item.getCarbonEmiss()); //아이템에서 각각 음식 이름과 탄소배출량 가져올 수 있게
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void howMuchEat(View view, String foodName, float carbonEmiss){
        BottomSheetDialogFoodRecord bottomFragment = BottomSheetDialogFoodRecord.newInstance(foodName, carbonEmiss);
        
        /*Bundle bundle = new Bundle(2);
        bundle.putString("foodname", foodName); //각 아이템의 foodname 들어감
        bundle.putFloat("carbonemiss", carbonEmiss); //각 아이템의 1인분 당 탄소 배출량

        bottomFragment.setArguments(bundle);*/
        bottomFragment.show(getSupportFragmentManager(), "bottomSheetDialog");
        //Objects.requireNonNull(bottomFragment.getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void onClickfN1(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), RecordComplete.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(View view, int position, RecyclerItemFood item) {

    }
}
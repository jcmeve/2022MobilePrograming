package com.example.mysprout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.List;

public class record_food_1 extends AppCompatActivity {

    Button bottomsheet;
    private RecyclerCustomAdapterFood adapter;
    private List<RecyclerItemFood> foods;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_food_1);
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

        //음식 이름 적힌 텍스트뷰 누르면 아래에서 다이얼로그 팝업(현재 임시 버튼으로 동작)
        bottomsheet = findViewById(R.id.tempbutton_bottomtest);
        bottomsheet.setOnClickListener(v->{
            BottomSheetDialogFoodRecord bottomSheetDialog = new BottomSheetDialogFoodRecord();
            bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheetDialog");
        });
    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_food);
        recyclerView.setHasFixedSize(true);

        //임시 데이터 리스트, 나중에 DB에서 데이터 받는 메소드로 수정 필요
        foods = new ArrayList<>();
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("쌀밥"));
        foods.add(new RecyclerItemFood("비빔밥"));
        foods.add(new RecyclerItemFood("잡곡밥"));
        foods.add(new RecyclerItemFood("제육볶음"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));
        foods.add(new RecyclerItemFood("곰탕"));

        adapter = new RecyclerCustomAdapterFood(this,
                foods, android.R.layout.simple_list_item_1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    //나중에 정리합니다...
    /*private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_foodrecord);

        Button add = dialog.findViewById(R.id.addbtn_food);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(record_food_1.this, "식단이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }*/

    public void onClickfN1(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), record_food_2.class);
        startActivity(intent);

    }
}

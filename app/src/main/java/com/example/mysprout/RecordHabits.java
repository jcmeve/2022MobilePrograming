package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysprout.databinding.RecordHabitsBinding;
import com.example.mysprout.recycler.RecyclerCustomAdapterHabit;
import com.example.mysprout.recycler.RecyclerItemHabit;

import java.util.ArrayList;

public class RecordHabits extends AppCompatActivity {
    RecordHabitsBinding record1Binding;
    RecyclerView recyclerView_habit;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.record_1); 뷰 바인딩 사용
        record1Binding = RecordHabitsBinding.inflate(getLayoutInflater());
        setContentView(record1Binding.getRoot());
        recyclerView_habit = record1Binding.recyclerviewHabit;

        //RecyclerView에 표시할 데이터(임시)
        ArrayList<RecyclerItemHabit> habits = new ArrayList<>();
        for(int i=0; i<15; i++){
            habits.add(new RecyclerItemHabit("Habit"+(i+1), 0));
        }

        //커스텀 어댑터 생성
        RecyclerCustomAdapterHabit adapterHabit = new RecyclerCustomAdapterHabit(this,
                                                            habits, R.layout.recycler_itemview_habit);
        //RecyclerView 표시 결정, linear(vertical)로 해놓음
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_habit.setLayoutManager(layoutManager);

        recyclerView_habit.setAdapter(adapterHabit);

//        ImageView iv12 = (ImageView) findViewById(R.id.imageView12);
//        ImageView iv13 = (ImageView) findViewById(R.id.imageView13);
//
//        iv12.setAdjustViewBounds(true);
//        iv12.setMaxWidth(400);
//        iv12.setMaxHeight(400);
//        iv13.setAdjustViewBounds(true);
//        iv13.setMaxWidth(400);
//        iv13.setMaxHeight(400);
//
//        iv12.setImageDrawable(getResources().getDrawable(R.drawable.page7));
//        iv13.setImageDrawable(getResources().getDrawable(R.drawable.page8));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickN1(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), RecordComplete.class);
        startActivity(intent);

    }
}

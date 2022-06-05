package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.ActionData;
import com.example.mysprout.databinding.RecordHabitsBinding;
import com.example.mysprout.recycler.RecyclerCustomAdapterHabit;
import com.example.mysprout.recycler.RecyclerItemHabit;

import java.util.ArrayList;

public class RecordHabits extends AppCompatActivity
        implements DB.getActionListCallBack, RecyclerCustomAdapterHabit.OnActionItemListener {
    RecordHabitsBinding record1Binding;
    RecyclerView recyclerView_habit;
    ArrayList<RecyclerItemHabit> habits;

    ArrayList<RecyclerItemHabit> selects;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        habits = new ArrayList<>();
        selects = new ArrayList<>();
        //setContentView(R.layout.record_1); 뷰 바인딩 사용

        setUpRecyclerView();

    }

    private void setUpRecyclerView(){
        record1Binding = RecordHabitsBinding.inflate(getLayoutInflater());
        setContentView(record1Binding.getRoot());
        recyclerView_habit = record1Binding.recyclerviewHabit;

        getDBData();

        //커스텀 어댑터 생성
        RecyclerCustomAdapterHabit adapterHabit = new RecyclerCustomAdapterHabit(this,
                habits, R.layout.recycler_itemview_habit);
        //RecyclerView 표시 결정, linear(vertical)로 해놓음
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_habit.setLayoutManager(layoutManager);

        adapterHabit.setOnActionItemListener(new RecyclerCustomAdapterHabit.OnActionItemListener() {
            @Override
            public void onItemChecked(String tag, RecyclerItemHabit item) {
                switch (tag){
                    case "TRUE":
                        selects.add(item);
                        //Log.d("item", selects.get(0).getName());
                        break;
                    case "FALSE":
                        selects.remove(item);
                        //Log.d("item", String.valueOf(selects));
                        break;
                }
            }
        });

        recyclerView_habit.setAdapter(adapterHabit);
    }

    public void getDBData(){
        if(habits.isEmpty()){
            DB.getActionListCallBack actionListCallBack = new DB.getActionListCallBack() {
                @Override
                public void callback(ActionData[] actionData) {
                    for (ActionData actionDatum : actionData) {
                        if(actionDatum != null){
                            RecyclerItemHabit item = new RecyclerItemHabit(actionDatum.getName(),
                                    actionDatum.getSaveCarbon(), actionDatum.getId());
                            habits.add(item);
                        }
                        else{
                            break;
                        }
                    }
                }
            };

            DB.getInstance().GetActionList(actionListCallBack);

            try {
                Thread.sleep(2400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickN1(View v) {
        Intent intent = new Intent(this, RecordComplete.class);
        intent.putExtra("tag", "Habits");
        intent.putExtra("selectList", selects);
        startActivity(intent);

    }

    @Override
    public void callback(ActionData[] actionData) {

    }

    public void onClick_h_back(View v) {
        //  Intent intent = new Intent(getApplicationContext(), UserInformation.class);
        //  startActivity(intent);
        //onBackPressed();
        finish();
    }


    @Override
    public void onItemChecked(String tag, RecyclerItemHabit item) {

    }
}

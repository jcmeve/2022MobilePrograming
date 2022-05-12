package com.example.mysprout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amplifyframework.datastore.generated.model.User;
import com.example.mysprout.data.FoodPassData;
import com.example.mysprout.databinding.RecordCompleteBinding;
import com.example.mysprout.recycler.RecyclerCustomAdapterResult;
import com.example.mysprout.recycler.RecyclerItemHabit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RecordComplete extends AppCompatActivity implements RecyclerCustomAdapterResult.OnResultItemListener {
    private float total;
    private String tag;
    ArrayList selects;

    int givenSize;

    RecordCompleteBinding recordCompleteBinding;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        givenSize = size.x;

        recordCompleteBinding = RecordCompleteBinding.inflate(getLayoutInflater()); //뷰 바인딩 사용
        setContentView(recordCompleteBinding.getRoot());

        getDate();

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        if(tag.equals("Food")){
            selects = new ArrayList<FoodPassData>();
            selects = intent.getParcelableArrayListExtra("selectList");

            calTotal(selects);
            setTextOfRecordFoods();
        }else if(tag.equals("Habits")){
            //Habits
            selects = new ArrayList<RecyclerItemHabit>();
            selects = intent.getParcelableArrayListExtra("selectList");
            setTextOfRecordHabits();
        }

        setRecyclerView();

    }

    static User getUserResult;
    @SuppressLint("SetTextI18n")
    private void getDate(){
        Calendar now = Calendar.getInstance();
        Date currentTime = now.getTime();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm", Locale.getDefault());


        DB.getInstance().GetUserInfo((result)->{
            getUserResult = result;
            runOnUiThread(() -> {
                Calendar curr = Calendar.getInstance();
                long days = TimeUnit.DAYS.convert(curr.getTime().getTime()/1000 - MainSprout.getUserResult.getMcreatedAt().getSecondsSinceEpoch(), TimeUnit.SECONDS);
                recordCompleteBinding.textviewCompleteToday.setText("Day "+days);

            });
        });


        recordCompleteBinding.textviewCompleteToday.setText("Day 0"); //여기 며칠째인지 들어가야 함 -아마 DB 연동-
        recordCompleteBinding.textviewCompleteDate.setText(formatDate.format(currentTime));

        int isAMorPM = now.get(Calendar.AM_PM);

        switch (isAMorPM){
            case Calendar.AM:
                recordCompleteBinding.textviewCompleteTime.setText("오전 "+formatTime.format(currentTime));
                break;

            case Calendar.PM:
                recordCompleteBinding.textviewCompleteTime.setText("오후 "+formatTime.format(currentTime));
                break;
        }
    }

    private void calTotal(ArrayList<FoodPassData> data){
        float beforeTotal = 0.f;

        for(FoodPassData datum : data){
            beforeTotal += datum.getItem().getCarbon() * datum.getUnit();
        }

        total = beforeTotal;
    }

    @SuppressLint("SetTextI18n")
    private void setTextOfRecordHabits(){
        recordCompleteBinding.pagenameRecordResult.setText("습관 기록");
        recordCompleteBinding.textviewCompleteTotal.setText("절약한 탄소 배출량: " + total + "CO2eg");
    }

    @SuppressLint("SetTextI18n")
    private void setTextOfRecordFoods(){
        recordCompleteBinding.pagenameRecordResult.setText("식단 기록");
        recordCompleteBinding.textviewCompleteTotal.setText("총 탄소 배출량: " + total + "CO2eg");
    }

    private void setRecyclerView(){
        RecyclerCustomAdapterResult adapter
                = new RecyclerCustomAdapterResult(this, tag, R.layout.recycler_itemview_result, givenSize);

        if(tag.equals("Food")){
            adapter.setFoodDatas(selects);
        }else if(tag.equals("Habits")){
            adapter.setHabitDatas(selects);
        }

        //삭제할 때마다 텍스트 업데이트
        adapter.setOnResultItemListener(new RecyclerCustomAdapterResult.OnResultItemListener() {
            @Override
            public void onItemButtonClicked(View view, int position, FoodPassData item) {
                selects.remove(item);
                calTotal(selects);
                setTextOfRecordFoods();
            }

            @Override
            public void onItemButtonClicked(View view, int position, RecyclerItemHabit item) {
                //Habits
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recordCompleteBinding.recyclerviewResult.setHasFixedSize(true);
        recordCompleteBinding.recyclerviewResult.setLayoutManager(layoutManager);
        recordCompleteBinding.recyclerviewResult.setAdapter(adapter);
    }

    public float calSavings(){
        //DB에서 meatlevel 받아오기, 일단 LowMeat로 받았다 생각하고 진행
        float save = 6061 - total;
        if(save < 0){
            save = 0;
        }
        return save;
    }

    public void onClickN2(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Log.d("넘겨줄 리스트 컴플릿", String.valueOf(selects));
        Intent intent = new Intent(getApplicationContext(), GrowSprout.class);

        if(tag.equals("Food")){
            for(int i = 0; i < selects.size();i++){
                FoodPassData foodPassData = (FoodPassData)selects.get(i);
                DB.getInstance().AddFoodData(foodPassData.getItem().getName(),foodPassData.getUnit());
            }
            intent.putExtra("tag", "Food");
            float save = calSavings();
            intent.putExtra("save", save);
            //아침, 점심, 저녁 중에 무엇인지 나중에 추가
        }else if(tag.equals("Habits")){
            intent.putExtra("tag", "Habits");
            intent.putExtra("save", total);
            intent.putExtra("num", selects.size());
        }else{

        }

        startActivity(intent);
    }

    @Override
    public void onItemButtonClicked(View view, int position, FoodPassData item) {

    }

    @Override
    public void onItemButtonClicked(View view, int position, RecyclerItemHabit itemHabit) {

    }


    public void onClick_r_c_back(View v) {
        finish();
    }

    //뒤로가기 2번 클릭 시 종료
    private long lastTimeBackPressed; //뒤로가기 버튼이 클릭된 시간
    @Override
    public void onBackPressed()
    {
        //2초 이내에 뒤로가기 버튼을 재 클릭 시 앱 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000)
        {
            finish();
            return;
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

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

public class RecordComplete extends AppCompatActivity {
    private int total;
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
            selects = intent.getParcelableArrayListExtra("selectsList");

            calTotalFood(selects);
            setTextOfRecordFoods();
        }else if(tag.equals("Habits")){
            recordCompleteBinding.recordCompleteBreakfast.setVisibility(View.GONE);
            recordCompleteBinding.recordCompleteLunch.setVisibility(View.GONE);
            recordCompleteBinding.recordCompleteDinner.setVisibility(View.GONE);
            recordCompleteBinding.recyclerviewResult2.setVisibility(View.GONE);
            recordCompleteBinding.recyclerviewResult3.setVisibility(View.GONE);

            selects = new ArrayList<RecyclerItemHabit>();
            selects = intent.getParcelableArrayListExtra("selectList");

            calTotalHabit(selects);
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
                long days = TimeUnit.DAYS.convert(curr.getTime().getTime()/1000 - RecordComplete.getUserResult.getMcreatedAt().getSecondsSinceEpoch(), TimeUnit.SECONDS);
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
    int b = 0;
    int l = 0;
    int d = 0;

    private void calTotalFood(ArrayList<FoodPassData> data){
        int beforeTotal = 0;
        b = 0;
        l = 0;
        d = 0;
        for(FoodPassData datum : data){
            switch (datum.getTime()) {
                case "아침":
                    b += datum.getItem().getCarbon() * datum.getUnit();
                    break;
                case "점심":
                    l += datum.getItem().getCarbon() * datum.getUnit();
                    break;
                case "저녁":
                    d += datum.getItem().getCarbon() * datum.getUnit();
                    break;
                default:
                    Log.e("ERROR", "ERROR");
                    break;
            }
            beforeTotal += datum.getItem().getCarbon() * datum.getUnit();
        }

        total = beforeTotal;
    }

    private void calTotalHabit(ArrayList<RecyclerItemHabit> data){
        int beforeTotal = 0;

        for(RecyclerItemHabit datum : data){
            beforeTotal += datum.getCarbon();
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

        ResultItemListener listener = new ResultItemListener();

        if(tag.equals("Food")){
            ArrayList<FoodPassData> selectsBreakfast = new ArrayList<>();
            ArrayList<FoodPassData> selectsLunch = new ArrayList<>();
            ArrayList<FoodPassData> selectsDinner = new ArrayList<>();

            RecyclerCustomAdapterResult adapterB
                    = new RecyclerCustomAdapterResult(this, tag, R.layout.recycler_itemview_result, givenSize);
            RecyclerCustomAdapterResult adapterL
                    = new RecyclerCustomAdapterResult(this, tag, R.layout.recycler_itemview_result, givenSize);
            RecyclerCustomAdapterResult adapterD
                    = new RecyclerCustomAdapterResult(this, tag, R.layout.recycler_itemview_result, givenSize);

            for(FoodPassData data : (ArrayList<FoodPassData>) selects){
                switch (data.getTime()){
                    case "아침":
                        selectsBreakfast.add(data);
                        break;
                    case "점심":
                        selectsLunch.add(data);
                        break;
                    case "저녁":
                        selectsDinner.add(data);
                        break;
                }
            }

            adapterB.setFoodDatas(selectsBreakfast);
            adapterL.setFoodDatas(selectsLunch);
            adapterD.setFoodDatas(selectsDinner);

            adapterB.setOnResultItemListener(listener);
            adapterL.setOnResultItemListener(listener);
            adapterD.setOnResultItemListener(listener);

            LinearLayoutManager layoutManagerB = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recordCompleteBinding.recyclerviewResult.setHasFixedSize(true);
            recordCompleteBinding.recyclerviewResult.setLayoutManager(layoutManagerB);
            recordCompleteBinding.recyclerviewResult.setAdapter(adapterB);

            LinearLayoutManager layoutManagerL = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recordCompleteBinding.recyclerviewResult2.setHasFixedSize(true);
            recordCompleteBinding.recyclerviewResult2.setLayoutManager(layoutManagerL);
            recordCompleteBinding.recyclerviewResult2.setAdapter(adapterL);

            LinearLayoutManager layoutManagerD = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recordCompleteBinding.recyclerviewResult3.setHasFixedSize(true);
            recordCompleteBinding.recyclerviewResult3.setLayoutManager(layoutManagerD);
            recordCompleteBinding.recyclerviewResult3.setAdapter(adapterD);

        }else if(tag.equals("Habits")){
            RecyclerCustomAdapterResult adapter
                    = new RecyclerCustomAdapterResult(this, tag, R.layout.recycler_itemview_result, givenSize);
            adapter.setHabitDatas(selects);
            adapter.setOnResultItemListener(listener);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recordCompleteBinding.recyclerviewResult.setHasFixedSize(true);
            recordCompleteBinding.recyclerviewResult.setLayoutManager(layoutManager);
            recordCompleteBinding.recyclerviewResult.setAdapter(adapter);
        }

    }



    public void onClickN2(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Log.d("넘겨줄 리스트 컴플릿", String.valueOf(selects));
        Intent intent = new Intent(this, GrowSprout.class);

        if(tag.equals("Food")){
            for(int i = 0; i < selects.size();i++){
                FoodPassData foodPassData = (FoodPassData)selects.get(i);
                if(foodPassData.getTime().equals("아침")){
                    DB.getInstance().AddFoodData(foodPassData.getItem().getName(),foodPassData.getUnit(), DB.TIME.BREAKFAST);
                }else if(foodPassData.getTime().equals("점심")){
                    DB.getInstance().AddFoodData(foodPassData.getItem().getName(),foodPassData.getUnit(), DB.TIME.LUNCH);
                }else  if(foodPassData.getTime().equals("저녁")){
                    DB.getInstance().AddFoodData(foodPassData.getItem().getName(),foodPassData.getUnit(), DB.TIME.DINNER);
                }else{
                    Log.e("ERROR","ERROR");
                }

            }
            intent.putExtra("tag", "Food");
            DB.getInstance().GetUserInfo(result->{
                int save = 0;
                if(b != 0){
                    if(result.getMeatCarbon() - b >0)
                        save += result.getMeatCarbon() - b;
                }
                if(l != 0){
                    if(result.getMeatCarbon() - l >0)
                        save += result.getMeatCarbon() - l;
                }
                if(d != 0){
                    if(result.getMeatCarbon() - d >0)
                        save += result.getMeatCarbon() - d;
                }

                intent.putExtra("save", save);
                Log.i(result.getMeatCarbon().toString(),String.valueOf(total));
                startActivity(intent);

            });
            //아침, 점심, 저녁 중에 무엇인지 나중에 추가
        }else if(tag.equals("Habits")){
            intent.putExtra("tag", "Habits");
            intent.putExtra("save", total); //습관 기록은 total 자체가 절약량
            intent.putExtra("num", selects.size());
            startActivity(intent);

        }else{
            startActivity(intent);
        }
    }

    class ResultItemListener implements RecyclerCustomAdapterResult.OnResultItemListener{

        @Override
        public void onItemButtonClicked(View view, int position, FoodPassData itemFood) {
            selects.remove(itemFood);
            calTotalFood(selects);
            setTextOfRecordFoods();
        }

        @Override
        public void onItemButtonClicked(View view, int position, RecyclerItemHabit itemHabit) {
            selects.remove(itemHabit);
            calTotalHabit(selects);
            setTextOfRecordHabits();
        }
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
            //홈으로 가야 한다
            Toast.makeText(this, "기록이 취소됩니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);

            return;
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 기록이 취소됩니다.", Toast.LENGTH_SHORT).show();
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

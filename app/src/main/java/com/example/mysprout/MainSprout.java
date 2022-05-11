package com.example.mysprout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.datastore.generated.model.User;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainSprout extends AppCompatActivity {
    static User getUserResult = null;

    @SuppressLint("SetTextI18n")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sprout);




        //새싹 애니메이션 누르면 상세 화면(mysprout)으로 이동
        LottieAnimationView sproutAnim = findViewById(R.id.sprout_main);
        sproutAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSprout.this, "상세 화면으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainSprout.this, MySprout.class);
                startActivity(intent);
            }
        });

        Button menuButton = findViewById(R.id.menubtn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSprout.this, "메뉴 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainSprout.this, Menu.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DB.getInstance().GetUserInfo((result)->{
            getUserResult = result;
            runOnUiThread(() -> {
                TextView days_txt = findViewById(R.id.todayText);
                Calendar curr = Calendar.getInstance();
                long days = TimeUnit.DAYS.convert(curr.getTime().getTime()/1000 - MainSprout.getUserResult.getMcreatedAt().getSecondsSinceEpoch(), TimeUnit.SECONDS);
                days_txt.setText("Day "+days);

                TextView level_txt = findViewById(R.id.level_main);
                TextView sprout_name_txt = findViewById(R.id.sproutName_main);
                level_txt.setText("Lv. "+(int)(MainSprout.getUserResult.getSproutExp()/ 1000));
                sprout_name_txt.setText(MainSprout.getUserResult.getSproutName());

            });
        });

        CalculateTodayCarbon();
    }

    private void CalculateTodayCarbon(){
        _Action();
        _Food();
        _Transportation();
    }

    private void _Action(){
        DB.getInstance().GetUserActionHistory(
                result->{
                    Calendar curr = Calendar.getInstance();

                    int carbon = 0;
                    for(int i = 0; i< result.length; i++){//종류
                        for(int j = 0; j < result[i].action_history.getCount().size();j++){//각 기록
                            long miles = result[i].action_history.getDate().get(j).getSecondsSinceEpoch()*1000;
                            long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                            if(days == 0) {
                                carbon += result[i].action_history.getCount().get(j) * result[i].data.getSaveCarbon();
                            }
                        }
                    }
                    SetTodayCarbonTxt(carbon);
                }
        );
    }

    private void _Food(){
        DB.getInstance().GetUserFoodHistory(
                result->{
                    Calendar curr = Calendar.getInstance();

                    int carbon = 0;
                    for(int i = 0; i< result.length; i++){//종류
                        for(int j = 0; j < result[i].food_history.getCount().size();j++){//각 기록
                            long miles = result[i].food_history.getDate().get(j).getSecondsSinceEpoch()*1000;
                            long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                            if(days == 0) {
                                carbon += result[i].food_history.getCount().get(j) * result[i].data.getCarbonPerUnit();
                            }
                        }
                    }
                    SetTodayCarbonTxt(carbon);
                }
        );
    }

    private void _Transportation(){
        DB.getInstance().GetUserTransportationHistory(
                result->{
                    Calendar curr = Calendar.getInstance();

                    int carbon = 0;
                    for(int i = 0; i< result.length; i++){//종류
                        for(int j = 0; j < result[i].transportation_history.getCount().size();j++){//각 기록
                            long miles = result[i].transportation_history.getDate().get(j).getSecondsSinceEpoch()*1000;
                            long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                            if(days == 0) {
                                carbon += result[i].transportation_history.getCount().get(j) * result[i].data.getCarbonPerUnit();
                            }
                        }
                    }
                    SetTodayCarbonTxt(carbon);
                }
        );
    }

    static int total = 0;
    @SuppressLint("SetTextI18n")
    public synchronized void SetTodayCarbonTxt(int value){
        total += value;
        Log.i("tatasta",total+"");
        runOnUiThread(() -> {
            TextView txt = findViewById(R.id.estimateText);
            txt.setText(MainSprout.total+"");
        });

    }


    //기록 버튼 눌렀을 때 습관 기록과 식단 기록 중 어디로 넘어갈지 팝업 메뉴로 선택
    public void recordButton(View button){
        PopupMenu chooseRecord = new PopupMenu(this, button);
        chooseRecord.getMenuInflater().inflate(R.menu.record_popup, chooseRecord.getMenu());
        chooseRecord.setOnMenuItemClickListener(
                item -> {
                    if(item.getItemId() == R.id.record_habit){
                        Toast.makeText(MainSprout.this, "습관 기록으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainSprout.this, RecordHabits.class);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.record_food){
                        Toast.makeText(MainSprout.this, "식단 기록으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainSprout.this, RecordFood.class);
                        startActivity(intent);
                    }
                    return true;
                });
        chooseRecord.show();
    }

}
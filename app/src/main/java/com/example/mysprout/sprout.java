package com.example.mysprout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.User;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class sprout extends AppCompatActivity {
    static User getUserResult = null;

    @SuppressLint("SetTextI18n")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprout);


        DB.getInstance().GetUserInfo((result)->{
            getUserResult = result;
            runOnUiThread(() -> {
                TextView days_txt = findViewById(R.id.todayText);
                Calendar curr = Calendar.getInstance();
                long days = TimeUnit.DAYS.convert(curr.getTime().getTime()/1000 - sprout.getUserResult.getMcreatedAt().getSecondsSinceEpoch(), TimeUnit.SECONDS);
                days_txt.setText("Day "+days);
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
            txt.setText(sprout.total+"");
        });

    }


    //기록 버튼 눌렀을 때 습관 기록과 식단 기록 중 어디로 넘어갈지 팝업 메뉴로 선택
    public void recordButton(View button){
        PopupMenu chooseRecord = new PopupMenu(this, button);
        chooseRecord.getMenuInflater().inflate(R.menu.record_popup, chooseRecord.getMenu());
        chooseRecord.setOnMenuItemClickListener(
                item -> {
                    if(item.getItemId() == R.id.record_habit){
                        Toast.makeText(sprout.this, "습관 기록으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sprout.this, RecordHabits.class);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.record_food){
                        Toast.makeText(sprout.this, "식단 기록으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sprout.this, RecordFood.class);
                        startActivity(intent);
                    }
                    return true;
                });
        chooseRecord.show();
    }

    //팝업메뉴
    public void onClick4(View button4) {
        PopupMenu popup = new PopupMenu(this, button4);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        popup.setOnMenuItemClickListener(
                item -> {
                    if (item.getItemId() == R.id.action_menu1) {
                        Toast.makeText(sprout.this, "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), nickname.class);
                        startActivity(intent);
                    } else if (item.getItemId() == R.id.action_menu2) {
                        Toast.makeText(sprout.this, "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), mysprout.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(sprout.this, "메뉴 3 클릭", Toast.LENGTH_SHORT).show();

                    }
                    return false;
                });
        popup.show();
    }



    public void onClick3(View v) {
        Intent intent = new Intent(getApplicationContext(), sprout2.class);
        startActivity(intent);
    }



}

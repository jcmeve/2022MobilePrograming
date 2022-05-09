package com.example.mysprout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.User;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class mysprout extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysprout);

        SetSprout();

        SetAction();

        SetFood();

        findViewById(R.id.mysprout_today_walk);
        findViewById(R.id.mysprout_today_walk_carbon);

        Button btn = findViewById(R.id.backBtn_mysprout);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), sprout.class);
            startActivity(intent);
        });

    }

    static User user_result = null;
    @SuppressLint("SetTextI18n")
    private void SetSprout(){

        DB.getInstance().GetUserInfo(
                result->{
                    mysprout.user_result = result;
                    runOnUiThread(() -> {
                        TextView name = findViewById(R.id.mysprout_sprout_name);
                        TextView level = findViewById(R.id.mysprout_sprout_level);
                        name.setText(mysprout.user_result.getSproutName());
                        level.setText(getResources().getString(R.string.mysprout_level_str) + " " + mysprout.user_result.getSproutExp());
                    });
                }
        );
    }

    static DB.Action_Pair[] action_result = null;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void SetAction(){
        DB.getInstance().GetUserActionHistory(
            result->{
                action_result = result;
                runOnUiThread(() -> {

                    TextView action = findViewById(R.id.mysprout_today_action);
                    TextView action_carbon = findViewById(R.id.mysprout_today_action_carbon);
                    Calendar curr = Calendar.getInstance();

                    int action_val = 0;
                    int action_carbon_val = 0;
                    for(int i = 0; i< mysprout.action_result.length; i++){//종류
                        for(int j = 0; j < mysprout.action_result[i].action_history.getCount().size();j++){//각 기록
                            long miles = mysprout.action_result[i].action_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                            long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                            if(days == 0) {
                                action_val += 1;
                                action_carbon_val += mysprout.action_result[i].action_history.getCount().get(j) * mysprout.action_result[i].data.getSaveCarbon();
                            }
                        }
                    }
                    action.setText(action_val+ "가지");
                    action_carbon.setText(String.format("%.1f",(float) action_carbon_val) + "g");

                });
            }
        );
    }

    static DB.Food_Pair[] Food_result = null;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void SetFood(){
        DB.getInstance().GetUserFoodHistory(
                result->{
                    Food_result = result;
                    runOnUiThread(() -> {

                        TextView Food = findViewById(R.id.mysprout_today_food);
                        TextView Food_carbon = findViewById(R.id.mysprout_today_food_carbon);
                        Calendar curr = Calendar.getInstance();

                        int Food_val = 0;
                        int Food_carbon_val = 0;
                        for(int i = 0; i< mysprout.Food_result.length; i++){//종류
                            for(int j = 0; j < mysprout.Food_result[i].food_history.getCount().size();j++){//각 기록
                                long miles = mysprout.Food_result[i].food_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0) {
                                    Food_val += 1;
                                    Food_carbon_val += mysprout.Food_result[i].food_history.getCount().get(j) * mysprout.Food_result[i].data.getCarbonPerUnit();
                                }
                            }
                        }
                        Food.setText(Food_val+ "회");
                        Food_carbon.setText(String.format("%.1f",(float) Food_carbon_val) + "g");

                    });
                }
        );
    }
}

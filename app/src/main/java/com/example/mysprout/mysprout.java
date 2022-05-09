package com.example.mysprout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.User;

import java.util.Calendar;

public class mysprout extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysprout);

        SetSprout();

        SetAction();

        findViewById(R.id.mysprout_today_walk);
        findViewById(R.id.mysprout_today_walk_carbon);




        findViewById(R.id.mysprout_today_food);
        findViewById(R.id.mysprout_today_food_carbon);


    }

    static User user_result = null;
    @SuppressLint("SetTextI18n")
    private void SetSprout(){

        DB.getInstance().GetUserInfo(
                result->{
                    mysprout.user_result = result;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView name = findViewById(R.id.mysprout_sprout_name);
                            TextView level = findViewById(R.id.mysprout_sprout_level);
                            name.setText(mysprout.user_result.getSproutName());
                            level.setText(getResources().getString(R.string.mysprout_level_str) + " " + result.getSproutExp());
                        }
                    });
                }
        );
    }

    static DB.Action_Pair[] action_result = null;
    private void SetAction(){
        DB.getInstance().GetUserActionHistory(
            result->{
                action_result = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView action = findViewById(R.id.mysprout_today_action);
                        TextView action_carbon = findViewById(R.id.mysprout_today_action_carbon);
                        Calendar curr = Calendar.getInstance();

                        int action_val = 0;
                        int action_carbon_val = 0;
                        for(int i = 0; i< mysprout.action_result.length; i++){//종류
                            for(int j = 0; j < mysprout.action_result[i].action_history.getCount().size();j++){//각 기록
                                long miles = mysprout.action_result[i].action_history.getDate().get(j).getSecondsSinceEpoch();

                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(miles);
                                if(c.get(Calendar.DATE) == curr.get(Calendar.DATE) && c.get(Calendar.MONTH) == curr.get(Calendar.MONTH) && c.get(Calendar.YEAR) == curr.get(Calendar.YEAR) ){
                                    action_val += 1;
                                    action_carbon_val += mysprout.action_result[i].action_history.getCount().get(j) * mysprout.action_result[i].data.getSaveCarbon();
                                }
                            }
                        }
                        action.setText(action_val+ "가지");
                        action_carbon.setText(String.format("%.1f",(float) action_carbon_val) + "g");

                    }
                });
            }
        );
    }
}

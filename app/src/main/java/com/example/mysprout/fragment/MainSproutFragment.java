package com.example.mysprout.fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amplifyframework.datastore.generated.model.User;
import com.example.mysprout.DB;
import com.example.mysprout.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainSproutFragment extends Fragment {
    static User getUserResult = null;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_sprout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        DB.getInstance().GetUserInfo((result)->{
            getUserResult = result;
            runOnUiThread(() -> {
                TextView days_txt = view.findViewById(R.id.todayText);
                Calendar curr = Calendar.getInstance();
                long days = TimeUnit.DAYS.convert(curr.getTime().getTime()/1000 - MainSproutFragment.getUserResult.getMcreatedAt().getSecondsSinceEpoch(), TimeUnit.SECONDS);
                days_txt.setText("Day "+days);

                TextView level_txt = view.findViewById(R.id.level_main);
                TextView sprout_name_txt = view.findViewById(R.id.sproutName_main);
                level_txt.setText("Lv. "+DB.ExpToLevel (MainSproutFragment.getUserResult.getCarbonSave()));
                sprout_name_txt.setText(MainSproutFragment.getUserResult.getSproutName());

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
            TextView txt = view.findViewById(R.id.estimateText);
            txt.setText(MainSproutFragment.total+"");
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        total = 0;
    }
}

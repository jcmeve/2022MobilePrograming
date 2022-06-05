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

public class MyPageFragment extends Fragment {
    View view;
    //FragmentMyPageBinding myPageBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        //myPageBinding = FragmentMyPageBinding.inflate(inflater);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        SetSprout();

        SetStep();

        SetAction();

        SetFood();

        view.findViewById(R.id.mysprout_today_walk);
        view.findViewById(R.id.mysprout_today_walk_carbon);
    }

    static User user_result = null;
    @SuppressLint("SetTextI18n")
    private void SetSprout(){

        DB.getInstance().GetUserInfo(
                result->{
                    MyPageFragment.user_result = result;
                    runOnUiThread(() -> {
                        TextView name = view.findViewById(R.id.mysprout_sprout_name);
                        TextView level = view.findViewById(R.id.mysprout_sprout_level);
                        name.setText(MyPageFragment.user_result.getSproutName());
                        level.setText(getResources().getString(R.string.mysprout_level_str) + " " + DB.ExpToLevel(MyPageFragment.user_result.getCarbonSave()));
                    });
                }
        );

    }

    static DB.Transportation_Pair[] transportation_result = null;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void SetStep(){
        Log.i("HEHE","HAHA");
        DB.getInstance().GetUserTransportationHistory(
                result -> {
                    Log.i("HEHE","HAHA");
                    transportation_result = result;
                    runOnUiThread(()->{

                        TextView steps_txt = view.findViewById(R.id.mysprout_today_walk);
                        TextView steps_Carbon_txt = view.findViewById(R.id.mysprout_today_walk_carbon);
                        TextView total_walk_save = view.findViewById(R.id.my_page_analysis_text_walk);
                        Calendar curr = Calendar.getInstance();
                        int step_cnt = 0;
                        int step_save = 0;
                        int total_carbon = 0;
                        for(int i = 0; i < MyPageFragment.transportation_result.length; i++){
                            for(int j = 0; j < MyPageFragment.transportation_result[i].transportation_history.getCount().size(); j++){
                                long miles = MyPageFragment.transportation_result[i].transportation_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0){
                                    if(MyPageFragment.transportation_result[i].data.getName().equals("걷기")) {
                                        step_cnt +=  MyPageFragment.transportation_result[i].transportation_history.getCount().get(j);
                                    }else{
                                        step_save += MyPageFragment.transportation_result[i].transportation_history.getCount().get(j) * MyPageFragment.transportation_result[i].data.getCarbonPerUnit();
                                    }
                                }
                                total_carbon += MyPageFragment.transportation_result[i].transportation_history.getCount().get(j) * MyPageFragment.transportation_result[i].data.getCarbonPerUnit();
                            }
                        }
                        Log.i("HEHE","HAHA");
                        steps_txt.setText(step_cnt+" 걸음");
                        steps_Carbon_txt.setText(String.format("%.1f",(float) step_save) + "g");
                        total_walk_save.setText(String.format("%5.2fkg",(float)(total_carbon)/1000f));

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

                        TextView action = view.findViewById(R.id.mysprout_today_action);
                        TextView action_carbon = view.findViewById(R.id.mysprout_today_action_carbon);
                        TextView total_habit_save = view.findViewById(R.id.my_page_analysis_text_habit);

                        Calendar curr = Calendar.getInstance();

                        int action_val = 0;
                        int action_carbon_val = 0;
                        int total_carbon = 0;
                        for(int i = 0; i< MyPageFragment.action_result.length; i++){//종류
                            for(int j = 0; j < MyPageFragment.action_result[i].action_history.getCount().size(); j++){//각 기록
                                long miles = MyPageFragment.action_result[i].action_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0) {
                                    action_val += 1;
                                    action_carbon_val += MyPageFragment.action_result[i].action_history.getCount().get(j) * MyPageFragment.action_result[i].data.getSaveCarbon();
                                }
                                total_carbon += MyPageFragment.action_result[i].action_history.getCount().get(j) * MyPageFragment.action_result[i].data.getSaveCarbon();

                            }
                        }
                        action.setText(action_val+ "가지");
                        action_carbon.setText(String.format("%.1f",(float) action_carbon_val) + "g");
                        total_habit_save.setText(String.format("%5.2fkg",(float)(total_carbon)/1000f));
                    });
                }
        );
    }

    static int b = 0;
    static int l = 0;
    static int d = 0;
    static int saveTotal = 0;
    static int total_carbon = 0;
    static DB.Food_Pair[] Food_result = null;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void SetFood(){
        DB.getInstance().GetUserFoodHistory(
                result->{
                    Food_result = result;
                    runOnUiThread(() -> {

                        TextView Food = view.findViewById(R.id.mysprout_today_food);
                        TextView Food_carbon = view.findViewById(R.id.mysprout_today_food_carbon);
                        TextView total_meal_save = view.findViewById(R.id.my_page_analysis_text_meal);
                        Calendar curr = Calendar.getInstance();

                        b = 0;
                        l = 0;
                        d = 0;
                        int Food_val = 0;
                        int food_history_cnt = 0;
                        for(int i = 0; i< MyPageFragment.Food_result.length; i++){//종류
                            for(int j = 0; j < MyPageFragment.Food_result[i].food_history.getCount().size(); j++){//각 기록
                                long miles = MyPageFragment.Food_result[i].food_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0) {
                                    Food_val += 1;

                                    switch (MyPageFragment.Food_result[i].food_history.getMtime().get(j)) {
                                        case "b":
                                            b += MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit();
                                            break;
                                        case "l":
                                            l += MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit();
                                            break;
                                        case "d":
                                            d += MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit();
                                            break;
                                        default:
                                            Log.e("ERROR", "ERROR");
                                            break;
                                    }
                                }
                            }
                        }

                        Food.setText(Food_val+ "회");

                        DB.getInstance().GetUserInfo(ret->{
                            saveTotal = 0;
                            total_carbon = 0;
                            if(b != 0){
                                if(ret.getMeatCarbon() - b > 0)
                                    saveTotal += ( ret.getMeatCarbon() - b);
                            }
                            if(l != 0){
                                if(ret.getMeatCarbon() - l > 0)
                                    saveTotal += ( ret.getMeatCarbon() - l);
                            }
                            if(d != 0){
                                if(ret.getMeatCarbon() - d > 0)
                                    saveTotal += ( ret.getMeatCarbon() - d);
                            }
                            for(int i = 0; i< MyPageFragment.Food_result.length; i++){//종류
                                for(int j = 0; j < MyPageFragment.Food_result[i].food_history.getCount().size(); j++) {//각 기록
                                    if(ret.getMeatCarbon() - MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit() > 0)
                                        total_carbon += ret.getMeatCarbon() - MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit();

                                }
                            }
                            runOnUiThread(()->{
                                Food_carbon.setText(String.format("%.1f",(float) saveTotal) + "g");
                                total_meal_save.setText(String.format("%5.2fkg",(float)(total_carbon)/1000f));}
                            );

                        });

                    });
                }
        );



    }


//    public void onClick_my_back(View v) {
//        finish();
//    }
}

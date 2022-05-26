package com.example.mysprout.fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amplifyframework.datastore.generated.model.User;
import com.example.mysprout.DB;
import com.example.mysprout.MySprout;
import com.example.mysprout.R;
import com.example.mysprout.databinding.FragmentMyPageBinding;

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

    static DB.Action_Pair[] action_result = null;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void SetAction(){
        DB.getInstance().GetUserActionHistory(
                result->{
                    action_result = result;
                    runOnUiThread(() -> {

                        TextView action = view.findViewById(R.id.mysprout_today_action);
                        TextView action_carbon = view.findViewById(R.id.mysprout_today_action_carbon);
                        Calendar curr = Calendar.getInstance();

                        int action_val = 0;
                        int action_carbon_val = 0;
                        for(int i = 0; i< MyPageFragment.action_result.length; i++){//종류
                            for(int j = 0; j < MyPageFragment.action_result[i].action_history.getCount().size(); j++){//각 기록
                                long miles = MyPageFragment.action_result[i].action_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0) {
                                    action_val += 1;
                                    action_carbon_val += MyPageFragment.action_result[i].action_history.getCount().get(j) * MyPageFragment.action_result[i].data.getSaveCarbon();
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

                        TextView Food = view.findViewById(R.id.mysprout_today_food);
                        TextView Food_carbon = view.findViewById(R.id.mysprout_today_food_carbon);
                        Calendar curr = Calendar.getInstance();

                        int Food_val = 0;
                        int Food_carbon_val = 0;
                        for(int i = 0; i< MyPageFragment.Food_result.length; i++){//종류
                            for(int j = 0; j < MyPageFragment.Food_result[i].food_history.getCount().size(); j++){//각 기록
                                long miles = MyPageFragment.Food_result[i].food_history.getDate().get(j).getSecondsSinceEpoch() * 1000;
                                long days = TimeUnit.DAYS.convert(curr.getTime().getTime() - miles, TimeUnit.MILLISECONDS);
                                if(days == 0) {
                                    Food_val += 1;
                                    Food_carbon_val += MyPageFragment.Food_result[i].food_history.getCount().get(j) * MyPageFragment.Food_result[i].data.getCarbonPerUnit();
                                }
                            }
                        }
                        Food.setText(Food_val+ "회");
                        Food_carbon.setText(String.format("%.1f",(float) Food_carbon_val) + "g");

                    });
                }
        );
    }


//    public void onClick_my_back(View v) {
//        finish();
//    }
}

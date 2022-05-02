package com.example.mysprout.fragment;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysprout.R;
import com.example.mysprout.record_food_1;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//식단 기록, 인분, 그램수 정하는 프래그먼트
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetDialogFoodRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetDialogFoodRecord extends BottomSheetDialogFragment {
    /* TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
    private String foodName; //음식 이름
    private float carbonEmiss; //탄소 배출량 - 1인분 당-
    int minValue;
    int maxValue;
    int step; //선택가능 값 간격
    int defValue; //시작 값

    public BottomSheetDialogFoodRecord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomSheetDialogFoodRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetDialogFoodRecord newInstance(String param1, String param2) {
        BottomSheetDialogFoodRecord fragment = new BottomSheetDialogFoodRecord();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottomsheetlayout_foodrecord, container, false);

        //추가 버튼
        view.findViewById(R.id.addbtn_food).setOnClickListener(v-> {
            Toast.makeText(getContext(), "식단이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            
            //Numberpicker에서 정한 값 따로 저장되도록
            
            dismiss();
        });

        //Numberpicker -인분-
        NumberPicker numberPicker = view.findViewById(R.id.numberpicker_oneserving);

        assert getArguments() != null;
        foodName = getArguments().getString("foodname"); //선택한 음식 이름 뜨도록
        carbonEmiss = getArguments().getFloat("carbonemiss"); //선택한 음식, 1인분 당 탄소 배출량 뜨도록
        minValue = 1; //항상 1인분이 최소
        maxValue = 50; //항상 50인분이 최대
        step = 1; //항상 1칸씩 움직임
        defValue = 1; //항상 1인분부터 시작

        String[] mValues = getArrayWithSteps(minValue, maxValue, step); //numberpicker에서 표시될 숫자들 배열

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setDisplayedValues(mValues);

        numberPicker.setValue(defValue);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //키보드 입력 방지

        TextView textViewFoodName = view.findViewById(R.id.bottom_text_foodname);
        textViewFoodName.setText(foodName);

        TextView textViewCarbon = view.findViewById(R.id.bottom_text_carbon);
        textViewCarbon.setText(String.valueOf(carbonEmiss));

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //numberpicker 움직였을 때 리스너
                Toast.makeText(getContext(), "넘버피커", Toast.LENGTH_SHORT).show(); //일단 토스트만
            }
        });

        return view;
    }

    public String[] getArrayWithSteps (int min, int max, int step){
        int size = (max - min) / step + 1;

        String[] result = new String[size];

        for(int i = 0 ; i < size ; i++){
            result[i] = String.valueOf(min + step * i);
        }

        return  result;
    }
}
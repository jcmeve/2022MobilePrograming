package com.example.mysprout.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysprout.R;
import com.example.mysprout.databinding.BottomsheetlayoutFoodrecordBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//식단 기록, 인분, 그램수 정하는 프래그먼트
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetDialogFoodRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetDialogFoodRecord extends BottomSheetDialogFragment {
    //TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FOODNAME = "foodname";
    private static final String ARG_CARBON = "carbonemiss";

    // TODO: Rename and change types of parameters
    private String foodName; //음식 이름
    private float carbonEmiss; //탄소 배출량 - 1인분 당-
    int minValue;
    int maxValue;
    int step; //선택가능 값 간격
    int defValue; //시작 값

    private TextView textViewCarbon; //계속 업데이트 해야 해서 따로 빼놓음

    public BottomSheetDialogFoodRecord() {
        // Required empty public constructor
    }

    /*/**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomSheetDialogFoodRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetDialogFoodRecord newInstance(String foodName, float carbonEmiss) {
        BottomSheetDialogFoodRecord fragment = new BottomSheetDialogFoodRecord();
        Bundle args = new Bundle();
        args.putString(ARG_FOODNAME, foodName);
        args.putFloat(ARG_CARBON, carbonEmiss);
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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운드 둥글게 만들려고 하는데 안 돌아감

        TextView textViewFoodName = view.findViewById(R.id.bottom_text_foodname);
        textViewCarbon = view.findViewById(R.id.bottom_text_carbon);
        //Numberpicker -인분-
        NumberPicker numberPicker = view.findViewById(R.id.numberpicker_oneserving);

        view.findViewById(R.id.exit_bottomdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //추가 버튼 리스너
        view.findViewById(R.id.addbtn_food).setOnClickListener(v-> {
            if(foodRecordDataPassListener != null){
                int unit = numberPicker.getValue();
                //Log.d("넘버피커", String.valueOf(unit));
                String thisName = (String) textViewFoodName.getText();
                foodRecordDataPassListener.onRecordDataPass(view, thisName, unit);
            }
            Toast.makeText(getContext(), "식단이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            
            dismiss();
        });

        assert getArguments() != null;
        foodName = getArguments().getString(ARG_FOODNAME); //선택한 음식 이름 뜨도록
        carbonEmiss = getArguments().getFloat(ARG_CARBON); //선택한 음식, 1인분 당 탄소 배출량 뜨도록
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

        textViewFoodName.setText(foodName);

        textViewCarbon.setText(String.valueOf(carbonEmiss));

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int beford, int after) {
                //numberpicker 움직였을 때 리스너
                textViewCarbon.setText(String.valueOf(carbonEmiss*after));
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

    //Data from BottomSheetDialog to RecordFood
    private FoodRecordDataPassListener foodRecordDataPassListener;
    public void setOnDataPassListener(FoodRecordDataPassListener listener){
        foodRecordDataPassListener = listener;
    }

    public interface FoodRecordDataPassListener{
        void onRecordDataPass(View view, String name, int unit);
    }
}
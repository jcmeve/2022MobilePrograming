package com.example.mysprout.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mysprout.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogMovementAmount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogMovementAmount extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DialogMovementAmount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogMovementAmount.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogMovementAmount newInstance(String param1, String param2) {
        DialogMovementAmount fragment = new DialogMovementAmount();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    int minValue;
    int maxValue;
    int step;
    int defValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_movement_amount, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        NumberPicker numberPicker = view.findViewById(R.id.numberpicker_movement_amount);

        minValue = 1; //최소
        maxValue = 100; //최대
        step = 1; //간격
        defValue = 5; //시작값

        String[] myValues = getArrayWithSteps();

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setDisplayedValues(myValues);
        numberPicker.setValue(defValue);

        view.findViewById(R.id.amountdialogbtn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movementAmountDataListener != null){
                    int amount = numberPicker.getValue();
                    Log.d("이동량", String.valueOf(amount));
                    movementAmountDataListener.setMovementAmount(amount);
                }
                Toast.makeText(getContext(), "이동량 설정 완료", Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

        view.findViewById(R.id.amountdialogbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movementAmountDataListener != null){
                    movementAmountDataListener.setMovementAmount(-1);
                }

                dismiss();
            }
        });

        return  view;
    }

    public String[] getArrayWithSteps (){
        int size = (maxValue - minValue) / step + 1;

        String[] result = new String[size];

        for(int i = 0 ; i < size ; i++){
            result[i] = String.valueOf(minValue + step * i);
        }

        return  result;
    }

    private MovementAmountDataListener movementAmountDataListener;
    public void setMovementAmountDataListener(MovementAmountDataListener listener){
        movementAmountDataListener = listener;
    }

    public interface MovementAmountDataListener{
        void setMovementAmount(int amount);
    }
}
package com.example.mysprout.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mysprout.R;
import com.example.mysprout.RecordStep;

import java.util.Objects;


public class DialogChooseTransportation extends DialogFragment {
    String chosenTransport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_choose_transportation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        chosenTransport = "";

        Button okBtn = view.findViewById(R.id.dialog_choose_transport_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!chosenTransport.isEmpty() && chosenDataListener != null){
                    chosenDataListener.giveData(chosenTransport);
                    Toast.makeText(getContext(), chosenTransport + "선택", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else if(chosenTransport.isEmpty()){
                    Toast.makeText(getContext(), "이동 수단을 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancelBtn = view.findViewById(R.id.dialog_choose_transport_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenTransport = "";
                if(chosenDataListener != null){
                    chosenDataListener.giveData("CANCEL");
                }
                dismiss();
            }
        });

        RadioButton carBtn = view.findViewById(R.id.dialog_choose_transport_car);
        carBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                if(checked){
                    chosenTransport = "승용차";
                }
            }
        });

//        RadioButton carBtn = view.findViewById(R.id.dialog_choose_transport_car);
//        carBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    chosenTransport = "CAR";
//                    Log.d("CHOOSE", chosenTransport);
//                }
//            }
//        });

        RadioButton busBtn = view.findViewById(R.id.dialog_choose_transport_bus);
        busBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                if(checked){
                    chosenTransport = "버스";
                }
            }
        });

        RadioButton subwayBtn = view.findViewById(R.id.dialog_choose_transport_subway);
        subwayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                if(checked){
                    chosenTransport = "지하철";
                }
            }
        });
    }

    private  ChosenDataListener chosenDataListener;
    public void setChosenDataListener(ChosenDataListener listener){
        chosenDataListener = listener;
    }

    //액티비티에 정보 전달할 인터페이스
    public interface ChosenDataListener{
        void giveData(String tag);
    }
}
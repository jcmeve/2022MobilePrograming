package com.example.mysprout.fragment;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mysprout.R;
import com.example.mysprout.record_food_1;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//식단 기록, 인분, 그램수 정하는 프래그먼트
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetDialogFoodRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetDialogFoodRecord extends BottomSheetDialogFragment {
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.bottomsheetlayout_foodrecord, container, false);
        view.findViewById(R.id.addbtn_food).setOnClickListener(v-> {
            Toast.makeText(getContext(), "식단이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return view;
    }
}
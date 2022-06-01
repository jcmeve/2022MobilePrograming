package com.example.mysprout.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mysprout.R;

public class RecycleInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recycle_info, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View paper = view.findViewById(R.id.recycle_info_button_paper);
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });

        View can = view.findViewById(R.id.recycle_info_button_can);
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });

        View glass = view.findViewById(R.id.recycle_info_button_glass);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });

        View petAndPlastic = view.findViewById(R.id.recycle_info_button_plastic);
        petAndPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });

        View vinyl = view.findViewById(R.id.recycle_info_button_vinyl);
        vinyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });

        View styrofoam = view.findViewById(R.id.recycle_info_button_styrofoam);
        styrofoam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecycleInfo();
            }
        });
    }

    public void showRecycleInfo(){
        DialogRecycleInfo dialog = new DialogRecycleInfo();
        dialog.show(getActivity().getSupportFragmentManager(), "recycle info dialog");
    }
}

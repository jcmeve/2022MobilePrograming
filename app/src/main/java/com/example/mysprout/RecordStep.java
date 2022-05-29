package com.example.mysprout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mysprout.databinding.RecordStepBinding;
import com.example.mysprout.fragment.DialogChooseTransportation;

public class RecordStep extends AppCompatActivity {

    RecordStepBinding stepBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepBinding = RecordStepBinding.inflate(getLayoutInflater());
        setContentView(stepBinding.getRoot());

        DialogChooseTransportation dialog = new DialogChooseTransportation();

        dialog.show(getSupportFragmentManager(), "choose transportation dialog");
    }
}
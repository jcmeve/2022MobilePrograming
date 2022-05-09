package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class sprout2 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprout2);
    }

    public void onClicks2_1(View v) {
        Intent intent = new Intent(getApplicationContext(), RecordHabits.class);
        startActivity(intent);

    }

    public void onClicks2_2(View v) {
        Intent intent = new Intent(getApplicationContext(), RecordFood.class);
        startActivity(intent);

    }

}



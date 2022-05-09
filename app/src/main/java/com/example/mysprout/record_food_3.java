package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class record_food_3 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_food_3);
    }

    public void onClickfN3(View v) {
        Intent intent = new Intent(getApplicationContext(), record_food_4.class);
        startActivity(intent);

    }
}

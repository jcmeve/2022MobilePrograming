package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class record_food_4 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_food_4);
    }

    public void onClickfN4(View v) {
        Intent intent = new Intent(getApplicationContext(), save.class);
        startActivity(intent);

    }
}

package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class record_2 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_2);
    }

    public void onClickN2(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), record_3.class);
        startActivity(intent);

    }
}

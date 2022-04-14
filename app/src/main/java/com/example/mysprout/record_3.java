package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class record_3 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_3);
    }

    public void onClickN3(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), save.class);
        startActivity(intent);

    }
}
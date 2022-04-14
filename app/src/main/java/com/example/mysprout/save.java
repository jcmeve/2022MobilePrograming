package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class save extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
    }

    public void onClickF(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), sprout.class);
        startActivity(intent);

    }
}

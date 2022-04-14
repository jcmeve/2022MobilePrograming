package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class record_1 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_1);

//        ImageView iv12 = (ImageView) findViewById(R.id.imageView12);
//        ImageView iv13 = (ImageView) findViewById(R.id.imageView13);
//
//        iv12.setAdjustViewBounds(true);
//        iv12.setMaxWidth(400);
//        iv12.setMaxHeight(400);
//        iv13.setAdjustViewBounds(true);
//        iv13.setMaxWidth(400);
//        iv13.setMaxHeight(400);
//
//        iv12.setImageDrawable(getResources().getDrawable(R.drawable.page7));
//        iv13.setImageDrawable(getResources().getDrawable(R.drawable.page8));
    }

    public void onClickN1(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), record_2.class);
        startActivity(intent);

    }
}

package com.example.mysprout;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

public class GrowSprout extends AppCompatActivity {

    private float expBefore;
    private float expUpdate;

    private float startPoint;
    private float endPoint;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_sprout);

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        float save = intent.getFloatExtra("save", 0.f);
        Log.d("절약량", String.valueOf(save));
        int habitNum = intent.getIntExtra("num", 0);

        setText(tag, save, habitNum);

        calEXP(save);
        makeAnim();

    }

    void calEXP(float save){
        //DB에서 유저 level, EXP 불러오기 / 임시 값: leve 1, exp 0
        int level = 1; //임시값
        expBefore = 0; //임시값
        expUpdate = expBefore + save;
        //level * 1000을 목표 EXP로 생각
        startPoint = (expBefore - ((level-1)*1000)) / 1000; //progress bar 시작 지점
        endPoint = (expUpdate - expBefore) / 1000;
        Log.d("calEXP", String.valueOf(startPoint)+("/")+String.valueOf(endPoint));
        //기록된 절약량 추가한 EXP, level DB로 전송
        DB.getInstance().AddSaveCarbon((int) save);
    }

    void makeAnim(){
        LottieAnimationView expBar = findViewById(R.id.expbar_growsprout);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startPoint, endPoint);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                expBar.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.start();
    }

    @SuppressLint("SetTextI18n")
    void setText(String tag, float save, int num){
        TextView dialog = findViewById(R.id.dialog_growsprout);
        TextView nowSave = findViewById(R.id.carbon_save_growsprout);

        switch(tag){
            case "Food":
                dialog.setText("식단 기록 완료!"); //나중에 아침, 점심, 저녁 받기
                break;

            case "Habits":
                dialog.setText(String.valueOf(num)+"가지 실천 완료!");
                break;

            case "Walk":
                break;
        }

        nowSave.setText(String.valueOf(save));

        //level도 DB에서 받아와서 업데이트
    }

    public void onClickN3(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Intent intent = new Intent(GrowSprout.this, MainSprout.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
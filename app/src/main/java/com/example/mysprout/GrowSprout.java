package com.example.mysprout;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    void calEXP(float save) {
        //DB에서 유저 level, EXP 불러오기 / 임시 값: leve 1, exp 0
        expBefore = 0; //임시값
        expUpdate = expBefore + save;

        int level = DB.ExpToLevel((int) expBefore);//curr level

        //level * 1000을 목표 EXP로 생각
        startPoint = (expBefore - (DB.GetStartExp(level))) / 1000; //progress bar 시작 지점
        endPoint = (expUpdate - expBefore) / 1000;
        Log.d("calEXP", String.valueOf(startPoint) + ("/") + String.valueOf(endPoint));
        //기록된 절약량 추가한 EXP, level DB로 전송
        DB.getInstance().AddSaveCarbon((int) save);
    }

    void makeAnim() {
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
    void setText(String tag, float save, int num) {
        TextView dialog = findViewById(R.id.dialog_growsprout);
        TextView nowSave = findViewById(R.id.carbon_save_growsprout);

        switch (tag) {
            case "Food":
                dialog.setText("식단 기록 완료!"); //나중에 아침, 점심, 저녁 받기
                break;

            case "Habits":
                dialog.setText(String.valueOf(num) + "가지 실천 완료!");
                break;

            case "Walk":
                break;
        }

        nowSave.setText(String.valueOf(save));

        //level도 DB에서 받아와서 업데이트
    }

    public void onClickN3(View v) {
        ConstraintLayout container = findViewById(R.id.container);
        Intent intent = new Intent(GrowSprout.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    //인스타 버튼..아지만 트위터 시도2
    public void instarButton(View button) {
        String strLink = null;
        try {
            strLink = String.format("http://twitter.com/intent/tweet?text=%s",
                    URLEncoder.encode("공유할 텍스트를 입력하세요", "utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strLink));
        startActivity(intent);
    }

    //트위터 버튼
    public void twitterButton(View button) {
        Toast.makeText(GrowSprout.this, "트위터에 공유합니다.", Toast.LENGTH_SHORT).show();
        try {
            String sharedText = String.format("http://twitter.com/intent/tweet?text=%s",
                    URLEncoder.encode("공유한다", "utf-8"));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharedText));
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //페이스북 버튼
    public void facebookButton(View button){
        Toast.makeText(GrowSprout.this, "페이스북에 공유합니다.", Toast.LENGTH_SHORT).show();

        //onRequestPermissionsResult();
        if(permissionCheck) {
       // Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.image);
        String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "이미지명.png";
        String folderName = "/폴더명/";
        String fullPath = storage + folderName;
        File filePath;
        try {
            filePath = new File(fullPath);
            if (!filePath.isDirectory()) {
                filePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(fullPath + fileName);
         //   bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();fos.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        Uri uri = Uri.fromFile(new File(fullPath, fileName));
        try {
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.putExtra(Intent.EXTRA_TEXT, "텍스트는 지원 노");
            share.setPackage("com.instagram.android");startActivity(share);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(this, "페이스북이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    //페이스북 빌드업업
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    public void onRequestPermission() {
        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);int permissionWriteStorage =
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permissionReadStorage == PackageManager.PERMISSION_DENIED
                        || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CODE);
                }else {
                    permissionCheck = true;
                }
    }
    //@Override
    public void onRequestPermission(int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_CODE:for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
               // int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //    if(grantResult == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "허용O", Toast.LENGTH_SHORT).show();
                        permissionCheck = true;} else {Toast.makeText(this, "허용X", Toast.LENGTH_SHORT).show();
                     //   permissionCheck = false
                    }
                }
            }
           // break;
      //  }
    }




}





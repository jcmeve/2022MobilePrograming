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

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GrowSprout extends AppCompatActivity {

    private int expBefore;
    private int expUpdate;
    private int lvlBefore;
    private int lvlUpdate;

    LottieAnimationView expBar;
    TextView lvlText;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_sprout);

        expBar = findViewById(R.id.expbar_growsprout);
        lvlText = findViewById(R.id.grow_sprout_lvl_text);
        lvlText.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        int save = intent.getIntExtra("save", 0);
        Log.d("절약량", String.valueOf(save));

        int num = 0;

        if(tag.equals("Habits")){
            num = intent.getIntExtra("num", 0);
        }else if(tag.equals("Walk")){
            num = intent.getIntExtra("step", 0);
        }

        calEXP(tag, save);
        setText(tag, save, num);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void calEXP(String tag, int save) {
        //DB에서 유저 level, EXP 불러오기
        DB.getExpCallBack expCallBack = new DB.getExpCallBack() {
            @Override
            public void callback(int exp, int lvl) {
                expBefore = exp;
                lvlBefore = lvl;
                expUpdate = exp + save;
                lvlUpdate = DB.ExpToLevel(expUpdate);

                if(tag.equals("Walk")){
                    makeAnim();
                }else{
                    DB.getInstance().AddSaveCarbon(save);
                    Log.d("growSavings", String.valueOf(save));
                    makeAnim();
                }
            }
        };

        DB.getInstance().GetTotalExp(expCallBack);
    }

    void makeAnim() {
        //현 레벨에서 다음 레벨까지 몇 칸 차이 나는지 계산
        //차이 나는 만큼 애니메이션 반복
        //프레임 다 찰 때마다 레벨 수 변화
        lvlText.setVisibility(View.VISIBLE);
        lvlText.setText("Lv."+String.valueOf(lvlBefore));
        int repeatNum = lvlUpdate - lvlBefore;

        float startPoint = (expBefore - DB.GetStartExp(lvlBefore))/DB.GetStartExp(lvlBefore + 1);
        Log.d("growstartpoint", String.valueOf(startPoint));

        float endPoint = (expUpdate - DB.GetStartExp(lvlUpdate))/DB.GetStartExp(lvlUpdate + 1);
        Log.d("growendpoint", String.valueOf(endPoint));

        LottieAnimationView sproutAnim = findViewById(R.id.sprout_anim);
        sproutAnim.playAnimation();

        if(repeatNum == 0){
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startPoint, endPoint);
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener(listener);
            valueAnimator.start();
        }else{
            ValueAnimator valueAnimator;
            for(int i = 1 ; i <= repeatNum ; i++){
                if(i == 1){

                    valueAnimator = ValueAnimator.ofFloat(startPoint, 1);
                    valueAnimator.setDuration(1000);
                    valueAnimator.addUpdateListener(listener);
                    valueAnimator.start();

                }else if(i == repeatNum){

                    valueAnimator = ValueAnimator.ofFloat(0, endPoint);
                    valueAnimator.setDuration(2000);
                    valueAnimator.addUpdateListener(listener);
                    valueAnimator.start();

                }else{
                    lvlText.setText("Lv."+(lvlBefore+(i-1)));
                    valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setDuration(1000);
                    valueAnimator.addUpdateListener(listener);
                    valueAnimator.start();

                }
            }

            lvlText.setText("Lv."+lvlUpdate);
        }

    }

    private final valueUpdateListener listener = new valueUpdateListener();
    class valueUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            expBar.setProgress((Float) animation.getAnimatedValue());
        }
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
                dialog.setText(num + "가지 실천 완료!");
                break;

            case "Walk":
                dialog.setText(num + "걸음 기록 완료!");
                break;
        }
        nowSave.setText(String.valueOf(save));
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





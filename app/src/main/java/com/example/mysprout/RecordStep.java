package com.example.mysprout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ToggleButton;

import com.example.mysprout.databinding.RecordStepBinding;
import com.example.mysprout.fragment.DialogChooseTransportation;

import java.io.InputStream;

public class RecordStep extends AppCompatActivity implements DialogChooseTransportation.ChosenDataListener {
    //뷰 바인딩
    RecordStepBinding stepBinding;
    String chosenTransport;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepBinding = RecordStepBinding.inflate(getLayoutInflater());
        setContentView(stepBinding.getRoot());

        chosenTransport = "";
        showDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        stepBinding.recordStepButtonStartAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((ToggleButton)view).isChecked();

                if(checked){
                    stepBinding.recordStepButtonStartAndStop.setTextColor(getResources().getColor(R.color.white));
                    //서비스(걸음 수 측정) 시작
                    callNotification();
                }else{
                    //종료 묻는 다이얼로그 띄운 후 서비스(걸음 수 측정) 종료
                }
            }
        });
    }

    void showDialog(){
        DialogChooseTransportation dialog = new DialogChooseTransportation();

        dialog.setChosenDataListener(new DialogChooseTransportation.ChosenDataListener() {
            @Override
            public void giveData(String tag) {
                if(tag.equals("CANCEL")){
                    finish();
                }else{
                    stepBinding.recordStepTextTransport.setText(tag + " 대신 걷기!");
                    stepBinding.recordStepTextTransport.setText(emphasizeText(tag));

                    chosenTransport = tag;
                }
            }
        });

        dialog.show(getSupportFragmentManager(), "choose transportation dialog");
    }

    SpannableString emphasizeText(String word){

        String content = stepBinding.recordStepTextTransport.getText().toString();

        SpannableString spannableString = new SpannableString(content);
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.mintGreen)),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public void giveData(String tag) {

    }

    //여기서부터 Notification 생성 메소드
    public void callNotification(){
        NotificationCompat.Builder builder = getBuilder("channel_1", "noti_record_steps");
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay); //임시 small 아이콘
        builder.setContentTitle("0,000걸음"); //걸음 수 업데이트
        builder.setContentText(chosenTransport + " 대신 걸어서 환경 보호"); //사용자 선택한 이동수단 따라 텍스트 변경
        Bitmap bitmapWalking = BitmapFactory.decodeResource(getResources(), R.drawable.icon_baseline_walk);
        builder.setLargeIcon(bitmapWalking);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);

        //PendingIntent로 noti누르면 RecordStep 액티비티로 돌아오도록 시스템에 의뢰
        Intent intent = new Intent(this, RecordStep.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        notificationManager.notify(2000, notification);
    }

    public NotificationCompat.Builder getBuilder(String channel_id, String channel_name){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true); //일단 진동만
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channel_id);
        }else{
            builder = new NotificationCompat.Builder(this, channel_id);
        }
        return builder;
    }
}
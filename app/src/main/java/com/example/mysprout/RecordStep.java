package com.example.mysprout;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.mysprout.databinding.RecordStepBinding;
import com.example.mysprout.fragment.DialogChooseTransportation;

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
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean onRun = false;

        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            Log.i("SEVIRENAME",service.service.getClassName());
            if(StepService.class.getName().equals(service.service.getClassName())){
                onRun = true;
                break;
            }
        }
        if(onRun) {
            stepBinding.recordStepButtonStartAndStop.toggle();
        }
        stepBinding.recordStepButtonStartAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((ToggleButton)view).isChecked();

                if(checked){
                    stepBinding.recordStepButtonStartAndStop.setTextColor(getResources().getColor(R.color.white));


                    Intent intent1 = new Intent(getApplicationContext(), StepService.class);
                    intent1.putExtra("Method", "StartRecord");
                    intent1.putExtra("Transport", chosenTransport);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent1);
                    }else{
                        Log.e("SDKV","ERRORORR");
                    }

                    Intent intent2 = new Intent(getApplicationContext(), StepService.class);
                    intent2.putExtra("Messenger",mMessenger);
                    intent2.putExtra("Method","ActivityOn");
                    startService(intent2);



                    //서비스(걸음 수 측정) 시작
               //     callNotification();
                }else{
                    //종료 묻는 다이얼로그 띄운 후 서비스(걸음 수 측정) 종료
                    Intent intent = new Intent(getApplicationContext(), StepService.class);
                    intent.putExtra("Method","StopRecord");
                    startService(intent);
                }
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String data = (String)msg.obj;
            Log.i("DATA ", data);
            super.handleMessage(msg);
        }
    };
    private Messenger mMessenger = new Messenger(mHandler);

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
/*
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
*/
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

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), StepService.class);
        intent.putExtra("Method","ActivityOff");
        startService(intent);

        super.onDestroy();
    }
}
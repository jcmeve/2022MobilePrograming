package com.example.mysprout;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.amplifyframework.datastore.generated.model.TransportationData;
import com.example.mysprout.databinding.RecordStepBinding;
import com.example.mysprout.fragment.DialogChooseTransportation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RecordStep extends AppCompatActivity implements DialogChooseTransportation.ChosenDataListener {
    //뷰 바인딩
    RecordStepBinding stepBinding;
    static String chosenTransport;
    NotificationManager notificationManager;
    String filepath = "/serviceOn";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepBinding = RecordStepBinding.inflate(getLayoutInflater());
        setContentView(stepBinding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean onRun = false;

        File file = new File(getFilesDir() + filepath);
        if(file.exists())
            onRun = true;

        if (onRun) {
            stepBinding.recordStepButtonStartAndStop.toggle();
            Intent intent2 = new Intent(getApplicationContext(), StepService.class);
            intent2.putExtra("Messenger", mMessenger);
            intent2.putExtra("Method", "ActivityOn");
            startService(intent2);
        } else {
            showDialog();
        }
        stepBinding.recordStepButtonStartAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((ToggleButton) view).isChecked();

                if (checked) {
                    stepBinding.recordStepButtonStartAndStop.setTextColor(getResources().getColor(R.color.white));
                    try {
                        FileWriter fileWriter = new FileWriter(file,false );
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(1);
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent intent1 = new Intent(getApplicationContext(), StepService.class);
                    intent1.putExtra("Method", "StartRecord");
                    intent1.putExtra("Transport", chosenTransport);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent1);
                    } else {
                        Log.e("SDKV", "ERRORORR");
                    }

                    Intent intent2 = new Intent(getApplicationContext(), StepService.class);
                    intent2.putExtra("Messenger", mMessenger);
                    intent2.putExtra("Method", "ActivityOn");
                    startService(intent2);


                    //서비스(걸음 수 측정) 시작
                    //     callNotification();
                } else {
                    stepBinding.recordStepButtonStartAndStop.setTextColor(getResources().getColor(R.color.almostBlack));
                    //종료 묻는 다이얼로그 띄운 후 서비스(걸음 수 측정) 종료
                    alert(file);

//                    file.delete();
//                    Intent intent = new Intent(getApplicationContext(), StepService.class);
//                    intent.putExtra("Method", "StopRecord");
//                    intent.putExtra("Messenger", mStopMessenger);
//                    startService(intent);
//                    stopService(intent);

                }
            }
        });
    }
    private final Handler activityHandler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            String data = (String)msg.obj;
            Log.i("DATA ", data);
            String[] tokens = data.split("/");

            stepBinding.recordStepTextSteps.setText(tokens[0]);
            stepBinding.recordStepDistance.setText(tokens[1]);
            stepBinding.recordStepTime.setText(tokens[2]);
            stepBinding.recordStepCalorie.setText(tokens[3]);
            super.handleMessage(msg);
        }
    };
    private final Handler mStopHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                String data = (String)msg.obj;
            Log.i("DATA ", data);
            String[] tokens = data.split("/");//0 -> step, 1 -> km

                DB.getInstance().AddTransportationData("걷기", Integer.parseInt(tokens[0]));
                DB.getInstance().AddTransportationData(chosenTransport, Double.parseDouble(tokens[1]));
                DB.getInstance().GetTransportationList((result) -> {
                    TransportationData transportationData = null;
                    for (int i = 0; i < result.length; i++) {
                        if (result[i].getName().equals(chosenTransport)) {
                            transportationData = result[i];
                            break;
                        }
                    }
                    Log.i("km",tokens[1]);
                    int saveCarbon = (int)(transportationData.getCarbonPerUnit() * Double.parseDouble(tokens[1]));
                    DB.getInstance().AddSaveCarbon(saveCarbon);

                    finishRecordStep(Integer.parseInt(tokens[0]), saveCarbon);
                });
            }catch (Exception e){
                e.printStackTrace();
            }

            super.handleMessage(msg);
        }
    };
    private final Messenger mMessenger = new Messenger(activityHandler);
    private final Messenger mStopMessenger = new Messenger(mStopHandler);

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

    void alert(File file){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        View view = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_custom, (LinearLayout)findViewById(R.id.layoutDialog));

        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText("기록을 종료할까요?");
        ((TextView)view.findViewById(R.id.textMessage)).setVisibility(View.GONE);

        AlertDialog alertDialog = builder.create();

        ((Button)view.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "기록 종료", Toast.LENGTH_SHORT).show();

                file.delete();
                Intent intent = new Intent(getApplicationContext(), StepService.class);
                intent.putExtra("Method", "StopRecord");
                intent.putExtra("Messenger", mStopMessenger);
                startService(intent);
                stopService(intent);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "기록을 계속합니다.", Toast.LENGTH_SHORT).show();
                stepBinding.recordStepButtonStartAndStop.toggle();
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
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

    void finishRecordStep(int steps, int savedCarbon){
        Intent intent = new Intent(this, GrowSprout.class);
        intent.putExtra("tag", "Walk");
        intent.putExtra("step", steps);
        intent.putExtra("save", (float)savedCarbon);
        Log.d("savedpass", String.valueOf(savedCarbon));
        startActivity(intent);
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

    public void onClick_s_back(View v) {
        finish();
    }

}
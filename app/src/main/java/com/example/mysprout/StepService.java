package com.example.mysprout;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class StepService extends Service {
    boolean activityOn = false;
    final String CHANNEL_ID = "channel_1";
    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    NotificationManager notificationManager;
    public void callNotification(String transport){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Test notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("TEST");
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = getBuilder(CHANNEL_ID, "noti_record_steps");
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay); //임시 small 아이콘
        builder.setContentTitle("0,000걸음"); //걸음 수 업데이트
        builder.setContentText(transport + " 대신 걸어서 환경 보호"); //사용자 선택한 이동수단 따라 텍스트 변경
        Bitmap bitmapWalking = BitmapFactory.decodeResource(getResources(), R.drawable.icon_baseline_walk);
        builder.setLargeIcon(bitmapWalking);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);

        //PendingIntent로 noti누르면 RecordStep 액티비티로 돌아오도록 시스템에 의뢰
        Intent intent = new Intent(this, RecordStep.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        startForeground(2000, notification);
        //notificationManager.notify(2000, notification);
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String method = (String) intent.getExtras().get("Method");
        if(method.equals("StartRecord")){
            String transport = (String)intent.getExtras().get("Transport");
            String filename = "/RecordStartTime.txt";
            File file = new File(getFilesDir() + filename);
            try {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String data =   c.get(Calendar.YEAR)+"/" +(c.get(Calendar.MONTH)+1)+"/" + c.get(Calendar.DATE)+"/" + c.get(Calendar.HOUR_OF_DAY)+"/"+c.get(Calendar.MINUTE);
                Log.i("STEP DATA", data);
                bufferedWriter.write(data);
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            callNotification(transport);




        }else if(method.equals("ActivityOn")) {
            activityOn = true;
            Messenger messenger = (Messenger) intent.getExtras().get("Messenger");
            Thread thread = new Thread(() -> {
                while (activityOn) {
                    readData();

                    Message msg = Message.obtain();
                    msg.obj = "TEST!!!";
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }else if(method.equals("ActivityOff")){
            activityOn = false;
        }else if(method.equals("StopRecord")){
            String filename = "/RecordStartTime.txt";
            File file = new File(getFilesDir() + filename);

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String data = bufferedReader.readLine();


                //fit service로 총 걸음수 계산, DB업데이트 혹은 messenger 연결해서 값 액티비티에 주기
            } catch (IOException e) {
                e.printStackTrace();
            }
            activityOn = false;
            
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("ONDES","ROY");

        super.onDestroy();
    }








    private void readData() {
        final Calendar cal = Calendar.getInstance();
        Date now = Calendar.getInstance().getTime();
        cal.setTime(now);

        // 시작 시간
        cal.set(cal.get(Calendar.YEAR)-1, cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 6, 0, 0);
        long startTime = cal.getTimeInMillis();

        // 종료 시간
        cal.set(cal.get(Calendar.YEAR)+1, cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 23, 0, 0);
        long endTime = cal.getTimeInMillis();
        String TAG = "GOOGLE FIT";
        try {


            Fitness.getHistoryClient(this,
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                    .readData(new DataReadRequest.Builder()
                            .read(DataType.TYPE_STEP_COUNT_DELTA) // Raw 걸음 수
                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                            .build())
                    .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                        @Override
                        public void onSuccess(DataReadResponse response) {
                            DataSet dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                            Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
                            Log.i(TAG, dataSet.getDataPoints().size() + "");
                            for (DataPoint dp : dataSet.getDataPoints()) {
                                Log.i(TAG, "Data point:");
                                Log.i(TAG, "\tType: " + dp.getDataType().getName());

                                Log.i(TAG, "\tStart: " + (dp.getStartTime(TimeUnit.MILLISECONDS) / 1000));
                                Log.i(TAG, "\tEnd: " + (dp.getEndTime(TimeUnit.MILLISECONDS) / 1000));
                                for (Field field : dp.getDataType().getFields()) {
                                    Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                                }
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
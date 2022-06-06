package com.example.mysprout;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StepService extends Service {
    static boolean activityOn = false;
    private void setActivityOn(boolean var){
        activityOn = var;
        Log.i("ActivityOn  " ,(var?"true":"false" ));
    }

    static boolean notifyOn = false;
    private void setNotifyOn(boolean var){
        notifyOn = var;
        Log.i("notifyOn  " ,(var?"true":"false" ));
    }

    final String CHANNEL_ID = "channel_1";
    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("LIFE","CREATE");
        super.onCreate();
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
        Intent intent = new Intent(this, RecordStep.class);
        PendingIntent pendingIntent;

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
       //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(Build.VERSION.SDK_INT >= 31) {
            pendingIntent = stackBuilder.getPendingIntent(1000,PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            //pendingIntent = PendingIntent.getActivity(this, 1000, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            pendingIntent = stackBuilder.getPendingIntent(1000,PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            //pendingIntent = PendingIntent.getActivity(this, 1000, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        }
        NotificationCompat.Builder builder = getBuilder(CHANNEL_ID, "noti_record_steps");

        Thread thread = new Thread(() -> {
            boolean firstTime = true;
            while (notifyOn)
            {
                if(firstTime) {
                    builder.setSmallIcon(android.R.drawable.ic_notification_overlay); //임시 small 아이콘

                    Bitmap bitmapWalking = BitmapFactory.decodeResource(getResources(), R.drawable.icon_baseline_walk);
                    builder.setLargeIcon(bitmapWalking);
                    builder.setContentText(transport + " 대신 걸어서 환경 보호"); //사용자 선택한 이동수단 따라 텍스트 변경
                    builder.setDefaults(Notification.DEFAULT_VIBRATE);
                    builder.setOnlyAlertOnce(true);
                    builder.setContentIntent(pendingIntent);
                    firstTime = false;
                }
                //PendingIntent로 noti누르면 RecordStep 액티비티로 돌아오도록 시스템에 의뢰



                builder.setContentTitle(totalSteps + "걸음"); //걸음 수 업데이트


                Notification notification = builder.build();
                startForeground(2000, notification);

                long endTime = new Date().getTime();
                readData(null, startTime, endTime);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });
        thread.start();

        //notificationManager.notify(2000, notification);
    }
    public NotificationCompat.Builder getBuilder(String channel_id, String channel_name){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
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
            startTime = new Date().getTime();
            setNotifyOn(true);
            String transport = (String)intent.getExtras().get("Transport");

            callNotification(transport);

        }else if(method.equals("ActivityOn")) {
            if(activityOn)
                return super.onStartCommand(intent, flags, startId);
            setActivityOn(true);
            Messenger messenger = (Messenger) intent.getExtras().get("Messenger");
            Thread thread = new Thread(() -> {
                while (activityOn) {

                    long endTime = new Date().getTime();
                    long deltaTime = ((endTime - startTime)/1000)/60;
                    int H = (int) (deltaTime/60);
                    int M = (int) (deltaTime%60);

                    Message msg = Message.obtain();
                    msg.obj =
                            StepService.totalSteps + "/" +
                            String.format("%.2f",(StepService.totalDistance / 1000)) +"/"+
                            String.format("%02d",H)+":"+String.format("%02d",M)   +"/" +
                            (int)totalKCal;
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
            setActivityOn(false);
        }else if(method.equals("StopRecord")){

            Messenger messenger = (Messenger)intent.getExtras().get("Messenger");

            long endTime = new Date().getTime();
            readData(messenger, startTime, endTime);

                //fit service로 총 걸음수 계산, DB업데이트 혹은 messenger 연결해서 값 액티비티에 주기

            setNotifyOn(false);
            setActivityOn(false);
            stopForeground(true);
            stopSelfResult(startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("LIFE","DESTROY");
        setNotifyOn(false);
        setActivityOn(false);
        super.onDestroy();
    }
    public static int totalSteps = 0;
    public static double totalDistance = 0;
    public static double totalKCal = 0;



    static long startTime = 0;

    private void readData(Messenger messenger, long startTime, long endTime){

        Log.i("starttime : ",     Long.toString(startTime));
        Log.i("endtime : ",      Long.toString(endTime));

        String TAG = "GOOGLE FIT";


        try {
            Fitness.getHistoryClient(this,
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()))
                    .readData(new DataReadRequest.Builder()
                            .read(DataType.TYPE_STEP_COUNT_DELTA) // Raw 걸음 수
                            .read(DataType.TYPE_DISTANCE_DELTA)
                            .read(DataType.TYPE_CALORIES_EXPENDED)
                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                            .build())
                    .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                        @Override
                        public void onSuccess(DataReadResponse response) {
                            int sumSteps = 0;
                            float sumDistance = 0;
                            float sumKCal = 0;
                            DataSet stepDataset = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);

                            for (DataPoint dp : stepDataset.getDataPoints()) {

                                for (Field field : dp.getDataType().getFields()) {
                                    sumSteps += dp.getValue(field).asInt();
                                }
                            }

                            DataSet distanceDataset = response.getDataSet(DataType.TYPE_DISTANCE_DELTA);
                            for (DataPoint dp : distanceDataset.getDataPoints()) {
                                for (Field field : dp.getDataType().getFields()) {
                                    sumDistance += dp.getValue(field).asFloat();
                                }
                            }

                            DataSet kcalDataset = response.getDataSet(DataType.TYPE_CALORIES_EXPENDED);
                            for (DataPoint dp : kcalDataset.getDataPoints()) {
                                for (Field field : dp.getDataType().getFields()) {
                                    sumKCal += dp.getValue(field).asFloat();
                                }
                            }

                            Log.i(TAG,"totalsteps :" + totalSteps + "  totalDistance : " + totalDistance+ "  totalKCal : " + totalKCal);
                            totalSteps = sumSteps;
                            totalDistance = sumDistance;
                            totalKCal = sumKCal;
                            if(messenger != null) {
                                Message message = Message.obtain();
                                message.obj = StepService.totalSteps + "/" + (StepService.totalDistance / 1000);

                                try {
                                    messenger.send(message);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
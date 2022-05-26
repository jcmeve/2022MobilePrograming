package com.example.mysprout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class StepTest extends AppCompatActivity {
    final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    FitnessOptions fitnessOptions;
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_test);
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .addDataType(DataType.TYPE_DISTANCE_DELTA)
                .build();

/*        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
        }*/
        if(!GoogleSignIn.hasPermissions(account, fitnessOptions)){
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    account,
                    fitnessOptions
                    );
            Log.i("GIGIGIGI","sadfasfasf");
        }
        else{
            accessGoogleFit();
        }





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();

                readData();
            }

        }
    }
    private void readData() {
        final Calendar cal = Calendar.getInstance();
        Date now = Calendar.getInstance().getTime();
        cal.setTime(now);

        // 시작 시간
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 6, 0, 0);
        long startTime = cal.getTimeInMillis();

        // 종료 시간
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 23, 0, 0);
        long endTime = cal.getTimeInMillis();
        String TAG = "GOOGLE FIT";
        Fitness.getHistoryClient(this,
                GoogleSignIn.getLastSignedInAccount(this))
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA) // Raw 걸음 수
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build())
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse response) {
                        DataSet dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
                        Log.i(TAG, dataSet.getDataPoints().size()+"");
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            Log.i(TAG, "Data point:");
                            Log.i(TAG, "\tType: " + dp.getDataType().getName());

                            Log.i(TAG, "\tStart: " +  (dp.getStartTime(TimeUnit.MILLISECONDS) / 1000));
                            Log.i(TAG, "\tEnd: " + (dp.getEndTime(TimeUnit.MILLISECONDS)/ 1000));
                            for (Field field : dp.getDataType().getFields()) {
                                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                            }
                        }
                    }
                });
    }

    private void accessGoogleFit() {

        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnCompleteListener(
                        var -> Log.i("SUCCESS", "Successfully subscribed!")
                ).addOnFailureListener(
                    e -> Log.w("ERROR", "There was a problem subscribing.", e)
                );

    }
}
package com.example.mysprout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;

public class MainActivity extends AppCompatActivity {
    final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    FitnessOptions fitnessOptions;
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());

            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GoogleFitInit();

        tryAutoLogin();

        setContentView(R.layout.activity_main);
    }




    private void GoogleFitInit() {

        account =  GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .addDataType(DataType.TYPE_DISTANCE_DELTA)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                .build();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
        }
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

    private void accessGoogleFit() {

        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(getApplicationContext(), fitnessOptions))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnCompleteListener(
                        var -> Log.i("SUCCESS", "Successfully subscribed!")
                ).addOnFailureListener(
                e -> Log.w("ERROR", "There was a problem subscribing.", e)
        );
        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(getApplicationContext(), fitnessOptions))
                .subscribe(DataType.TYPE_DISTANCE_DELTA)
                .addOnCompleteListener(
                        var -> Log.i("SUCCESS", "Successfully subscribed!")
                ).addOnFailureListener(
                e -> Log.w("ERROR", "There was a problem subscribing.", e)
        );
        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(getApplicationContext(), fitnessOptions))
                .subscribe(DataType.TYPE_CALORIES_EXPENDED)
                .addOnCompleteListener(
                        var -> Log.i("SUCCESS", "Successfully subscribed!")
                ).addOnFailureListener(
                e -> Log.w("ERROR", "There was a problem subscribing.", e)
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();

                //    readData();
            }

        }
    }









    public void tryAutoLogin(){
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes);
                    _tryAuroLogin();
                },
                error -> Log.i("AuthDemo", "Failed to fetch user attributes.", error)
        );


    }
    private void _tryAuroLogin(){
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i("AmplifyQuickstart", result.toString());

                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                },
                error -> Log.i("AmplifyQuickstart", error.toString())
        );


    }


    //남은수
    public void onClick1(View v) {
        Intent intent = new Intent(getApplicationContext(), UserInformation.class);
        startActivity(intent);

    }

    //남은수
    public void onClick2(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
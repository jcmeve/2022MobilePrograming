package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class MainActivity extends AppCompatActivity {

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


        tryAutoLogin();


        setContentView(R.layout.activity_main);
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

                    Intent intent = new Intent(MainActivity.this, MainSprout.class);
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
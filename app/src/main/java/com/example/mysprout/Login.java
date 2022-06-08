package com.example.mysprout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button nextBtn;
        nextBtn = findViewById(R.id.nextbtn_login);

        findViewById(R.id.login_to_userinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, UserInformation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        nextBtn.setOnClickListener(v -> tryLogin());


    }

    private void tryLogin(){
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();


        Amplify.Auth.signIn(email_txt,password_txt,
                result-> {
                    Log.i("AuthQuickstart", result.isSignInComplete()?"Sign in succeeded": "Sign in not complete");
                    Log.i("asdfasdfasdf", result.toString());
                    loginProgress(result.isSignInComplete());
                },
                error-> {
                    if(!(error.getClass() == AuthException.InvalidParameterException.class))
                        Log.e("AuthQuickstart",error.toString());
                    loginProgress(false);
                }
        );

    }




    private void loginProgress(boolean result){
        if(result) {

            Intent intent = new Intent(Login.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            runOnUiThread(()->Toast.makeText(getBaseContext(),"로그인에 실패하였습니다.",Toast.LENGTH_LONG).show());

        }

    }

}

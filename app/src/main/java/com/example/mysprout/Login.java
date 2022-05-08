package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button nextBtn;
        nextBtn = findViewById(R.id.nextbtn_login);



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


            Intent intent = new Intent(Login.this, sprout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            runOnUiThread(()->Toast.makeText(getBaseContext(),"로그인에 실패하였습니다.",Toast.LENGTH_LONG).show());

        }

    }

}

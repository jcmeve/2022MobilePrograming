package com.example.mysprout;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;

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

        Amplify.Auth.signOut(
                () -> Log.i("Auth" , "signout Succ"),
                value -> Log.e("AuthQuickstart",value.toString())

        );

        Amplify.Auth.signIn(email_txt,password_txt,
                result-> {
                    Log.i("AuthQuickstart", result.isSignInComplete()?"Sign in succeeded": "Sign in not complete");
                    if(!result.isSignInComplete()){
                        Toast.makeText(Login.this,"로그인에 실패하였습니다.",Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(Login.this, sprout.class);
                        startActivity(intent);
                    }
                },
                error-> {
                    Log.e("AuthQuickstart",error.toString());
                    Toast.makeText(Login.this,"로그인에 실패하였습니다.",Toast.LENGTH_LONG).show();
                }
        );





    }

}

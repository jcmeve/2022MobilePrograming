package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class userinformation extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation);


        Button nextBtn;
        nextBtn = findViewById(R.id.nextbtn_start);
        nextBtn.setOnClickListener(v -> {
            EditText email = findViewById(R.id.input_email);
            EditText password = findViewById(R.id.input_password);
            EditText password_check = findViewById(R.id.input_password_check);
            EditText nickname = findViewById(R.id.input_yourname);

            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();
            String password_check_txt = password_check.getText().toString();
            String nickname_txt = nickname.getText().toString();
            String birthday_txt = "1999.01.01";
            if(password_txt.compareTo(password_check_txt) != 0){
                Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                return;
            }


            AuthSignUpOptions.Builder<?> builder = AuthSignUpOptions.builder();
            builder.userAttribute(AuthUserAttributeKey.email(), email.getText().toString());
            AuthUserAttribute[] authUserAttributes = {
                    new AuthUserAttribute(AuthUserAttributeKey.email(), email_txt),
                    new AuthUserAttribute(AuthUserAttributeKey.nickname(), nickname_txt),
                    new AuthUserAttribute(AuthUserAttributeKey.birthdate(), birthday_txt)
            };
            builder.userAttributes(Arrays.asList(authUserAttributes));
            AuthSignUpOptions options = builder.build();

            Amplify.Auth.signUp(email_txt,password_txt,options,
                result -> Log.i("AuthQuickStart", "Result: " + result),
                error->Log.e("AuthQuickStart", "Sign up failed", error)
            );

            Intent intent = new Intent(userinformation.this, userinformation_2.class);
            startActivity(intent);
        });




    }
}
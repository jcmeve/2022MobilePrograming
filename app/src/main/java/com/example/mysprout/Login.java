package com.example.mysprout;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_login);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Login.this, sprout.class);
                startActivity(intent);
            }
        });
    }
}

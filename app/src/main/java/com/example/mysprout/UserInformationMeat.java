package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformationMeat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_meat);

        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_meat);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(UserInformationMeat.this, UserInformationTransport.class);
                startActivity(intent);
            }
        });
        //TODO: MeatLevelData table을 받아 SetMeatCarbon로 탄소량 저장
    }
}
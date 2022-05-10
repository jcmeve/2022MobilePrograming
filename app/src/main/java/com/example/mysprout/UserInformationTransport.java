package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformationTransport extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_transport);

        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_trans);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(UserInformationTransport.this, MainSprout.class);
                startActivity(intent);
            }
        });
        //TODO: GetTransportationList를 받아 탄소소비량을 계산해 SetTransportationCarbon로 탄소량 저장
    }
}

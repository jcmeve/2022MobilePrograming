package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class userinformation_3 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_3);

        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_trans);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(userinformation_3.this, sprout.class);
                startActivity(intent);
            }
        });
    }
}

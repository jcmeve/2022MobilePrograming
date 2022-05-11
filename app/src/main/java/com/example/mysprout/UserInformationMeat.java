package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.MeatLevelData;

public class UserInformationMeat extends AppCompatActivity {
    private enum MEAT_LEVEL{
        HIGH,LOW,NO,NULL
    }

    static MEAT_LEVEL meatLevel = MEAT_LEVEL.NULL;
    static MeatLevelData meatLevelData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_meat);

        DB.getInstance().msGetMeatLevelData(data -> UserInformationMeat.meatLevelData = data);

        RadioGroup radioGroup = findViewById(R.id.meat_radio_group);
        radioGroup.setOnCheckedChangeListener((radioGroup1, id) -> {
            if (id == R.id.highMeat_btn) {
                UserInformationMeat.meatLevel = MEAT_LEVEL.HIGH;
            } else if (id == R.id.lowMeat_btn) {
                UserInformationMeat.meatLevel = MEAT_LEVEL.LOW;
            } else if (id == R.id.noMeat_btn) {
                UserInformationMeat.meatLevel = MEAT_LEVEL.NO;
            } else {
                Log.e("ERROR", "SELECT MEAT");
            }
        });

        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_meat);
        nextBtn.setOnClickListener(v -> {
            switch (meatLevel){
                case HIGH:
                    DB.getInstance().SetMeatCarbon(meatLevelData.getHighCarbon());
                    break;
                case LOW:
                    DB.getInstance().SetMeatCarbon(meatLevelData.getLowCarbon());
                    break;
                case NO:
                    DB.getInstance().SetMeatCarbon(meatLevelData.getNoCarbon());
                    break;
                default:
                    Log.e("ERROR", "SELECT MEAT");
            }


            Intent intent = new Intent(UserInformationMeat.this, UserInformationTransport.class);
            startActivity(intent);
        });
    }


    public void onClick_u_m_back(View v) {
        finish();
    }
}

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

    static MeatLevelData meatLevelData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_meat);

        DB.getInstance().msGetMeatLevelData(data -> UserInformationMeat.meatLevelData = data);


        Button nextBtn;
        nextBtn = (Button)findViewById(R.id.nextbtn_meat);
        nextBtn.setOnClickListener(v -> {
            RadioGroup radioGroup = findViewById(R.id.meat_radio_group);
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.highMeat_btn) {
                DB.getInstance().SetMeatCarbon(meatLevelData.getHighCarbon());
            } else if (checkedRadioButtonId == R.id.lowMeat_btn) {
                DB.getInstance().SetMeatCarbon(meatLevelData.getLowCarbon());
            } else if (checkedRadioButtonId == R.id.noMeat_btn) {
                DB.getInstance().SetMeatCarbon(meatLevelData.getNoCarbon());
            } else {
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

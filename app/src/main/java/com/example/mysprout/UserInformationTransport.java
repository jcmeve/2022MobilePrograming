package com.example.mysprout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysprout.fragment.DialogMovementAmount;

public class UserInformationTransport extends AppCompatActivity implements DialogMovementAmount.MovementAmountDataListener {
    int moveAmount; //사용자 이동량 저장
    String transportationName; //이동수단 이름 저장
    TextView resultText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation_transport);

        moveAmount = -1;
        transportationName = null;

        resultText = findViewById(R.id.userinfo_transport_text_result);
        resultText.setVisibility(View.INVISIBLE);

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

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.userinfo_transport_car:
                if(checked){
                    transportationName = "자동차";
                    setAmount("CAR", view);
                }
                break;
            case R.id.userinfo_transport_bus:
                if(checked){
                    transportationName = "버스";
                    setAmount("BUS", view);
                }
                break;
            case R.id.userinfo_transport_subway:
                if(checked){
                    transportationName = "지하철";
                    setAmount("SUBWAY", view);
                }
                break;
        }
    }

    void setAmount(String tag, View view){
        DialogMovementAmount dialogMovementAmount = new DialogMovementAmount();
        dialogMovementAmount.setMovementAmountDataListener(new DialogMovementAmount.MovementAmountDataListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void setMovementAmount(int amount) {
                //amount에 이동량 저장되어있음
                moveAmount = amount;

                if(moveAmount == -1){
                    Log.d("moveAmount", String.valueOf(moveAmount));
                    transportationName = null;
                    resultText.setVisibility(View.INVISIBLE);
                    ((RadioButton) view).setChecked(false);
                }else{
                    resultText.setVisibility(View.VISIBLE);
                    resultText.setText(tag + " / 평균 "+moveAmount+"km");
                }
            }
        });

        dialogMovementAmount.show(getSupportFragmentManager(), "MovementAmountDialog");
    }


    public void onClick_u_t_back(View v) {
        finish();
    }

    @Override
    public void setMovementAmount(int amount) {

    }
}

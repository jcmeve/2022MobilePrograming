package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class sprout extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprout);
    }

    //팝업메뉴
    public void onClick4(View button4) {
        PopupMenu popup = new PopupMenu(this, button4);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        popup.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_menu1) {
                            Toast.makeText(sprout.this, "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), nickname.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.action_menu2) {
                            Toast.makeText(sprout.this, "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), mysprout.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(sprout.this, "메뉴 3 클릭", Toast.LENGTH_SHORT).show();

                        }
                        return false;
                    }
                });
        popup.show();
    }



    public void onClick3(View v) {
        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);
        Intent intent = new Intent(getApplicationContext(), sprout2.class);
        startActivity(intent);
    }



}

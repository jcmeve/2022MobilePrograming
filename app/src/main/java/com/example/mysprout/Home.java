package com.example.mysprout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mysprout.databinding.ActivityHomeBinding;
import com.example.mysprout.fragment.MainSproutFragment;
import com.example.mysprout.fragment.MyPageFragment;
import com.example.mysprout.fragment.RecycleInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainSproutFragment fragmentMain = new MainSproutFragment();
    private MyPageFragment fragmentMyPage = new MyPageFragment();
    private RecycleInfoFragment fragmentRecycle = new RecycleInfoFragment();

    //여기선 뷰 바인딩 사용
    ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragmentMain).commitAllowingStateLoss();

        homeBinding.homeNavigationView.setOnItemSelectedListener(new ItemSelectedListener());
        homeBinding.homeNavigationView.setSelectedItemId(R.id.navigtion_button_to_home);
        //homeBinding.homeNavigationView.findViewById(R.id.navigtion_button_to_home);
    }

    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigtion_button_to_home:
                    transaction.replace(R.id.home_frame_layout, fragmentMain).commitAllowingStateLoss();
                    homeBinding.homeTopAppBar.setTitle("MySprout");
                    break;
                case R.id.navigtion_button_to_my_page:
                    transaction.replace(R.id.home_frame_layout, fragmentMyPage).commitAllowingStateLoss();
                    homeBinding.homeTopAppBar.setTitle("내 페이지");
                    break;
                case R.id.navigtion_button_to_recycle:
                    transaction.replace(R.id.home_frame_layout, fragmentRecycle).commitAllowingStateLoss();
                    homeBinding.homeTopAppBar.setTitle("분리배출 요령");
                    break;
            }
            return true;
        }
    }

    public void onClick_home_back(View v) {
        finish();
    }

}
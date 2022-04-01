package com.example.mysprout;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import Game.GameGLSurfaceView;

public class GameActivity extends AppCompatActivity {

    private GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new GameGLSurfaceView(this);
        setContentView(glView);


    }
}
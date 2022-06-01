package com.example.mysprout.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mysprout.R;

import java.util.Objects;

public class DialogRecycleInfo extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_recycle_info, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        //type: paper, can, glass, petandplastic, vinyl, styrofoam
        String type = null;
        if (bundle != null) {
            type = bundle.getString("type");
        }
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WebView webView = view.findViewById(R.id.dialog_recycle_info_web_view);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);

        webView.loadUrl("file:///android_asset/recycleInfo_paper.html");
        //로컬 html 파일


        ImageButton exitButton = view.findViewById(R.id.dialog_recycle_info_button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

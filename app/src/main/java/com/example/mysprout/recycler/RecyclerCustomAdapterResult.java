package com.example.mysprout.recycler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCustomAdapterResult
                extends RecyclerView.Adapter<RecyclerCustomAdapterResult.ViewHolder> {
    Context context;
    ArrayList Datas; //데이터 리스트
    int itemResult_layout;

    public RecyclerCustomAdapterResult(Context context, ArrayList datas, int itemResult_layout) {
        this.context = context;
        Datas = datas;
        this.itemResult_layout = itemResult_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public int getItemCount(){
        return Datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView){
            super(itemView);
        }

    }
}

package com.example.mysprout.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysprout.data.FoodPassData;
import com.example.mysprout.databinding.RecyclerItemviewResultBinding;

import java.util.ArrayList;

public class RecyclerCustomAdapterResult
                extends RecyclerView.Adapter<RecyclerCustomAdapterResult.ViewHolder> {
    Context context;
    ArrayList datas; //데이터 리스트
    int itemResult_layout;
    String tag;
    int givenSize;

    public RecyclerCustomAdapterResult(Context context, String tag, int itemResult_layout, int size) {
        this.context = context;
        this.tag = tag;
        this.itemResult_layout = itemResult_layout;
        this.givenSize = size;
    }

    public void setFoodDatas(ArrayList<FoodPassData> foods){
        datas = new ArrayList<FoodPassData>(foods);
    }

    public void setHabitDatas(ArrayList<RecyclerItemHabit> habits){
        datas = new ArrayList<RecyclerItemHabit>(habits);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position){
        datas.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemResult_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(tag.equals("Habits")){
            holder.bindItem((RecyclerItemHabit) datas.get(position), position);
        }else if(tag.equals("Food")){
            holder.bindItem((FoodPassData) datas.get(position), position);
        }
    }

    public int getItemCount(){
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerItemviewResultBinding itemviewResultBinding;

        public ViewHolder(View itemView){
            super(itemView);
            itemviewResultBinding = RecyclerItemviewResultBinding.bind(itemView);
        }

        void bindItem(FoodPassData item, final int pos){

            itemviewResultBinding.constraintlayoutResultItem.setMinWidth(givenSize - 160); //임시방편
            itemviewResultBinding.textviewResultItem.setText(item.getItem().getName());

            if(resultItemListener != null){
                itemviewResultBinding.buttonResultItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeItem(pos);
                        resultItemListener.onItemButtonClicked(view, pos, item);
                    }
                });
            }
        }

        void bindItem(RecyclerItemHabit item, final int pos){
            itemviewResultBinding.constraintlayoutResultItem.setMinWidth(givenSize - 160);
            itemviewResultBinding.textviewResultItem.setTextSize(12);
            itemviewResultBinding.textviewResultItem.setText(item.getName());

            if(resultItemListener != null){
                itemviewResultBinding.buttonResultItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeItem(pos);
                        resultItemListener.onItemButtonClicked(view, pos, item);
                    }
                });
            }
        }

    }

    private OnResultItemListener resultItemListener;
    public void setOnResultItemListener(OnResultItemListener listener){
        this.resultItemListener = listener;
    }

    public interface OnResultItemListener{
        void onItemButtonClicked(View view, int position, FoodPassData itemFood);
        void onItemButtonClicked(View view, int position, RecyclerItemHabit itemHabit);
    }
}

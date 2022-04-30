package com.example.mysprout.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysprout.R;

import java.util.List;

public class RecyclerCustomAdapterHabit
            extends RecyclerView.Adapter<RecyclerCustomAdapterHabit.ViewHolder> {
    Context context;
    List<RecyclerItemHabit> itemViewHabits; //데이터 리스트
    int itemHabit_layout; //아이템뷰 레이아웃

    public RecyclerCustomAdapterHabit(Context context, List<RecyclerItemHabit> itemViewHabits, int itemHabit_layout) {
        this.context = context;
        this.itemViewHabits = itemViewHabits;
        this.itemHabit_layout = itemHabit_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemHabit_layout, null);
        return new ViewHolder(view);
    }

    //항목 구성 메소드, 내부 뷰에 데이터 세팅
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //ArrayList에서 뷰에 나타낼 데이터 찾아옴
        RecyclerItemHabit item = itemViewHabits.get(position);
        holder.radioButton_habit.setText(item.getTitle()); //라디오버튼 텍스트

        /*holder.radioButton_habit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                if(bool){
                    selectedPosition = holder.getAdapterPosition(); //취소선...
                }
            }
        });*/
    }

    //출력할 데이터 개수 -어댑터가 반환할 아이템 개수-
    @Override
    public int getItemCount() {
        return this.itemViewHabits.size();
    }

    //아이템뷰를 담을 객체, 구성 위젯 연결
    public class ViewHolder extends RecyclerView.ViewHolder{
        //ImageView imageView_sort;
        RadioButton radioButton_habit;

        public ViewHolder(View itemView){
            super(itemView);
            //imageView_sort = itemView.findViewById(R.id.imageView_itemview_habit);
            radioButton_habit = itemView.findViewById(R.id.radioButton_itemview_habit);
        }
    }
}

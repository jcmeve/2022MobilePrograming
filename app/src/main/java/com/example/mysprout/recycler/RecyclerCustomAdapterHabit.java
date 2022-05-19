package com.example.mysprout.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysprout.data.CheckboxData;
import com.example.mysprout.databinding.RecyclerItemviewHabitBinding;

import java.util.ArrayList;

//뷰 바인딩 사용
public class RecyclerCustomAdapterHabit
            extends RecyclerView.Adapter<RecyclerCustomAdapterHabit.ViewHolder> {
    Context context;
    ArrayList<RecyclerItemHabit> habitDatas; //데이터 리스트
    int itemHabit_layout; //아이템뷰 레이아웃

    ArrayList<CheckboxData> checkboxList;

    //ArrayList<RecyclerItemHabit> checkedItems;

    public RecyclerCustomAdapterHabit(Context context, ArrayList<RecyclerItemHabit> habitDatas,
                                      int itemHabit_layout) {
        this.context = context;
        this.habitDatas = habitDatas;
        this.itemHabit_layout = itemHabit_layout;
        this.checkboxList = new ArrayList<>();
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
        holder.bindItem(habitDatas.get(position), position);

        //Log.d("BindViewHolder", String.valueOf(position));
        //Log.d("BindViewHolder", String.valueOf(holder.getItemId()));
        }

    //출력할 데이터 개수 -어댑터가 반환할 아이템 개수-
    @Override
    public int getItemCount() {
        return this.habitDatas.size();
    }

    //아이템뷰를 담을 객체, 구성 위젯 연결
    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerItemviewHabitBinding itemHabitBinding; //뷰 바인딩 사용
        //ImageView imageView_sort;
        //RadioButton radioButton_habit;

        public ViewHolder(View itemView){
            super(itemView);
            itemHabitBinding = RecyclerItemviewHabitBinding.bind(itemView);
        }

        void bindItem(RecyclerItemHabit item, final int pos){
            itemHabitBinding.checkboxItemviewHabit.setText(item.getName());

            if(pos >= checkboxList.size())
                checkboxList.add(pos, new CheckboxData(itemHabitBinding.checkboxItemviewHabit.getId()+pos, false));

            //Log.d("bindItem", String.valueOf(itemHabitBinding.checkboxItemviewHabit.getId()+pos));

            itemHabitBinding.checkboxItemviewHabit.setChecked(checkboxList.get(pos).getChecked());

            itemHabitBinding.checkboxItemviewHabit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemHabitBinding.checkboxItemviewHabit.isChecked()){
                        checkboxList.get(pos).setChecked(true);
                        actionItemListener.onItemChecked("TRUE", item);
                        //checkedItems.add(item);
                    }else{
                        checkboxList.get(pos).setChecked(false);
                        actionItemListener.onItemChecked("FALSE", item);
                        //checkedItems.remove(item);
                    }
                }
            });

            //Log.d("bindItem", String.valueOf(checkedItems));
        }
    }

    private OnActionItemListener actionItemListener;
    public void setOnActionItemListener(OnActionItemListener listner){
        this.actionItemListener = listner;
    }

    public interface OnActionItemListener{
        void onItemChecked(String tag, RecyclerItemHabit item);
    }

    /*public ArrayList<RecyclerItemHabit> getCheckedItems(){
        return checkedItems;
    }*/
}

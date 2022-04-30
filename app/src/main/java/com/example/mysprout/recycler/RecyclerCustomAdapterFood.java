package com.example.mysprout.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class RecyclerCustomAdapterFood
            extends RecyclerView.Adapter<RecyclerCustomAdapterFood.ViewHolder> implements Filterable {
    Context context;
    List<RecyclerItemFood> foods; //데이터
    List<RecyclerItemFood> foodsAll; //기존 데이터
    int item_layout;

    public RecyclerCustomAdapterFood(Context context, List<RecyclerItemFood> foods, int item_layout) {
        this.context = context;
        this.foods = foods;
        this.foodsAll = foods;
        this.item_layout = item_layout;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void dataSetChanged(List<RecyclerItemFood> list) {
        foods = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(item_layout, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItemFood food = foods.get(position);//리스트에서 데이터 가져와서
        holder.foodName.setText(food.getFoodName());//뷰홀더에 표시

        //여기 리스너 정의
    }

    @Override
    public int getItemCount() {
        return this.foods.size();
    }

    //데이터 필터
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    private Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RecyclerItemFood> filteredList = new ArrayList<>();
            //Log.d("filter", (String) charSequence);
            //Log.d("List", foods.get(0).getFoodName());

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(foodsAll);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                //Log.d("filter", filterPattern);
                for(RecyclerItemFood food : foodsAll){
                    //Log.d("for", String.valueOf(food.getFoodName().contains(filterPattern)));
                    //filter 대상 세팅
                    if(food.getFoodName().toLowerCase().contains(filterPattern)){
                        //Log.d("filter", "필터리스트 추가");
                        filteredList.add(food);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            foods.clear();
            foods.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodName;

        public ViewHolder(View itemView){
            super(itemView);
            //임시 아이템뷰
            foodName = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface onItemListener{
        void onItemClicked(int position);
    }

}

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

import com.example.mysprout.databinding.RecyclerItemviewFoodBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class RecyclerCustomAdapterFood
            extends RecyclerView.Adapter<RecyclerCustomAdapterFood.ViewHolder> implements Filterable {
    Context context;
    ArrayList<RecyclerItemFood> foods; //데이터
    ArrayList<RecyclerItemFood> foodsAll; //기존 데이터
    int itemFood_layout;

    public RecyclerCustomAdapterFood(Context context, ArrayList<RecyclerItemFood> foods, int item_layout) {
        this.context = context;
        this.foods = foods;
        this.foodsAll = new ArrayList<>(foods);
        this.itemFood_layout = item_layout;
    }

    //interface 클릭 인터페이스
    private OnItemListener itemListener;
    public void setOnItemListner(OnItemListener listner){
        this.itemListener = listner;
    }

    //검색할 때마다 데이터 바뀜 -foods만 바뀌고 foodsAll은 고정-
    @SuppressLint("NotifyDataSetChanged")
    public void dataSetChanged(ArrayList<RecyclerItemFood> list) {
        foods = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemFood_layout, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(foods.get(position), position);
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
                    if(food.getFoodName().toLowerCase().trim().contains(filterPattern)){
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

            if(filterResults.values != null)
                foods.addAll((ArrayList)filterResults.values);

            notifyDataSetChanged();
        }
    };

    //뷰 바인딩 사용
    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerItemviewFoodBinding itemFoodBinding;
        //TextView foodName;

        public ViewHolder(View itemView){
            super(itemView);
            itemFoodBinding = RecyclerItemviewFoodBinding.bind(itemView);
        }

        void bindItem(RecyclerItemFood item, final int pos){
            itemFoodBinding.textviewFoodname.setText(item.getFoodName());

            if(itemListener != null){
                itemFoodBinding.textviewFoodname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemListener.onItemClicked(view, pos, item);
                    }
                });
            }
        }
    }
    
    //interface - 내부 선언, 외부에서 접근 가능하도록 setOnClickListener
    //bindItem에서 처리 중
    public interface OnItemListener{
        void onItemClicked(View view, int position, RecyclerItemFood item);
    }

}

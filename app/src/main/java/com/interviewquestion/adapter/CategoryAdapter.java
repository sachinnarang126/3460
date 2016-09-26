package com.interviewquestion.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interviewquestion.R;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<String> categoryList;

    public CategoryAdapter(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.txtCategory.setText(categoryList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
        }
    }


}

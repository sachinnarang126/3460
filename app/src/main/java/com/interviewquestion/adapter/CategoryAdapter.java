package com.interviewquestion.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interviewquestion.R;
import com.interviewquestion.fragment.CategoryFragment;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<String> categoryList;
    private CategoryFragment categoryFragment;

    public CategoryAdapter(List<String> categoryList, CategoryFragment categoryFragment) {
        this.categoryList = categoryList;
        this.categoryFragment = categoryFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.txtCategory.setText(categoryList.get(position));

            holder.txtCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryFragment.goToQuestionActivity(position, categoryList.get(position));
                }
            });
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

package com.interviewquestion.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.interviewquestion.R;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interfaces.OnItemClickListener;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<String> categoryList;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private int lastPosition = -1;
    private Context context;

    public CategoryAdapter(List<String> categoryList, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.categoryList = categoryList;
        this.onItemClickCallback = onItemClickCallback;
        context = ((CategoryFragment) onItemClickCallback).getActivity();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.txtCategory.setText(categoryList.get(position));
            holder.txtCategory.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
//            setAnimation(holder.container, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategory;
        LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            container = (LinearLayout) itemView.findViewById(R.id.container);
        }
    }

}

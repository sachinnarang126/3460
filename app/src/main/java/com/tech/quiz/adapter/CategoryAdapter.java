package com.tech.quiz.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.interfaces.OnItemClickListener;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Sachin Narang
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Map<String, Integer> categoryMap;
    private List<String> categoryList;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private int lastPosition = -1;

    public CategoryAdapter(List<String> categoryList, Map<String, Integer> categoryMap, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.categoryList = categoryList;
        this.onItemClickCallback = onItemClickCallback;
        this.categoryMap = categoryMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            String data = categoryList.get(position) + " (" + categoryMap.get(categoryList.get(position)) + ")";
            holder.txtCategory.setText(data);
            holder.txtCategory.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
            setAnimation(holder.container, position);
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
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategory;
        CardView container;

        ViewHolder(View itemView) {
            super(itemView);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            container = (CardView) itemView.findViewById(R.id.container);
        }
    }

}

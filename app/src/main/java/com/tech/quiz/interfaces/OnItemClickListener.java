package com.tech.quiz.interfaces;

import android.view.View;

/**
 * @author Sachin Narang
 */

public class OnItemClickListener implements View.OnClickListener {

    private final int position;
    private final OnItemClickCallback onItemClickCallback;

    public OnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}

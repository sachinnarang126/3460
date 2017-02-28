package com.tech.quiz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.interfaces.OnItemClickListener;
import com.tech.quiz.models.bean.Discussion;

import java.util.List;

/**
 * @author Sachin Narang
 */

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

    private final List<Discussion> discussionList;
    private final OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public DiscussionAdapter(List<Discussion> discussionList, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.discussionList = discussionList;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_discussion, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
//            holder.txtQuestion.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion, txtAnswer;
        private LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) itemView.findViewById(R.id.txtAnswer);
            container = (LinearLayout) itemView.findViewById(R.id.container);
        }
    }
}

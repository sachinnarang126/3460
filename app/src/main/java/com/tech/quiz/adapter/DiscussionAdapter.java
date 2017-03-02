package com.tech.quiz.adapter;

import android.content.Context;
import android.graphics.Typeface;
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

    private final List<Discussion.Response> discussionList;
    private final OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private boolean isSubScribedUser;
    private Context context;

    public DiscussionAdapter(List<Discussion.Response> discussionList, boolean isSubScribedUser, Context context,
                             OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.discussionList = discussionList;
        this.onItemClickCallback = onItemClickCallback;
        this.isSubScribedUser = isSubScribedUser;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_discussion, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            Discussion.Response response = discussionList.get(position);
            String question = context.getString(R.string.question) + response.getQuestion();
            String technology = "Technology : " + response.getTechnology().toUpperCase();
            holder.txtQuestion.setText(question);
            holder.txtTechnology.setText(technology);
            if (response.getStatus().equalsIgnoreCase("1")) {
                if (isSubScribedUser) {
                    String answer = context.getString(R.string.answer) + response.getAnswer();
                    holder.txtAnswer.setText(answer);
                    holder.txtAnswer.setTypeface(null, Typeface.NORMAL);
                } else {
                    String paidAnswer = "This Answer is only for subscribed users";
                    holder.txtAnswer.setText(paidAnswer);
                    holder.txtAnswer.setTypeface(null, Typeface.BOLD_ITALIC);
                }
            } else {
                String answer = context.getString(R.string.answer) + response.getAnswer();
                holder.txtAnswer.setText(answer);
                holder.txtAnswer.setTypeface(null, Typeface.NORMAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtQuestion, txtAnswer, txtTechnology;
        private LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) itemView.findViewById(R.id.txtAnswer);
            txtTechnology = (TextView) itemView.findViewById(R.id.txtTechnology);
            container = (LinearLayout) itemView.findViewById(R.id.container);
            container.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onItemClickCallback.onItemClicked(v, getLayoutPosition());
        }
    }
}

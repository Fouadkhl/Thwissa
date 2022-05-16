package com.example.thwissa.fragment.entertainment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;


public class QuizPagerAdapter extends RecyclerView.Adapter<QuizPagerAdapter.ViewPagerViewHolder>{

    private int checkedPos;
    private final quiz[] quizzes = {
            new quiz(R.string.decide_where_you_want_to_go
            , R.string.option_1, R.string.option_2, R.string.option_3),
            new quiz(R.string.decide_where_you_want_to_go
                    , R.string.option_1, R.string.option_2, R.string.option_3)
    };

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_view_pager_layout,
            parent, false
        );
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        if(position != quizzes.length) {
            RadioButton[] radioButtons = {holder.itemView.findViewById(R.id.radioButton1)
                    , holder.itemView.findViewById(R.id.radioButton2), holder.itemView.findViewById(R.id.radioButton3)
            };
            TextView textView = holder.itemView.findViewById(R.id.question_text_view);

            radioButtons[1].setChecked(true);
            checkedPos = 1;

            for (int i = 0; i < 3; i++) {
                int finalI = i;
                radioButtons[i].setOnClickListener(view1 -> {
                    if (finalI != checkedPos) {
                        radioButtons[finalI].setChecked(true);
                        radioButtons[checkedPos].setChecked(false);
                        checkedPos = finalI;
                    }
                });
                radioButtons[i].setText(quizzes[position].options[i]);
            }

            textView.setText(quizzes[position].Question);
        } else {
            holder.itemView.findViewById(R.id.question_text_view).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.radioGroup).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.endView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return quizzes.length+1;
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

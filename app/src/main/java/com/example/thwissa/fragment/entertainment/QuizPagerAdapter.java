package com.example.thwissa.fragment.entertainment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;

import java.util.ArrayList;

public class QuizPagerAdapter extends RecyclerView.Adapter<QuizPagerAdapter.ViewPagerViewHolder>{

    private int checkedPos;
    private ArrayList<Quiz.innerQuiz> quizzes;

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
        if(position != quizzes.size()) {
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
                radioButtons[i].setText(quizzes.get(position).options[i]);
            }

            textView.setText(quizzes.get(position).question);
        } else {
            holder.itemView.findViewById(R.id.question_text_view).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.radioGroup).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.endView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return quizzes.size()+1;
    }

    public void setQuizzes(ArrayList<Quiz.innerQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

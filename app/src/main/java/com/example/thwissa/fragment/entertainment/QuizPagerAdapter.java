package com.example.thwissa.fragment.entertainment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thwissa.R;

import java.util.ArrayList;
import java.util.Collections;

public class QuizPagerAdapter extends RecyclerView.Adapter<QuizPagerAdapter.ViewPagerViewHolder>{

    private int checkedPos;
    private ArrayList<Quiz.innerQuiz> quizzes;
    private final ViewPager2 vp;
    private int trueAnswersCounter;
    private Quiz.innerQuiz curr;
    private RadioButton[] radioButtons;

    public QuizPagerAdapter(ViewPager2 vp){
        this.vp = vp;
    }

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
            curr = quizzes.get(holder.getAdapterPosition());
            radioButtons = new RadioButton[]{holder.itemView.findViewById(R.id.radioButton1)
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getAdapterPosition() == quizzes.size()){
                    Collections.shuffle(quizzes);
                    for(Quiz.innerQuiz q : quizzes){
                        q.shuffle();
                    }
                    vp.setCurrentItem(0);
                    trueAnswersCounter = 0;
                }
            }
        });
        if(position == getItemCount()-1){
            TextView tv = holder.itemView.findViewById(R.id.popoutText);
            tv.setText("number of true answers : "+this.trueAnswersCounter+tv.getText()+"\n");

        }
    }

    public void inc(boolean b){
        if(b) this.trueAnswersCounter++;
    }

    public int getCorrectAnswerPos(){
        return curr.trueAnswerPos;
    }

    public int selectedAnswerPos(){
        int cmp = 0;
        for(RadioButton rb : radioButtons) {
            if(rb.isChecked()) return cmp;
            cmp++;
        }
        return -1;
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

package com.example.thwissa.fragment.entertainment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopPicsRes {
    @SerializedName("PicturesOfTheWeek")
    public TopPic[] pictures;
    @SerializedName("Quize")
    public ArrayList<Quiz> quiz;

    public ArrayList<Quiz.innerQuiz> toInnerQuizList(){
        ArrayList<Quiz.innerQuiz> list = new ArrayList<>();
        for(Quiz q : quiz){
            list.add(new Quiz.innerQuiz(q.question, q.toArray()));
        }
        return  list;
    }
}

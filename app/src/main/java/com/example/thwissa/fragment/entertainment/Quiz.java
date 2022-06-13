package com.example.thwissa.fragment.entertainment;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz {
    @SerializedName("_id")
    public String _id;
    @SerializedName("question")
    public String question;
    @SerializedName("answer1")
    public String answer1;
    @SerializedName("answer2")
    public String answer2;
    @SerializedName("answer3")
    public String answer3;

    public String[] toArray(){
        List<String> strings = Arrays.asList(answer1, answer2, answer3);
        Collections.shuffle(strings, new Random(System.currentTimeMillis()));
        return new String[]{strings.get(0), strings.get(1), strings.get(2)};
    }

    public static class innerQuiz {
        public String question;
        public String[] options;

        public innerQuiz(String question, String[] options) {
            this.question = question;
            this.options = options;
        }
    }
}
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

    public tuple toArray(){
        String str = answer1;
        List<String> strings = Arrays.asList(answer1, answer2, answer3);
        Collections.shuffle(strings, new Random(System.currentTimeMillis()));
        return new tuple(new String[]{strings.get(0), strings.get(1), strings.get(2)},
                strings.indexOf(str)
                );
    }

    public static class innerQuiz {
        public String question;
        public String[] options;
        public int trueAnswerPos;

        public innerQuiz(String question, tuple t) {
            this.question = question;
            this.options = t.arr;
            this.trueAnswerPos = t.pos;
        }

        public void shuffle(){
            List<String> strings = Arrays.asList(options[0], options[1], options[2]);
            Collections.shuffle(strings, new Random(System.currentTimeMillis()));
            options[0] = strings.get(0);
            options[1] = strings.get(1);
            options[2] = strings.get(2);
        }
    }

    public static class tuple {
        public String[] arr;
        public int pos;

        public tuple(String[] arr, int pos) {
            this.arr = arr;
            this.pos = pos;
        }
    }
}
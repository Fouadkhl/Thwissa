package com.example.thwissa.fragment.entertainment;

public class quiz {
    int Question;
    int[] options = new int[3];

    quiz(){}
    quiz(int question, int option1, int option2, int option3){
        this.Question = question;
        this.options[0] = option1;
        this.options[1] = option2;
        this.options[2] = option3;
    }

}

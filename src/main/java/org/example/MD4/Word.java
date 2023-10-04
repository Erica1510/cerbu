package org.example.MD4;

import java.util.ArrayList;

public class Word {
    public ArrayList<Integer> word = new ArrayList<>();
    public Word(ArrayList word){
        this.word = word;
    }
    public Word(){};

    public ArrayList<Integer> not(){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < this.word.size(); i++) {
            if (this.word.get(i) == 0){
                result.add(1);
            }
            else result.add(0);
        }
        return result;
    }


    public static ArrayList<Integer> and(ArrayList<Integer> firstList,ArrayList<Integer> secondList){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            result.add(firstList.get(i) & secondList.get(i));
        }
        return result;
    }

    public static ArrayList<Integer> or(ArrayList<Integer> firstList,ArrayList<Integer> secondList){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            result.add(firstList.get(i) | secondList.get(i));
        }
        return result;
    }

    public static ArrayList<Integer> xor(ArrayList<Integer> firstList,ArrayList<Integer> secondList){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            result.add(firstList.get(i) ^ secondList.get(i));
        }
        return result;
    }

    //public static long getLong(){
        //long result = word.calc
    //}
    public long getLong(){
        long result = 0;
        for (int i = 0; i < this.word.size(); i++) {
            if (this.word.get(i) == 1){
                result += (long)Math.pow(2,31 - i);
            }
        }
        return result;
    }
    public ArrayList<Integer> rotateLeft(int s){
        ArrayList<Integer> result = new ArrayList<>();
        Integer temp;
        for (int i = 0; i < s; i++) {
            temp = word.get(0);
            for (int j = 0; j < word.size() - 1; j++) {
                word.set(j,word.get(j + 1));
            }
            word.set(word.size() - 1, temp);
        }
        return word;
    }

    public static Word create(ArrayList<Integer> arrayList){
        return new Word(arrayList);
    }


}

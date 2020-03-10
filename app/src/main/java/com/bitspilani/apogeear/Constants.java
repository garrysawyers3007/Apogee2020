package com.bitspilani.apogeear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    private static Map<String,String> chartointerest=new HashMap<>();
    private static ArrayList<String> Types=new ArrayList<>();

    public static Map<String,String> getChartointerest() {
        chartointerest.clear();
        chartointerest.put("The HackerMan","coding and fintech");
        chartointerest.put("Maestro","quizzing and strategy");

        return chartointerest;
    }

    public static ArrayList<String> getTypes() {
        Types.clear();
        Types.add("coding and fintech");
        Types.add("quizzing and strategy");
        return Types;
    }
}

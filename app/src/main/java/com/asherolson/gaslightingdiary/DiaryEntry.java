package com.asherolson.gaslightingdiary;

public class DiaryEntry {



    private final String text;
    private final String date;
    private final int id;

    public DiaryEntry(String rawText){
        //takes a string that came from shared pref and parses into fields,
        //<!9!8> arbitrary separator,
        //format of string will be:
        //(int id)<!9!8>(date)<!9!8>(text)
        String[] parts = rawText.split("<!9!8>");
        id = Integer.parseInt(parts[0]);
        date = parts[1];
        text = parts[2];
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

}

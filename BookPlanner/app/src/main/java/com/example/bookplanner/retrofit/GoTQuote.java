package com.example.bookplanner.retrofit;

public class GoTQuote {

    //isti nazivi promenljivih kao i na restu
    private String quote;
    private String character;

    public GoTQuote(String quote, String character) {
        this.quote = quote;
        this.character = character;
    }

    public String getQuote() {
        return quote;
    }

    public String getCharacter() {
        return character;
    }
}

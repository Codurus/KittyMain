package com.proworks.listenerengine;


public class SentenceAssembly {

    public static int Flag = 0;

    public String iceBreaker(String spokenText) {
        String statement = "";
        if (spokenText.equalsIgnoreCase("hi") || spokenText.equalsIgnoreCase("hello")) {
            statement = "Hi. I am Kitty. What's your name?";
            Flag = 1;
        } else if (spokenText.toLowerCase().contains("my name") || Flag == 1) {
            if (spokenText.toLowerCase().contains(" ")) {
                String name = spokenText.substring(spokenText.lastIndexOf(" ") + 1);
                statement = "Hi. " + name + ". Check me out. Ask Anything!";
            } else {
                statement = "Hi. " + spokenText + ". Check me out. Ask Anything!";
            }
            Flag = 0;
        }

        return statement;
    }
}

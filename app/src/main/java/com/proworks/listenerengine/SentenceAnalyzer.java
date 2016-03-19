package com.proworks.listenerengine;


public class SentenceAnalyzer {

    public static int stopFlag = 0;

    public String analyzeSentence(String spokenText) {
        String statement = "";


        if (spokenText.toLowerCase().contains("joke") || spokenText.toLowerCase().contains("laugh")) {

            statement = new GeneralSentence().humour();
        } else if (spokenText.toLowerCase().contains("going around")) {
            statement = "Well, today's weather is soothing at 24 degree celsius. Sensex is 67 points up. and you have 2 unread mails. What else do you want to know?";
        } else if (spokenText.toLowerCase().contains("weather")) {
            statement = "today's weather is soothing and good. It's 20 degree celsius. Go out and have fun.";
        } else if (spokenText.toLowerCase().contains("tongue twister")) {
            statement = "Sure, chandoo kay chaachaa nay chandoo ki chaachee ko chatnee chataayee.";
        } else if (spokenText.toLowerCase().contains("sms") || spokenText.toLowerCase().contains("message") || SendingSMS.Flag == 1 || SendingSMS.Flag == 2) {
            statement = new SendingSMS().sendSMS(spokenText);
        } else if (spokenText.equalsIgnoreCase("hello") || spokenText.equalsIgnoreCase("hi kitty") || spokenText.equalsIgnoreCase("hi") || spokenText.toLowerCase().contains("my name") || SentenceAssembly.Flag == 1) {

            statement = new SentenceAssembly().iceBreaker(spokenText);
        } else if (spokenText.toLowerCase().contains("rude")) {

            statement = "Sorry for that, what can i do to make it up to you?";
        } else if (spokenText.toLowerCase().contains("nothing") || spokenText.toLowerCase().contains("no") || spokenText.toLowerCase().contains("thanks")) {

            statement = "Okay. Press the hard button again if you need anything.";
            stopFlag = 1;
        } else {
            statement = "Please speak clearly. Don't blabber i am not human.";
        }
        return statement;

    }


}

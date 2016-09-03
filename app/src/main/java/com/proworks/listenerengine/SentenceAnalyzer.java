package com.proworks.listenerengine;

/**
 * Created by KHILADI on 06-06-2015.
 */
public class SentenceAnalyzer {


    public String analyzeSentence(String spokenText){
        String statement="";

        if(spokenText.toLowerCase().contains("joke")|| spokenText.toLowerCase().contains("laugh")){

            statement = new GeneralSentence().humour();
        }
        else
        if (spokenText.toLowerCase().contains("story")){
            statement="no story go to sleep";
        }
        else
        if(spokenText.equalsIgnoreCase("hello") || spokenText.equalsIgnoreCase("hi kitty") || spokenText.equalsIgnoreCase("hi") || spokenText.toLowerCase().contains("my name") || SentenceAssembly.Flag==1){

            statement = new SentenceAssembly().iceBreaker(spokenText);
        }
        else{
            statement = "Please speak clearly. Don't blabber i am not human.";
        }
        return statement;

    }
}

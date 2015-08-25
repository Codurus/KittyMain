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
        if (spokenText.toLowerCase().contains("sex story")){
            statement="ok commander as u say. One night. Anuraag was staring at my boobs. He stripped me down and tore my panties. I was all naked and moaning ahhh. " +
                    "ahhh. ouchhh. please slow down. Anuraag. But he didn't show any mercy and fucked me very hard.In my ass";
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

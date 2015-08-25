package com.proworks.listenerengine;

import java.util.Random;

/**
 * Created by KHILADI on 06-06-2015.
 */
public class GeneralSentence {
    private String statement;
    private Random random;

    public GeneralSentence(){
        statement = "";
        random = new Random();
    }

    public String general(){


        return null;
    }

    public String humour(){

        switch (random.nextInt(6)){
            case 0 : statement="see your face! That's a joke already.";
                break;
            case 1 : statement= "I wondered why the ball was getting bigger! Then it hit me.";
                break;
            case 2 : statement = "LOL, you have nothing to do right?";
                break;
            case 3 : statement= "Still a kid? grow up!";
                break;
            case 4 : statement= "you are asking a budget phone to crack a joke. That's already a joke and a height of stupidity";
                break;
            case 5 : statement = "why don't you bang your head in a wall and make me laugh?";
                break;
        }

        return statement;
    }
}

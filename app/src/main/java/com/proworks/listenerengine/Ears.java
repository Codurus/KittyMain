package com.proworks.listenerengine;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;

public class Ears extends ActionBarActivity implements
        TextToSpeech.OnInitListener{

    //TextToSpeech ttobj;
    TextToSpeech tts;

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String spokenText;
    private SentenceAnalyzer sentenceAnalyzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ears);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar
        getSupportActionBar().hide();

        //for listening
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        tts = new TextToSpeech(this, this);
        //for speaking
       /* ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });*/
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }



    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    spokenText = result.get(0);
                    txtSpeechInput.setText(spokenText);
                    try{

                       speakOut(spokenText);
                    }catch (Exception e){
                       e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Some error occurred can't speak",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

        }
    }

/*    public void speakText(){
        toSpeak = txtSpeechInput.getText().toString();
        if(toSpeak.equals("Hello")) {
            String statement = "Hi daddy how may i help you?";
            Toast.makeText(getApplicationContext(), statement,
                    Toast.LENGTH_SHORT).show();
            ttobj.speak(statement, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            String statement = "Sorry i could not get you. Please repeat aahhh.";
            Toast.makeText(getApplicationContext(), statement,
                    Toast.LENGTH_SHORT).show();
            ttobj.speak(statement, TextToSpeech.QUEUE_FLUSH, null);
        }
    }*/

  /*  public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }*/

    private void speakOut(String spokenText) {
        sentenceAnalyzer = new SentenceAnalyzer();
        //To analyze spokentText and generate response
        String statement = sentenceAnalyzer.analyzeSentence(spokenText);
        Toast.makeText(getApplicationContext(), statement,
                Toast.LENGTH_LONG).show();

        tts.speak(statement, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ears, menu);
        return true;
    }

}

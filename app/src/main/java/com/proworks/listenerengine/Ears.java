package com.proworks.listenerengine;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Ears extends ActionBarActivity implements
        TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private static final String TAG = Ears.class.getSimpleName();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    //TextToSpeech ttobj;
    TextToSpeech tts;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private String spokenText;
    private SentenceAnalyzer sentenceAnalyzer;
    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            Log.d(TAG, "PACKAGE " + pack);
            Log.d(TAG, "TITLE " + title);
            Log.d(TAG, "TEXT " + text);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ears);

        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ComponentName mReceiverComponent = new ComponentName(this, MyHeadsetButtonReceiver.class);

        mAudioManager.registerMediaButtonEventReceiver(mReceiverComponent);


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

        Bundle bundle = getIntent().getExtras();
        String value = "";
        if (bundle != null) {
            value = bundle.getString("BUTTON_PRESSED");
        }
        Log.d(TAG, "onCreate: VALUE FROM BRDCST :" + value);
        if (value.equalsIgnoreCase("true")) {
            SentenceAnalyzer.stopFlag = 0;
            promptSpeechInput();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    Log.d(TAG, "dispatchKeyEvent: key up");
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    SentenceAnalyzer.stopFlag = 0;
                    promptSpeechInput();
                    Log.d(TAG, "dispatchKeyEvent: key down");
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceCompletedListener(this);
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
     */
    public void promptSpeechInput() {

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
     */
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
                    try {

                        speakOut(spokenText);
                    } catch (Exception e) {
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
        HashMap<String, String> myHashAlarm = new HashMap<String, String>();
        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
        tts.speak(statement, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
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

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        Log.d(TAG, "speech finished");
        if (SentenceAnalyzer.stopFlag == 0) {
            promptSpeechInput();
        }

    }
}

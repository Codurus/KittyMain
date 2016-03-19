package com.proworks.listenerengine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;


public class MyHeadsetButtonReceiver extends BroadcastReceiver {
    public MyHeadsetButtonReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d("receiever", "onReceive: in broadcast");
        String intentAction = intent.getAction();
        Log.i("receiever", intentAction.toString() + " happended");
        //Bundle bundle = new Bundle();
        //bundle.putString("BUTTON_PRESSED","true");

        // ### put what ever you need into the bundle here ###

        //Intent newIntent = new Intent();

        Intent newIntent = new Intent(context, Ears.class);
        //newIntent.setClassName(context, "Ears.class");
        newIntent.putExtra("BUTTON_PRESSED", "true");
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

        if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            Log.i("receiever", "no media button information");
            return;
        }
        KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null) {
            Log.i("receiver", "no keypress");
            return;
        }

    }

}

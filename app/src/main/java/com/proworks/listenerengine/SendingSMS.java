package com.proworks.listenerengine;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Sharad on 04-03-2016.
 */
public class SendingSMS {
    private static final String TAG = SendingSMS.class.getSimpleName();
    public static int Flag = 0;
    public static String phoneNumber = "";
    String statement = "";

    public String sendSMS(String spokenText) {


        String content = "";
        if (spokenText.toLowerCase().contains("sms") || spokenText.toLowerCase().contains("message")) {
            statement = "Okay. What's the phone number?";
            Flag = 1;
        } else if (Flag == 1 && this.checkPhoneNumber(spokenText)) {
            statement = "and what's the message you have to send?";
            Flag = 2;
        } else if (Flag == 2) {
            content = spokenText;
            statement = sendMessage(content, phoneNumber);
            Flag = 0;
        } else {
            statement = "What do you want me to send? a SMS or email?";
            Flag = 0;
        }
        return statement;
    }

    public Boolean checkPhoneNumber(String phNo) {
        phoneNumber = phNo.replaceAll("\\s", "");

        Log.d(TAG, "checkPhoneNumber: " + phoneNumber);
        String regex = "[1-9][0-9]{9,14}";
        return phoneNumber.matches(regex);
    }

    public String sendMessage(String text, String phoneNumber) {
        Log.d(TAG, "phoneNumber: " + phoneNumber);
        Log.d(TAG, "text: " + text);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        return "Your message is successfully sent!";
    }
}

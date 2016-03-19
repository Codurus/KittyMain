package com.proworks.listenerengine;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Sharad on 05-03-2016.
 */
public class PlayingMusic {
    Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private ContentResolver musicResolver = new Ears().getContentResolver();
    Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

    PlayingMusic() {
        musicResolver = new Ears().getContentResolver();

    }


}

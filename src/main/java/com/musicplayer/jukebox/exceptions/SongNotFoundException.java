package com.musicplayer.jukebox.exceptions;

public class SongNotFoundException extends RuntimeException{

    public SongNotFoundException(String message) {
        super(message);
    }
    
}

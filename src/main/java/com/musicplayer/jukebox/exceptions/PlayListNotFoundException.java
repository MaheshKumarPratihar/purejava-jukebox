package com.musicplayer.jukebox.exceptions;

public class PlayListNotFoundException extends RuntimeException{
    public PlayListNotFoundException(String message) {
        super(message);
    }
}

package com.musicplayer.jukebox.exceptions;

public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(String msg)
    {
     super(msg);
    }
}

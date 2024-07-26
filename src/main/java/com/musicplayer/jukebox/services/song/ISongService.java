package com.musicplayer.jukebox.services.song;

import com.musicplayer.jukebox.entities.Song;

import java.util.Optional;

public interface ISongService {
    void addSong(String name, String genre, String albumName, String albumArtist, String featuredArtists);
    boolean songExists(int id);
    Optional<Song> getSong(Integer id);
}

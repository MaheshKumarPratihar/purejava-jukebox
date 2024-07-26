package com.musicplayer.jukebox.services.song;

import com.musicplayer.jukebox.entities.Song;
import com.musicplayer.jukebox.repositories.song.ISongRepository;

import java.util.Optional;

public class SongService implements ISongService {

    private final ISongRepository songRepository;
    

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void addSong(String name, String genre, String albumName, String albumArtist,
            String featuredArtists) {
        String collaborators = featuredArtists.replace("#", ",");
        Song song = new Song(name, genre, albumName, albumArtist, collaborators);
        songRepository.save(song);        
    }

    @Override
    public boolean songExists(int id) {
        return this.songRepository.existsById(id);
    }

    @Override
    public Optional<Song> getSong(Integer id) {
        return this.songRepository.findById(id);
    }
    
}

package com.musicplayer.jukebox.repositories.song;

import com.musicplayer.jukebox.entities.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SongRepository implements ISongRepository{
    private Map<Integer, Song> songs;    
    private Map<String, Integer> nameToId;

    private Integer autoIncrement = 0;

    public SongRepository() {
        this.songs = new HashMap<>();
        this.nameToId = new HashMap<>();
    }

    public SongRepository(Map<Integer, Song> songs) {
        this.songs = songs;

        this.nameToId = songs.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getValue().getName(), // Key: Song name
                        Map.Entry::getKey // Value: original key
                ));
    }

    @Override
    public Song save(Song entity) {
        autoIncrement++;

        entity.setId(autoIncrement);

        this.nameToId.put(entity.getName(), autoIncrement);
        return this.songs.put(autoIncrement, entity);
    }

    @Override
    public List<Song> findAll() {
        return this.songs.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findById(Integer id) {
        return Optional.ofNullable(this.songs.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return this.songs.containsKey(id);
    }

    @Override
    public void delete(Song entity) {
        this.songs.remove(entity.getId());
        this.nameToId.remove(entity.getName());
    }

    @Override
    public void deleteById(Integer id) {
        this.songs.remove(id); 
        this.nameToId.remove(this.songs.get(id).getName());
    }

    @Override
    public long count() {
        return this.songs.size();
    }  
    

}

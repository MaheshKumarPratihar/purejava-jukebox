package com.musicplayer.jukebox.repositories.playList;

import com.musicplayer.jukebox.entities.PlayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayListRepository implements IPlayListRepository{

    private final Map<Integer, PlayList> playLists;
    private int autoIncrement = 0;

    

    public PlayListRepository() {
        this.playLists = new HashMap<>();
    }

    @Override
    public PlayList save(PlayList entity) {
        if(this.playLists.containsKey(entity.getId())){
            this.playLists.put(entity.getId(), entity);
            return entity;
        }
        autoIncrement++;
        entity.setId(autoIncrement);
        this.playLists.put(entity.getId(), entity);

        return this.playLists.get(autoIncrement);
    }

    @Override
    public List<PlayList> findAll() {
        return null;
    }

    @Override
    public Optional<PlayList> findById(Integer id) {
        return Optional.ofNullable(this.playLists.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return this.playLists.containsKey(id);
    }

    @Override
    public void delete(PlayList entity) {
        this.playLists.remove(entity.getId());        
    }

    @Override
    public void deleteById(Integer id) {
        this.playLists.remove(id);        
    }

    @Override
    public long count() {
        return this.autoIncrement;
    }
    
}

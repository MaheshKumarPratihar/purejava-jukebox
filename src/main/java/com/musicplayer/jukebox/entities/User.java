package com.musicplayer.jukebox.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    private String name;
    private Set<Integer> playListIds = new HashSet<>();
    private int activePlaylistId = 0;

    public User(String name){
        this.name = name;
    }

    public void addPlayListId(int playlistId) {
        this.playListIds.add(playlistId);
    }
}

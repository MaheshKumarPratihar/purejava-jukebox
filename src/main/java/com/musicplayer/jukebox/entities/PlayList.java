package com.musicplayer.jukebox.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayList extends BaseEntity {
    private String playListName;
    private List<Integer> songIds = new ArrayList<>();
    private int activeSongId = 0;

    public PlayList(String playListName) {
        this.playListName = playListName;
    }

    public PlayList(String playListName, List<Integer> songIds) {
        this.playListName = playListName;
        this.songIds = songIds;
    }
}

package com.musicplayer.jukebox.services.playList;

import com.musicplayer.jukebox.entities.PlayList;

import java.util.List;
import java.util.Optional;

public interface IPlayListService {
    PlayList createPlayList(String userId, String playListName, List<Integer> songIds);

    Optional<PlayList> getPlayList(int playListId);

    void deletePlayList(int playListId);

    void addSong(int songId, int playListId);

    PlayList save(PlayList playList);

    void deleteSong(int songId, int playListId);

    void printSongIds(int playListId);
}

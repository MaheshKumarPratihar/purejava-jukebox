package com.musicplayer.jukebox.services.playList;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.exceptions.SongNotFoundException;
import com.musicplayer.jukebox.repositories.playList.IPlayListRepository;
import com.musicplayer.jukebox.repositories.user.IUserRepository;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;
import java.util.Optional;

public class PlayListService implements IPlayListService {

    private final IPlayListRepository playListRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public PlayListService(IPlayListRepository playListRepository, IUserRepository userRepository,
            IUserService userService) {
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public PlayList createPlayList(String userId, String playListName, List<Integer> songIds) {
        PlayList playList = new PlayList(playListName, songIds);
        playList = this.playListRepository.save(playList);

        User user = this.userRepository.findById(Integer.valueOf(userId)).orElseThrow();
        this.userService.addPlayList(user.getId(), playList);
        return playList;
    }

    @Override
    public Optional<PlayList> getPlayList(int playListId) {
        return this.playListRepository.findById(playListId);
    }

    @Override
    public void deletePlayList(int playListId) {
        this.playListRepository.deleteById(playListId);
    }

    @Override
    public PlayList save(PlayList playList) {
        return this.playListRepository.save(playList);
    }

    @Override
    public void addSong(int songId, int playListId) {
        PlayList playList = this.playListRepository.findById(playListId)
                .orElseThrow(() -> new PlayListNotFoundException("Playlist not found"));
        if (!playList.getSongIds().contains(songId)) {
            playList.getSongIds().add(songId);
        }

        this.save(playList);
    }

    @Override
    public void deleteSong(int songId, int playListId) {
        PlayList playList = this.playListRepository.findById(playListId)
                .orElseThrow(() -> 
                    new PlayListNotFoundException("Playlist not found")
                );

        
        if(playList.getSongIds().contains(songId)){
            int songIdx = playList.getSongIds().indexOf(songId);
            playList.getSongIds().remove(songIdx);
        }else{
            throw new SongNotFoundException("Some Requested Songs for Deletion are not present in the playlist. Please try again.");
        }

        this.save(playList);
    }

    @Override
    public void printSongIds(int playListId) {
       PlayList playList = this.getPlayList(playListId)
               .orElseThrow(() -> new PlayListNotFoundException("Playlist not found"));

        for (Integer id : playList.getSongIds()) {
            System.out.print(id + " ");
        }
    }

    

}

package com.musicplayer.jukebox.services.user;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.User;

import java.util.Optional;

public interface IUserService {
    User createUser(String name);
    User addPlayList(int userId, PlayList playList);
    Optional<User> getUser(int id);
    void deletePlayList(int userId, int playListId);
    User save(User user);
}

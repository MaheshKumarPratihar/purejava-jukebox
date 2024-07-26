package com.musicplayer.jukebox.services.user;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.exceptions.UserNotFoundException;
import com.musicplayer.jukebox.repositories.user.IUserRepository;

import java.util.Optional;

public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name) {
        User user = new User(name);
        user = this.userRepository.save(user);
        return user;
    }

    @Override
    public User addPlayList(int userId, PlayList playList) {
        User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not present"));
        user.addPlayListId(playList.getId());
        return user;
    }

    @Override
    public Optional<User> getUser(int id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void deletePlayList(int userId, int playListId) {
        User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not present"));
        
        if(!user.getPlayListIds().remove(playListId)){
            throw new PlayListNotFoundException("Playlist Not Found");
        }             
    }

    @Override
    public User save(User user) {
        return null;
    }

}

package com.musicplayer.jukebox.repositories.user;

import com.musicplayer.jukebox.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository implements IUserRepository{

    private Map<Integer, User> users;
    private int autoIncrement = 0;

    

    public UserRepository() {
        this.users = new HashMap<>();
    }

    

    public UserRepository(Map<Integer, User> users) {
        this.users = users;
        this.autoIncrement = users.size();
    }



    @Override
    public User save(User entity) {
        if(this.users.containsKey(entity.getId())){
            this.users.put(entity.getId(), entity);
            return entity;
        }
        autoIncrement++;
        entity.setId(autoIncrement);
        this.users.put(autoIncrement, entity);

        return this.users.get(autoIncrement);
    }

    @Override
    public List<User> findAll() {
        return this.users.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(this.users.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return this.users.containsKey(id);
    }

    @Override
    public void delete(User entity) {
        this.users.remove(entity.getId());
    }

    @Override
    public void deleteById(Integer id) {
        this.users.remove(id);
    }

    @Override
    public long count() {
        return this.users.size();
    }
    
}

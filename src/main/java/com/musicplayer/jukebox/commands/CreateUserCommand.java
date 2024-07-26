package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;
/*
    CREATE-USER
    Create a new user in the system
    Input format:
    CREATE-USER { Name }

    Sample input:
        CREATE-USER Kiran 
    Output Format Schema:
    { User id } { User Name }
    Sample Output:
    1 Kiran

 */
public class CreateUserCommand implements ICommand{

    private final IUserService userService;
    
    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String name = tokens.get(1);
        User user = this.userService.createUser(name);

        System.out.println(user.getId() + " " + user.getName());
    }
    
}

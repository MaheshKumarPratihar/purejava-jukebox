package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;

/*
 * DELETE-PLAYLIST Delete a Playlist given Playlist ID if it exists in the system. Input format:
 * DELETE-PLAYLIST { USER_ID } { Playlist-ID }
 * 
 * Sample input: DELETE-PLAYLIST 1 1 Output Format Schema: Delete Successful Or Error Message if the
 * Playlist IDs do not exist. Sample Output: Delete Successful Or Playlist Not Found
 * 
 */
public class DeletePlayListCommand implements ICommand {

    private final IUserService userService;
    private final IPlayListService playListService;

    

    public DeletePlayListCommand(IUserService userService, IPlayListService playListService) {
        this.userService = userService;
        this.playListService = playListService;
    }

    @Override
    public void execute(List<String> tokens) {
        int userId = Integer.valueOf(tokens.get(1));
        int playListId = Integer.valueOf(tokens.get(2));

        this.playListService.getPlayList(playListId)
                .orElseThrow(() -> new PlayListNotFoundException("Playlist Not Found"));

        this.playListService.deletePlayList(playListId);
        this.userService.deletePlayList(userId, playListId);

        System.out.println("Delete Successful");

    }

}

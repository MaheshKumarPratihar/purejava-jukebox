package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.exceptions.SongNotFoundException;
import com.musicplayer.jukebox.exceptions.UserNotFoundException;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.song.ISongService;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;
import java.util.Optional;

/*
MODIFY-PLAYLIST
Modify a Playlist to add/delete Song from the playlist.

Input format:
To add Song
MODIFY-PLAYLIST ADD-SONG { USER_ID } {Playlist-ID } { List of Song IDs }

Note:- Do not add the song again if the Song ID already exists in the playlist.

To delete Song
MODIFY-PLAYLIST DELETE-SONG { USER_ID } {Playlist-ID } { List of Song IDs }

Sample input:
To add Song
MODIFY-PLAYLIST ADD-SONG 1 1 6 7

To Delete Song
MODIFY-PLAYLIST DELETE-SONG 1 1 3 4
Output Format Schema:
To add Song
Playlist ID - { Playlist ID  }
Playlist Name - { Playlist Name }
Song IDs - { List of final Song IDs}
Or
Error Message if Any of the Requested Song IDs is not present in the pool.

To delete Song
Playlist ID - { Playlist ID  }
Playlist Name - { Playlist Name }
Song IDs - { List of final Song IDs }
Or
Error Message if Any of the Requested Song IDs to be deleted are not present in the playlist.

Sample Output:
To add Song
Playlist ID - 1
Playlist Name - MY_PLAYLIST_1
Song IDs - 1 2 3 4 5 6 7
Or
Some Requested Songs Not Available. Please try again.

To delete Song
Playlist ID - 1
Playlist Name - MY_PLAYLIST_1
Song IDs - 1 2 5 6 7
Or
Some Requested Songs for Deletion are not present in the playlist. Please try again.

 */
public class ModifyPlayListCommand implements ICommand{

    private final IUserService userService;
    private final IPlayListService playListService;
    private final ISongService songService;

    
    public ModifyPlayListCommand(IUserService userService, IPlayListService playListService,
            ISongService songService) {
        this.userService = userService;
        this.playListService = playListService;
        this.songService = songService;
    }


    @Override
    public void execute(List<String> tokens) {
        String operation = tokens.get(1);
        int userId = Integer.parseInt(tokens.get(2));
        int playListId = Integer.parseInt(tokens.get(3));

        List<Integer> songIds = tokens.subList(4, tokens.size()).stream()
                                .map(Integer::valueOf) 
                                .toList();

        User user = this.userService.getUser(userId)
            .orElseThrow(() -> 
                new UserNotFoundException("User not present")
            );
        PlayList playList = this.playListService.getPlayList(playListId)
            .orElseThrow(() -> 
                new PlayListNotFoundException("Playlist doesn't exists")
            );


        // some song ids are not present in the repository
        Optional<Integer> song = songIds.stream()
            .filter(id -> !songService.songExists(id))
            .findAny();

        if(song.isPresent()){
            throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        }

        if(operation.equals("ADD-SONG")){
            // if playlist is not present in user playlists
            if(!user.getPlayListIds().contains(playListId)){
                user.addPlayListId(playListId);
                this.userService.save(user);
            }else{
                songIds
                .forEach(songId -> {
                    this.playListService.addSong(songId, playListId);
                });
            }
        }else if(operation.equals("DELETE-SONG")){           

            for(int songId: songIds){
                try {
                    this.playListService.deleteSong(songId, playListId);
                } catch (SongNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

        }

        System.out.println("Playlist ID - " + playListId);
        System.out.println("Playlist Name - " + playList.getPlayListName());
        System.out.print("Song IDs - ");
        this.playListService.printSongIds(playListId);
        
    }
    
}

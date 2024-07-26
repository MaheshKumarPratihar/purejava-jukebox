package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.exceptions.SongNotFoundException;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.song.ISongService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 CREATE-PLAYLIST
Create a new Playlist from a pool of songs. A song could exist in multiple playlists.
Input format:
CREATE-PLAYLIST { USER_ID } { PLAYLIST_NAME } { List of Song IDs }

Note:- Order in which the song is added in the playlist must be maintained.

Sample input:
	CREATE-PLAYLIST 1  MY_PLAYLIST_1 1 2 3 4 5
Output Format Schema:
Playlist ID - { Playlist ID  }
Or
Error Message if Any of the Above Song IDs is not present in the pool.
Sample Output:
Playlist ID - 1
Or
Some Requested Songs Not Available. Please try again.

 */
public class CreatePlayListCommand implements ICommand{

    private IPlayListService playListService;
    private ISongService songService;

    

    public CreatePlayListCommand(IPlayListService playListService, ISongService songService) {
        this.playListService = playListService;
        this.songService = songService;
    }



    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playListName = tokens.get(2);

        List<Integer> songIds = tokens.subList(3, tokens.size()).stream()
                                .map(Integer::valueOf) 
                                .collect(Collectors.toList());

        // we need to check if song is present or not
        Optional<Integer> song = songIds.stream().filter(id -> !songService.songExists(id)).findAny();
        if(song.isPresent()){
            throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        }

        PlayList playList = this.playListService.createPlayList(userId, playListName, songIds);

        System.out.println("Playlist ID - " + playList.getId());        
    }
    
}

package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.Song;
import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.exceptions.NoDataPresentException;
import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.exceptions.SongNotFoundException;
import com.musicplayer.jukebox.exceptions.UserNotFoundException;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.song.ISongService;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;

/*
PLAY-PLAYLIST
Start playing the playlist. The output will be the first song of the playlist.
Input format:
PLAY-PLAYLIST  { USER_ID } { Playlist-ID }

Sample input:
	PLAY-PLAYLIST 1 1 
Output Format Schema:
Current Song Playing
Song  -  { Song Name  } 
Album - { Album Name  }  
Artists - { List of Artists }
Or
Error Message if Playlist is empty.

Note:-  The output song will be the first song of the playlist.

Sample Output:
Current Song Playing
Song - Save Your Tears(Remix)
Album -  After Hours 
Artists -  TheWeeknd,Ariana Grande  
Or
Playlist is empty.

 */
public class PlayPlayListCommand implements ICommand{

    private final IUserService userService;
    private final IPlayListService playListService;
    private final ISongService songService;

    
    public PlayPlayListCommand(IUserService userService, IPlayListService playListService,
            ISongService songService) {
        this.userService = userService;
        this.playListService = playListService;
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {
        int userId = Integer.valueOf(tokens.get(1));
        int playListId = Integer.valueOf(tokens.get(2));
        
        User user = this.userService.getUser(userId).orElseThrow(() -> new UserNotFoundException("User not present"));
        // if user doesn't have the playlist
        if(!user.getPlayListIds().contains(playListId)){
            // TODO
        }
        
        PlayList playList = this.playListService.getPlayList(playListId)
                    .orElseThrow(() -> 
                        new PlayListNotFoundException("Playlist doesn't exists")
                    );

        

        if(playList.getSongIds().isEmpty()){
            throw new NoDataPresentException("Playlist is empty.");
        }

        Song song = this.songService.getSong(playList.getSongIds().get(0))
                .orElseThrow(() -> new SongNotFoundException("Song doesn't exist"));
        System.out.println("Current Song Playing");
        System.out.println("Song - " + song.getName());
        System.out.println("Album - " + song.getAlbum());
        System.out.println("Artists - " + song.getFeaturedArtists());

        user.setActivePlaylistId(playListId);
        playList.setActiveSongId(song.getId());
    }
    
}

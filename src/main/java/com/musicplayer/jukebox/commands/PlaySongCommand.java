package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.entities.PlayList;
import com.musicplayer.jukebox.entities.Song;
import com.musicplayer.jukebox.entities.User;
import com.musicplayer.jukebox.exceptions.PlayListNotFoundException;
import com.musicplayer.jukebox.exceptions.SongNotFoundException;
import com.musicplayer.jukebox.exceptions.UserNotFoundException;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.song.ISongService;
import com.musicplayer.jukebox.services.user.IUserService;

import java.util.List;
/*
PLAY-SONG 
Switch songs on the active playlist currently playing any song.

Input format:

Switch to play a previous song in the active playlist.
PLAY-SONG { USER_ID }  BACK

Switch to play the next song in the active playlist.
PLAY-SONG  { USER_ID } NEXT

Switch to the preferred song in the playlist.
PLAY-SONG { USER_ID }  { Song ID }

Output Format Schema:
Switch to play a previous song in the active playlist.

Current Song Playing
Song  -  { Song Name  } 
Album - { Album Name  }  
Artists - { List of Artists 

Switch to play the next song in the active playlist.

Current Song Playing
Song  -  { Song Name  } 
Album - { Album Name  }  
Artists - { List of Artists 

Switch to the preferred song in the playlist.

Current Song Playing
Song  -  { Song Name  } 
Album - { Album Name  }  
Artists - { List of Artists 
Or
Error Message if above Song ID is not part of a current active playlist.

Sample Output:
Switch to play a previous song in the active playlist.

Current Song Playing
Song - Save Your Tears(Remix)
Album -  After Hours 
Artists -  TheWeeknd,Ariana Grande

Switch to play the next song in the active playlist.

Current Song Playing
Song - Save Your Tears(Remix)
Album -  After Hours 
Artists -  TheWeeknd,Ariana Grande

Switch to the preferred song in the playlist.

Current Song Playing
Song - Save Your Tears(Remix)
Album -  After Hours 
Artists -  TheWeeknd,Ariana Grande
Or
Song Not Found in the current active playlist.

 */
public class PlaySongCommand implements ICommand{

    private final IUserService userService;
    private final IPlayListService playListService;
    private final ISongService songService;

    public PlaySongCommand(IUserService userService, IPlayListService playListService,
            ISongService songService) {
        this.userService = userService;
        this.playListService = playListService;
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) throws Exception{
        int userId = Integer.parseInt(tokens.get(1));
        String operation = tokens.get(2);

        
        User user = this.userService.getUser(userId)
            .orElseThrow( () ->
                new UserNotFoundException("User not present")
            );

        int activePlaylistId = user.getActivePlaylistId();
        // there is no active playlist
        if(activePlaylistId == 0){

        }

        PlayList playList = this.playListService.getPlayList(activePlaylistId)
                    .orElseThrow(() -> 
                        new PlayListNotFoundException("Playlist doesn't exists")
                    );

        int activeSongId = playList.getActiveSongId();

        // index of currently playing song in playlist
        int idx = getIndexOfCurrentSong(playList, activeSongId);
        int nowPlaying = -1;
        if(operation.equals("NEXT")){            
            // currently playing last song of playlist
            // so if "NEXT" then first song of play list will be played
            if(idx == playList.getSongIds().size() - 1){
                nowPlaying = playList.getSongIds().get(0);
            }else {
                nowPlaying = playList.getSongIds().get(idx + 1);
            }
        }else if(operation.equals("BACK")){
            if(idx == 0){
                nowPlaying = playList.getSongIds().get(playList.getSongIds().size() - 1);
            }else{
                nowPlaying = playList.getSongIds().get(idx - 1);
            }
        }else {
            // playing random song
            int songId = Integer.parseInt(operation);
            if(this.checkSongExistInPlayList(playList, songId)){
                nowPlaying =  songId;
            }else{
                throw new SongNotFoundException("Song Not Found in the current active playlist.");
            }
        }
        this.play(nowPlaying);
        playList.setActiveSongId(nowPlaying);

    }

    private int getIndexOfCurrentSong(PlayList playList, int songId){
        int idx = 0;
        for(int id: playList.getSongIds()){
            if(id == songId){
                return idx;
            }
            idx++;
        }
        return -1;
    }

    private void play(int songId){
        Song song = this.songService.getSong(songId)
                .orElseThrow(() -> new SongNotFoundException("Song Not Found"));
        System.out.println("Current Song Playing");
        System.out.println("Song - " + song.getName());
        System.out.println("Album - " + song.getAlbum());
        System.out.println("Artists - " + song.getFeaturedArtists());
    }

    private boolean checkSongExistInPlayList(PlayList playList, int songId){
        return playList.getSongIds().stream()
            .anyMatch(id -> id == songId);
    }

    enum SongOperation {
        BACK, NEXT
    }
    
}

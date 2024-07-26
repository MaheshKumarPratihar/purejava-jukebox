package com.musicplayer.jukebox.appConfig;

import com.musicplayer.jukebox.commands.CommandInvoker;
import com.musicplayer.jukebox.commands.CreatePlayListCommand;
import com.musicplayer.jukebox.commands.CreateUserCommand;
import com.musicplayer.jukebox.commands.DeletePlayListCommand;
import com.musicplayer.jukebox.commands.LoadDataCommand;
import com.musicplayer.jukebox.commands.ModifyPlayListCommand;
import com.musicplayer.jukebox.commands.PlayPlayListCommand;
import com.musicplayer.jukebox.commands.PlaySongCommand;
import com.musicplayer.jukebox.repositories.playList.IPlayListRepository;
import com.musicplayer.jukebox.repositories.playList.PlayListRepository;
import com.musicplayer.jukebox.repositories.song.ISongRepository;
import com.musicplayer.jukebox.repositories.song.SongRepository;
import com.musicplayer.jukebox.repositories.user.IUserRepository;
import com.musicplayer.jukebox.repositories.user.UserRepository;
import com.musicplayer.jukebox.services.playList.IPlayListService;
import com.musicplayer.jukebox.services.playList.PlayListService;
import com.musicplayer.jukebox.services.song.ISongService;
import com.musicplayer.jukebox.services.song.SongService;
import com.musicplayer.jukebox.services.user.IUserService;
import com.musicplayer.jukebox.services.user.UserService;

public class ApplicationConfig {
    private final IPlayListRepository playListRepository = new PlayListRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final ISongRepository songRepository = new SongRepository();

    private final ISongService songService = new SongService(songRepository);
    private final IUserService userService = new UserService(userRepository);
    private final IPlayListService playListService = new PlayListService(playListRepository, userRepository, userService);
    
    private final LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final CreatePlayListCommand createPlayListCommand = new CreatePlayListCommand(playListService, songService);
    private final DeletePlayListCommand deletePlayListCommand = new DeletePlayListCommand(userService, playListService);
    private final PlayPlayListCommand playPlayListCommand = new PlayPlayListCommand(userService, playListService, songService);
    private final ModifyPlayListCommand modifyPlayListCommand = new ModifyPlayListCommand(userService, playListService, songService);
    private final PlaySongCommand playSongCommand = new PlaySongCommand(userService, playListService, songService);
    
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("LOAD-DATA",loadDataCommand);
        commandInvoker.register("CREATE-USER",createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST",createPlayListCommand);
        commandInvoker.register("DELETE-PLAYLIST",deletePlayListCommand);
        commandInvoker.register("PLAY-PLAYLIST",playPlayListCommand);
        commandInvoker.register("MODIFY-PLAYLIST",modifyPlayListCommand);
        commandInvoker.register("PLAY-SONG",playSongCommand);
        return commandInvoker;
    }
}

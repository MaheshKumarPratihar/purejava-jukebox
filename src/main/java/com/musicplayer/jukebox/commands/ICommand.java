package com.musicplayer.jukebox.commands;

import java.util.List;

public interface ICommand {
    void execute(List<String> tokens) throws Exception;
}

package com.musicplayer.jukebox.commands;

import com.musicplayer.jukebox.services.song.ISongService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public class LoadDataCommand implements ICommand{

    private final ISongService songService;

    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {

        String fileName = tokens.get(1);

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                this.songService.addSong(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4]);
            }
        } catch (IOException | CsvValidationException e) {
            log.error(e.getMessage());
        }
        log.info("Songs Loaded successfully");
    }
}

package com.musicplayer.jukebox.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song extends BaseEntity{
    private String name;
    private String genre;
    private String album;
    private String artist;
    private String featuredArtists;
}

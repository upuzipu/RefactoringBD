package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a song.
 * Contains information about the song, including its title, artist name, genre, and URL to access the song.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    /**
     * Unique identifier of the song.
     */
    private long songId;

    /**
     * Title of the song.
     */
    private String songName;

    /**
     * Name of the song's artist.
     */
    private String artistName;

    /**
     * Genre of the song.
     */
    private String jenreName;

    /**
     * URL to access the song (e.g., path to the audio file).
     */
    private String songUrl;
}

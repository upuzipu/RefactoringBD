package com.example.dto.song;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing song information.
 */
@Getter
@Setter
public class SongResponseDto {

    /**
     * Unique identifier for the song.
     */
    private Long id;

    /**
     * Name of the song.
     */
    private String name;

    /**
     * Artist of the song.
     */
    private String artist;

    /**
     * Genre of the song.
     */
    private String genre;
}

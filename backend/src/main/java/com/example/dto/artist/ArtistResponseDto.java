package com.example.dto.artist;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing artist information.
 */
@Getter
@Setter
public class ArtistResponseDto {

    /**
     * Unique identifier of the artist.
     */
    private long id;

    /**
     * Name of the artist.
     */
    private String name;
}

package com.example.dto.album;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing album information.
 */
@Getter
@Setter
public class AlbumResponseDto {

    /**
     * Unique identifier of the album.
     */
    private Long id;

    /**
     * Name of the album.
     */
    private String name;

    /**
     * Artist of the album.
     */
    private String artist;

    /**
     * Creation date of the album.
     */
    @JsonProperty("creation_date")
    private String creationDate;
}

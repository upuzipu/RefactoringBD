package com.example.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing playlist information.
 */
@Getter
@Setter
public class PlaylistResponseDto {

    /**
     * Unique identifier of the playlist.
     */
    private Long id;

    /**
     * Name of the playlist.
     */
    private String name;

    /**
     * Creator of the playlist.
     */
    private String creator;

    /**
     * Time of the last update of the playlist.
     */
    @JsonProperty("update_time")
    private String updateTime;

    /**
     * Date when the playlist was created.
     */
    @JsonProperty("creation_date")
    private String creationDate;
}

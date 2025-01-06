package com.example.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing the data to create a new playlist.
 */
@Getter
@Setter
public class PlaylistCreationDTO {

    /**
     * Name of the new playlist.
     */
    @JsonProperty("playlist_name")
    private String playlistName;
}

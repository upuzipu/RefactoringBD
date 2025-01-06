package com.example.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for representing the response when creating a new playlist.
 */
@Getter
@Setter
@AllArgsConstructor
public class PlaylistCreationResponseDto {

    /**
     * Unique identifier of the created playlist.
     */
    @JsonProperty("playlist_id")
    private long playlistId;
}

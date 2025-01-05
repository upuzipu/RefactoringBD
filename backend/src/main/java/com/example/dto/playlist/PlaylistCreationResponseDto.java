package com.example.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaylistCreationResponseDto {
    @JsonProperty("playlist_id")
    private long playlistId;
}

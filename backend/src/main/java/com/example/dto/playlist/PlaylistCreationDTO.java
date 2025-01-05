package com.example.dto.playlist;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCreationDTO {
    @JsonProperty("playlist_name")
    private String playlistName;
}

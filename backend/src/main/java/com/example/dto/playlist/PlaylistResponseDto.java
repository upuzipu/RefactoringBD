package com.example.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistResponseDto {

    private Long id;
    private String name;
    private String creator;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("creation_date")
    private String creationDate;

}
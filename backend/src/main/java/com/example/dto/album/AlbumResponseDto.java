package com.example.dto.album;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumResponseDto {

    private Long id;
    private String name;
    private String artist;

    @JsonProperty("creation_date")
    private String creationDate;

}
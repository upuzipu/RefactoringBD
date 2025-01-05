package com.example.dto.song;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongResponseDto {

    private Long id;
    private String name;
    private String artist;
    private String genre;

}
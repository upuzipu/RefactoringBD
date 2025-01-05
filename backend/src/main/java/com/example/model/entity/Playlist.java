package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private Long playlistId;
    private String playlistName;
    private String creatorName;
    private LocalDateTime creationDate;
    private LocalDateTime updateTime;
}

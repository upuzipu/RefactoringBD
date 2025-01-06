package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a playlist.
 * Contains information about the playlist, its name, creator, and creation/update times.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    /**
     * Unique identifier of the playlist.
     */
    private Long playlistId;

    /**
     * Name of the playlist.
     */
    private String playlistName;

    /**
     * Name of the playlist creator.
     */
    private String creatorName;

    /**
     * Date and time when the playlist was created.
     */
    private LocalDateTime creationDate;

    /**
     * Date and time when the playlist was last updated.
     */
    private LocalDateTime updateTime;
}

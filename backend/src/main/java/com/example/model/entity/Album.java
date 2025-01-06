package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing an album.
 * Contains information about the album's name, creator, and creation date.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    /**
     * Unique identifier of the album.
     */
    private long albumId;

    /**
     * Name of the album.
     */
    private String name;

    /**
     * Name of the album's creator.
     */
    private String albumCreatorName;

    /**
     * The creation date and time of the album.
     */
    private LocalDateTime creationDate;

}

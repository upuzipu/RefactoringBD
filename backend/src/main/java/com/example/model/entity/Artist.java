package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing an artist.
 * Contains information about the artist's unique identifier and their nickname.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Artist {

    /**
     * Unique identifier of the artist.
     */
    private long artistId;

    /**
     * Artist's nickname.
     */
    private String nickname;
}

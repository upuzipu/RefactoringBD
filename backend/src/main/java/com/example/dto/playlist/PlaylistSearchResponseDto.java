package com.example.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for representing the results of playlist search.
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class PlaylistSearchResponseDto {

    /**
     * List of playlists matching the search criteria.
     */
    private List<PlaylistResponseDto> values;

    /**
     * Total number of playlists found.
     */
    private int count;

    /**
     * Current page of the results.
     */
    private int currentPage;

    /**
     * Total number of result pages.
     */
    private int totalPages;
}

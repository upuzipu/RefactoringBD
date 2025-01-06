package com.example.dto.artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for representing search results of artists.
 */
@AllArgsConstructor
@Setter
@Getter
public class ArtistSearchResponseDto {

    /**
     * List of artists that match the search criteria.
     */
    private List<ArtistResponseDto> values;

    /**
     * Total number of artists found.
     */
    private int count;

    /**
     * Current page of the results.
     */
    private int currentPage;

    /**
     * Total number of pages of results.
     */
    private int totalPages;
}

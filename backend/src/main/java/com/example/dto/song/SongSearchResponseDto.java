package com.example.dto.song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for representing song search results.
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SongSearchResponseDto {

    /**
     * List of songs that match the search criteria.
     */
    private List<SongResponseDto> values;

    /**
     * Total number of songs found.
     */
    private int count;

    /**
     * Current page of results.
     */
    private int currentPage;

    /**
     * Total number of pages of results.
     */
    private int totalPages;
}

package com.example.dto.album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for representing album search results.
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AlbumSearchResponseDto {

    /**
     * List of albums matching the search criteria.
     */
    private List<AlbumResponseDto> values;

    /**
     * Total number of albums found.
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

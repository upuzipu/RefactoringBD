package com.example.dto.artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ArtistSearchResponseDto {

    private List<ArtistResponseDto> values;
    private int count;
    private int currentPage;
    private int totalPages;

}

package com.example.dto.song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SongSearchResponseDto {
    private List<SongResponseDto> values;
    private int count;
    private int currentPage;
    private int totalPages;
}

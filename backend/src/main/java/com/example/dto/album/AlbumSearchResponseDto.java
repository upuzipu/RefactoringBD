package com.example.dto.album;

import com.example.model.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AlbumSearchResponseDto {

    private List<AlbumResponseDto> values;
    private int count;
    private int currentPage;
    private int totalPages;
}

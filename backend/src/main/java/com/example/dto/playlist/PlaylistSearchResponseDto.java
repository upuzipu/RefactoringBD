package com.example.dto.playlist;

import com.example.dto.playlist.PlaylistResponseDto;
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
public class PlaylistSearchResponseDto {

    private List<PlaylistResponseDto> values;
    private int count;
    private int currentPage;
    private int totalPages;
}

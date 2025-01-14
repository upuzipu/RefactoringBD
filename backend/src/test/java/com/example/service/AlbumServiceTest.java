package com.example.service;

import com.example.dto.album.AlbumResponseDto;
import com.example.dto.album.AlbumSearchResponseDto;
import com.example.model.entity.Album;
import com.example.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAlbumsByName() {
        Album album1 = new Album(1L, "Album1", "Artist1", LocalDateTime.now());
        Album album2 = new Album(2L, "Album2", "Artist2", LocalDateTime.now());
        List<Album> albums = Arrays.asList(album1, album2);

        when(albumRepository.findByNameContaining(anyString(), anyInt(), anyInt())).thenReturn(albums);
        when(albumRepository.countByNameContaining(anyString())).thenReturn(2);

        AlbumSearchResponseDto result = albumService.getAllAlbumsByName("Album", 0, 2);

        assertEquals(2, result.getValues().size());
        assertEquals(1, result.getCurrentPage());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getCount());
    }

    @Test
    void testGetAllAlbumsByArtist() {
        Album album1 = new Album(1L, "Album1", "Artist1", LocalDateTime.now());
        Album album2 = new Album(2L, "Album2", "Artist1", LocalDateTime.now());
        List<Album> albums = Arrays.asList(album1, album2);

        when(albumRepository.findByArtistId(1L)).thenReturn(albums);

        List<AlbumResponseDto> result = albumService.getAllAlbumsByArtist(1L);

        assertEquals(2, result.size());
        assertEquals("Album1", result.get(0).getName());
        assertEquals("Album2", result.get(1).getName());
    }
}
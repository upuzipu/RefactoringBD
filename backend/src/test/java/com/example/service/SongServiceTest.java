package com.example.service;

import com.example.dto.song.SongResponseDto;
import com.example.dto.song.SongSearchResponseDto;
import com.example.model.entity.Song;
import com.example.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSongsByName() {
        Song song1 = new Song(1L, "Song1", "Artist1", "Genre1", "song1.mp3");
        Song song2 = new Song(2L, "Song2", "Artist2", "Genre2", "song2.mp3");
        List<Song> songs = Arrays.asList(song1, song2);

        when(songRepository.findByNameContaining(anyString(), anyInt(), anyInt())).thenReturn(songs);
        when(songRepository.countByNameContaining(anyString())).thenReturn(2);

        SongSearchResponseDto result = songService.getAllSongsByName("Song", 0, 2);

        assertEquals(2, result.getValues().size());
        assertEquals(1, result.getCurrentPage());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testGetAllSongsByArtist() {
        Song song1 = new Song(1L, "Song1", "Artist1", "Genre1", "song1.mp3");
        Song song2 = new Song(2L, "Song2", "Artist1", "Genre2", "song2.mp3");
        List<Song> songs = Arrays.asList(song1, song2);

        when(songRepository.findByArtistId(1L)).thenReturn(songs);

        List<SongResponseDto> result = songService.getAllSongsByArtist(1L);

        assertEquals(2, result.size());
        assertEquals("Song1", result.get(0).getName());
        assertEquals("Song2", result.get(1).getName());
    }

    @Test
    void testGetAllSongsByAlbum() {
        Song song1 = new Song(1L, "Song1", "Artist1", "Genre1", "song1.mp3");
        Song song2 = new Song(2L, "Song2", "Artist1", "Genre2", "song2.mp3");
        List<Song> songs = Arrays.asList(song1, song2);

        when(songRepository.findByAlbumId(1L)).thenReturn(songs);

        List<SongResponseDto> result = songService.getAllSongsByAlbum(1L);

        assertEquals(2, result.size());
        assertEquals("Song1", result.get(0).getName());
        assertEquals("Song2", result.get(1).getName());
    }

    @Test
    void testGetAllSongsByUser() {
        Song song1 = new Song(1L, "Song1", "Artist1", "Genre1", "song1.mp3");
        Song song2 = new Song(2L, "Song2", "Artist1", "Genre2", "song2.mp3");
        List<Song> songs = Arrays.asList(song1, song2);

        when(songRepository.findByUserId(1L)).thenReturn(songs);

        List<SongResponseDto> result = songService.getAllSongsByUser(1L);

        assertEquals(2, result.size());
        assertEquals("Song1", result.get(0).getName());
        assertEquals("Song2", result.get(1).getName());
    }

    @Test
    void testGetMp3ById_NotFound() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> songService.getMp3ById(1L));
    }
}
package com.example.service;

import com.example.dto.playlist.PlaylistResponseDto;
import com.example.dto.playlist.PlaylistSearchResponseDto;
import com.example.exception.AccessForbiddenException;
import com.example.exception.AlreadyInPlaylistException;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Playlist;
import com.example.repository.PlaylistRepository;
import com.example.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPlaylistsByName() {
        Playlist playlist1 = new Playlist(1L, "Playlist1", "Creator1", LocalDateTime.now(),  LocalDateTime.now());
        Playlist playlist2 = new Playlist(2L, "Playlist2", "Creator2",  LocalDateTime.now(),  LocalDateTime.now());
        List<Playlist> playlists = Arrays.asList(playlist1, playlist2);

        when(playlistRepository.findByNameContaining(anyString(), anyInt(), anyInt())).thenReturn(playlists);
        when(playlistRepository.countByNameContaining(anyString())).thenReturn(2);

        PlaylistSearchResponseDto result = playlistService.getAllPlaylistsByName("Playlist", 0, 2);

        assertEquals(2, result.getValues().size());
        assertEquals(1, result.getCurrentPage());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testGetAllPlaylistsByCreator() {
        Playlist playlist1 = new Playlist(1L, "Playlist1", "Creator1",  LocalDateTime.now(), LocalDateTime.now());
        Playlist playlist2 = new Playlist(2L, "Playlist2", "Creator1", LocalDateTime.now(), LocalDateTime.now());
        List<Playlist> playlists = Arrays.asList(playlist1, playlist2);

        when(playlistRepository.findByCreatorId(1L)).thenReturn(playlists);

        List<PlaylistResponseDto> result = playlistService.getAllPlaylistsByCreator(1L);

        assertEquals(2, result.size());
        assertEquals("Playlist1", result.get(0).getName());
        assertEquals("Playlist2", result.get(1).getName());
    }


    @Test
    void testAddMusicToPlaylist_SongNotFound() {
        when(songRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> playlistService.addMusicToPlaylist(1L, 1L, "user@example.com"));
    }

    @Test
    void testAddMusicToPlaylist_AlreadyInPlaylist() {
        when(songRepository.existsById(1L)).thenReturn(true);
        when(playlistRepository.existsSongInPlaylist(1L, 1L)).thenReturn(true);

        assertThrows(AlreadyInPlaylistException.class, () -> playlistService.addMusicToPlaylist(1L, 1L, "user@example.com"));
    }


    @Test
    void testRemoveMusicFromPlaylist_SongNotFound() {
        when(songRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> playlistService.removeMusicFromPlaylist(1L, 1L, "user@example.com", ""));
    }

}
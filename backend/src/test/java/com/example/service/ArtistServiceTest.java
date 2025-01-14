package com.example.service;

import com.example.dto.artist.ArtistResponseDto;
import com.example.dto.artist.ArtistSearchResponseDto;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Artist;
import com.example.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArtistsByName() {
        Artist artist1 = new Artist(1L, "Artist1");
        Artist artist2 = new Artist(2L, "Artist2");
        List<Artist> artists = Arrays.asList(artist1, artist2);

        when(artistRepository.findByNameContaining(anyString(), anyInt(), anyInt())).thenReturn(artists);
        when(artistRepository.countByNameContaining(anyString())).thenReturn(2);

        ArtistSearchResponseDto result = artistService.getAllArtistsByName("Artist", 0, 2);

        assertEquals(2, result.getValues().size());
        assertEquals(1, result.getCurrentPage());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void testGetArtistById() {
        Artist artist = new Artist(1L, "Artist1");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        ArtistResponseDto result = artistService.getArtistById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Artist1", result.getName());
    }

    @Test
    void testGetArtistById_NotFound() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> artistService.getArtistById(1L));
    }
}
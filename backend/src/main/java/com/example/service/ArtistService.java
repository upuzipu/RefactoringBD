package com.example.service;

import com.example.dto.artist.ArtistResponseDto;
import com.example.dto.artist.ArtistSearchResponseDto;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Artist;
import com.example.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for working with artists.
 * Provides methods for retrieving information about artists, including searching by name and getting an artist by ID.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    /**
     * Get all artists matching the search query by name.
     *
     * @param name the name of the artist to search for (can be null or empty to retrieve all artists)
     * @param offset the offset for pagination
     * @param limit the number of items per page
     * @return an {@link ArtistSearchResponseDto} object containing the list of artists and pagination information
     */
    public ArtistSearchResponseDto getAllArtistsByName(String name, int offset, int limit) {
        log.info("Fetching artists by name: {}, offset: {}, limit: {}", name, offset, limit);
        int count = (name != null && !name.isEmpty()) ?
                artistRepository.countByNameContaining(name) :
                artistRepository.count();
        int totalPages = count / limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currentPage = offset / limit + 1;
        List<Artist> artists = (name != null && !name.isEmpty()) ?
                artistRepository.findByNameContaining(name, offset, limit) :
                artistRepository.findAll(offset, limit);
        log.info("Found {} artists, current page: {}, total pages: {}", count, currentPage, totalPages);
        return new ArtistSearchResponseDto(artists.stream().map(this::mapToDto).collect(Collectors.toList()), count, currentPage, totalPages);
    }

    /**
     * Get an artist by their ID.
     *
     * @param artistId the artist's ID
     * @return an {@link ArtistResponseDto} object containing the artist's information
     * @throws EntityNotFoundException if no artist with the given ID is found
     */
    public ArtistResponseDto getArtistById(long artistId) {
        log.info("Fetching artist by ID: {}", artistId);
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        log.info("Found artist: {}", artist.getNickname());
        return mapToDto(artist);
    }

    /**
     * Converts the {@link Artist} entity to an {@link ArtistResponseDto} object.
     *
     * @param artist the artist to convert
     * @return an {@link ArtistResponseDto} object
     */
    private ArtistResponseDto mapToDto(Artist artist) {
        ArtistResponseDto dto = new ArtistResponseDto();
        dto.setId(artist.getArtistId());
        dto.setName(artist.getNickname());
        return dto;
    }
}

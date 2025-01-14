package com.example.service;

import com.example.dto.album.AlbumResponseDto;
import com.example.dto.album.AlbumSearchResponseDto;
import com.example.model.entity.Album;
import com.example.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for working with albums.
 * Provides methods for retrieving information about albums, including searching by name and getting all albums
 * by an artist.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumService {



    private final AlbumRepository albumRepository;

    /**
     * Get all albums matching the search query by name.
     *
     * @param name the name of the album to search for (can be null or empty to retrieve all albums)
     * @param offset the offset for pagination
     * @param limit the number of items per page
     * @return an {@link AlbumSearchResponseDto} object containing the list of albums and pagination information
     */
    public AlbumSearchResponseDto getAllAlbumsByName(String name, int offset, int limit) {
        log.info("Fetching albums by name: {}, offset: {}, limit: {}", name, offset, limit);
        int count = (name != null && !name.isEmpty()) ?
                albumRepository.countByNameContaining(name) :
                albumRepository.count();
        List<Album> albums = (name != null && !name.isEmpty()) ?
                albumRepository.findByNameContaining(name, offset, limit) :
                albumRepository.findAll(offset, limit);
        int totalPages = count / limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currentPage = offset / limit + 1;
        log.info("Found {} albums, current page: {}, total pages: {}", count, currentPage, totalPages);
        return new AlbumSearchResponseDto(albums.stream().map(this::mapToDto).collect(Collectors.toList()), count, currentPage, totalPages);
    }

    /**
     * Get all albums of a specific artist.
     *
     * @param artistId the artist's ID
     * @return a list of {@link AlbumResponseDto} containing information about the artist's albums
     */
    public List<AlbumResponseDto> getAllAlbumsByArtist(long artistId) {
        log.info("Fetching albums by artist ID: {}", artistId);
        List<Album> albums = albumRepository.findByArtistId(artistId);
        log.info("Found {} albums for artist ID: {}", albums.size(), artistId);
        return albums.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Converts the {@link Album} entity to a {@link AlbumResponseDto} object.
     *
     * @param album the album to convert
     * @return an {@link AlbumResponseDto} object
     */
    private AlbumResponseDto mapToDto(Album album) {
        AlbumResponseDto dto = new AlbumResponseDto();
        dto.setId(album.getAlbumId());
        dto.setName(album.getName());
        dto.setArtist(album.getAlbumCreatorName());
        dto.setCreationDate(album.getCreationDate().toString());
        return dto;
    }
}

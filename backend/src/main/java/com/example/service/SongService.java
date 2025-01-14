package com.example.service;

import com.example.dto.song.SongResponseDto;
import com.example.dto.song.SongSearchResponseDto;
import com.example.model.entity.Song;
import com.example.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing songs.
 * Handles creating, searching songs by different filters, and retrieving the song file.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    /**
     * Retrieves all songs with the option to filter by name and paginate.
     *
     * @param name   the name of the song to filter by
     * @param offset the offset for pagination
     * @param limit  the limit for pagination
     * @return a {@link SongSearchResponseDto} containing song information, total count, and the current page
     */
    public SongSearchResponseDto getAllSongsByName(String name, int offset, int limit) {
        log.info("Fetching songs by name: {}, offset: {}, limit: {}", name, offset, limit);
        int count = (name != null && !name.isEmpty()) ?
                songRepository.countByNameContaining(name) :
                songRepository.count();
        int totalPages = count / limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currentPage = offset / limit + 1;
        List<Song> songs = (name != null && !name.isEmpty()) ?
                songRepository.findByNameContaining(name, offset, limit) :
                songRepository.findAll(offset, limit);
        log.info("Found {} songs, current page: {}, total pages: {}", count, currentPage, totalPages);
        var songsDto = songs.stream().map(this::mapToDto).collect(Collectors.toList());
        return new SongSearchResponseDto(songsDto, count, currentPage, totalPages);
    }

    /**
     * Retrieves all songs by a specific artist.
     *
     * @param artistId the ID of the artist
     * @return a list of {@link SongResponseDto} containing information about the artist's songs
     */
    public List<SongResponseDto> getAllSongsByArtist(long artistId) {
        log.info("Fetching songs by artist ID: {}", artistId);
        List<Song> songs = songRepository.findByArtistId(artistId);
        log.info("Found {} songs for artist ID: {}", songs.size(), artistId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves all songs from a specific album.
     *
     * @param albumId the ID of the album
     * @return a list of {@link SongResponseDto} containing information about the album's songs
     */
    public List<SongResponseDto> getAllSongsByAlbum(long albumId) {
        log.info("Fetching songs by album ID: {}", albumId);
        List<Song> songs = songRepository.findByAlbumId(albumId);
        log.info("Found {} songs for album ID: {}", songs.size(), albumId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves all songs by a user based on their user ID.
     *
     * @param userId the ID of the user
     * @return a list of {@link SongResponseDto} containing information about the user's songs
     */
    public List<SongResponseDto> getAllSongsByUser(long userId) {
        log.info("Fetching songs by user ID: {}", userId);
        List<Song> songs = songRepository.findByUserId(userId);
        log.info("Found {} songs for user ID: {}", songs.size(), userId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves the MP3 file of a song by its ID.
     *
     * @param songId the ID of the song
     * @return a byte array containing the MP3 file data
     * @throws ResponseStatusException if the song is not found or there is an error reading the file
     */
    public byte[] getMp3ById(long songId) {
        log.info("Fetching MP3 for song ID: {}", songId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
        Path path = Paths.get("src/main/resources/songs/" + song.getSongUrl());
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Error reading file for song ID: {}", songId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file", e);
        }
    }

    /**
     * Converts a song entity into a DTO object.
     *
     * @param song the {@link Song} entity to be converted
     * @return a {@link SongResponseDto} containing information about the song
     */
    private SongResponseDto mapToDto(Song song) {
        SongResponseDto dto = new SongResponseDto();
        dto.setId(song.getSongId());
        dto.setName(song.getSongName());
        dto.setArtist(song.getArtistName());
        dto.setGenre(song.getJenreName());
        return dto;
    }
}

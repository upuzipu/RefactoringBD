package com.example.service;

import com.example.dto.song.SongResponseDto;
import com.example.dto.song.SongSearchResponseDto;
import com.example.model.entity.Song;
import com.example.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public SongSearchResponseDto getAllSongsByName(String name, int offset, int limit) {

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
        var songsDto = songs.stream().map(this::mapToDto).collect(Collectors.toList());
        return new SongSearchResponseDto(songsDto, count, currentPage, totalPages);

    }

    public List<SongResponseDto> getAllSongsByArtist(long artistId) {
        List<Song> songs = songRepository.findByArtistId(artistId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<SongResponseDto> getAllSongsByAlbum(long albumId) {
        List<Song> songs = songRepository.findByAlbumId(albumId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<SongResponseDto> getAllSongsByUser(long userId) {
        List<Song> songs = songRepository.findByUserId(userId);
        return songs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public byte[] getMp3ById(long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
        Path path = Paths.get("src/main/resources/songs/" + song.getSongUrl());
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file", e);
        }
    }

    private SongResponseDto mapToDto(Song song) {
        SongResponseDto dto = new SongResponseDto();
        dto.setId(song.getSongId());
        dto.setName(song.getSongName());
        dto.setArtist(song.getArtistName());
        dto.setGenre(song.getJenreName());
        return dto;
    }
}
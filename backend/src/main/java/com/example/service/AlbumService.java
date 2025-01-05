package com.example.service;

import com.example.dto.album.AlbumResponseDto;
import com.example.dto.album.AlbumSearchResponseDto;
import com.example.model.entity.Album;
import com.example.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumSearchResponseDto getAllAlbumsByName(String name, int offset, int limit) {
        int count = (name != null && !name.isEmpty()) ?
                albumRepository.countByNameContaining(name) :
                albumRepository.count();
        List<Album> albums = (name != null && !name.isEmpty()) ?
                albumRepository.findByNameContaining(name, offset, limit) :
                albumRepository.findAll(offset, limit);
        int totalPages = count/limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currentPage = offset/limit + 1;
        return new AlbumSearchResponseDto(albums.stream().map(this::mapToDto).collect(Collectors.toList()), count, currentPage, totalPages);
    }

    public List<AlbumResponseDto> getAllAlbumsByArtist(long artistId) {
        List<Album> albums = albumRepository.findByArtistId(artistId);
        return albums.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private AlbumResponseDto mapToDto(Album album) {
        AlbumResponseDto dto = new AlbumResponseDto();
        dto.setId(album.getAlbumId());
        dto.setName(album.getName());
        dto.setArtist(album.getAlbumCreatorName());
        dto.setCreationDate(album.getCreationDate().toString());
        return dto;
    }
}
package com.example.service;

import com.example.dto.artist.ArtistResponseDto;
import com.example.dto.artist.ArtistSearchResponseDto;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Artist;
import com.example.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistSearchResponseDto getAllArtistsByName(String name, int offset, int limit) {
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
        return new ArtistSearchResponseDto(artists.stream().map(this::mapToDto).collect(Collectors.toList()), count, currentPage, totalPages);
    }

    public ArtistResponseDto getArtistById(long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        return mapToDto(artist);
    }

    private ArtistResponseDto mapToDto(Artist artist) {
        ArtistResponseDto dto = new ArtistResponseDto();
        dto.setId(artist.getArtistId());
        dto.setName(artist.getNickname());
        return dto;
    }
}
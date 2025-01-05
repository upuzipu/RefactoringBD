package com.example.controller;

import com.example.dto.artist.ArtistResponseDto;
import com.example.dto.artist.ArtistSearchResponseDto;
import com.example.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<ArtistSearchResponseDto> getAllArtistsByName(@RequestParam(required = false) String name,
                                                                       @RequestParam(defaultValue = "0") int offset,
                                                                       @RequestParam(defaultValue = "1000") int limit) {
        ArtistSearchResponseDto artists = artistService.getAllArtistsByName(name, offset, limit);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponseDto> getArtistById(@PathVariable long artistId) {
        ArtistResponseDto artist = artistService.getArtistById(artistId);
        return ResponseEntity.ok(artist);
    }
}
package com.example.controller;

import com.example.dto.album.AlbumResponseDto;
import com.example.dto.album.AlbumSearchResponseDto;
import com.example.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<AlbumSearchResponseDto> getAllAlbumsByName(@RequestParam(required = false) String name,
                                                                     @RequestParam(defaultValue = "0") int offset,
                                                                     @RequestParam(defaultValue = "1000") int limit) {
        AlbumSearchResponseDto albums = albumService.getAllAlbumsByName(name, offset, limit);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<AlbumResponseDto>> getAllAlbumsByArtist(@PathVariable long artistId) {
        List<AlbumResponseDto> albums = albumService.getAllAlbumsByArtist(artistId);
        return ResponseEntity.ok(albums);
    }
}
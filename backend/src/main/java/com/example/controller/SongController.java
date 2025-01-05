package com.example.controller;

import com.example.dto.song.SongResponseDto;
import com.example.dto.song.SongSearchResponseDto;
import com.example.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<SongSearchResponseDto> getAllSongsByName(@RequestParam(required = false) String name,
                                                                   @RequestParam(defaultValue = "0") int offset,
                                                                   @RequestParam(defaultValue = "1000") int limit) {
        SongSearchResponseDto songs = songService.getAllSongsByName(name, offset, limit);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongResponseDto>> getAllSongsByArtist(@PathVariable long artistId) {
        List<SongResponseDto> songs = songService.getAllSongsByArtist(artistId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SongResponseDto>> getAllSongsByAlbum(@PathVariable long albumId) {
        List<SongResponseDto> songs = songService.getAllSongsByAlbum(albumId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SongResponseDto>> getAllSongsByUser(@PathVariable long userId) {
        List<SongResponseDto> songs = songService.getAllSongsByUser(userId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<byte[]> getMp3ById(@PathVariable long songId) {
        byte[] songData = songService.getMp3ById(songId);
        return ResponseEntity.ok().header("Content-Type", "audio/mpeg").body(songData);
    }
}
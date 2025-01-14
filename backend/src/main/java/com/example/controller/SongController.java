package com.example.controller;

import com.example.dto.song.SongResponseDto;
import com.example.dto.song.SongSearchResponseDto;
import com.example.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing music tracks.
 */
@RestController
@RequestMapping("/songs")
@Tag(name = "songs", description = "API for managing music tracks")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    @Operation(summary = "Get a list of songs", description = "Returns a list of songs matching the provided name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of songs successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @Cacheable
    public ResponseEntity<SongSearchResponseDto> getAllSongsByName(
            @Parameter(description = "Song name to search for (optional)") @RequestParam(required = false) String name,
            @Parameter(description = "Pagination offset (default 0)") @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "Maximum number of results (default 1000)") @RequestParam(defaultValue = "1000") int limit) {
        SongSearchResponseDto songs = songService.getAllSongsByName(name, offset, limit);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/artist/{artistId}")
    @Operation(summary = "Get songs by artist ID", description = "Returns a list of songs related to the specified artist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of songs successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Songs not found")
    })
    public ResponseEntity<List<SongResponseDto>> getAllSongsByArtist(
            @Parameter(description = "Artist ID") @PathVariable long artistId) {
        List<SongResponseDto> songs = songService.getAllSongsByArtist(artistId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/album/{albumId}")
    @Operation(summary = "Get songs by album ID", description = "Returns a list of songs related to the specified album.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of songs successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Songs not found")
    })
    public ResponseEntity<List<SongResponseDto>> getAllSongsByAlbum(
            @Parameter(description = "Album ID") @PathVariable long albumId) {
        List<SongResponseDto> songs = songService.getAllSongsByAlbum(albumId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get songs by user ID", description = "Returns a list of songs added by the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of songs successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Songs not found")
    })
    public ResponseEntity<List<SongResponseDto>> getAllSongsByUser(
            @Parameter(description = "User ID") @PathVariable long userId) {
        List<SongResponseDto> songs = songService.getAllSongsByUser(userId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{songId}")
    @Operation(summary = "Get MP3 file by song ID", description = "Returns the MP3 file of the song by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "MP3 file successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Song not found")
    })
    public ResponseEntity<byte[]> getMp3ById(
            @Parameter(description = "Song ID") @PathVariable long songId) {
        byte[] songData = songService.getMp3ById(songId);
        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(songData);
    }
}

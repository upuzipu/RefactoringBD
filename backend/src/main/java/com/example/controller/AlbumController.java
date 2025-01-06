package com.example.controller;

import com.example.dto.album.AlbumResponseDto;
import com.example.dto.album.AlbumSearchResponseDto;
import com.example.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling requests related to albums.
 * Provides APIs to retrieve album information by name and artist.
 */
@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
@Tag(name = "albums", description = "Controller for managing albums")
public class AlbumController {

    private final AlbumService albumService;

    /**
     * Returns a list of albums matching the specified name.
     *
     * @param name   (optional) the name of the album to search for.
     * @param offset the offset for pagination (default is 0).
     * @param limit  the maximum number of results (default is 1000).
     * @return a {@link ResponseEntity} containing {@link AlbumSearchResponseDto} with search results.
     */
    @GetMapping
    @Operation(summary = "Get a list of albums by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of albums",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumSearchResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
    public ResponseEntity<AlbumSearchResponseDto> getAllAlbumsByName(
            @Parameter(description = "The name of the album to search for", example = "Thriller")
            @RequestParam(required = false) String name,
            @Parameter(description = "The offset for pagination", example = "0")
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "The maximum number of results", example = "1000")
            @RequestParam(defaultValue = "1000") int limit) {
        AlbumSearchResponseDto albums = albumService.getAllAlbumsByName(name, offset, limit);
        return ResponseEntity.ok(albums);
    }

    /**
     * Returns a list of albums associated with the specified artist.
     *
     * @param artistId the identifier of the artist.
     * @return a {@link ResponseEntity} containing a list of {@link AlbumResponseDto}.
     */
    @GetMapping("/artist/{artistId}")
    @Operation(summary = "Get a list of albums by artist ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of albums",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found", content = @Content)
    })
    public ResponseEntity<List<AlbumResponseDto>> getAllAlbumsByArtist(
            @Parameter(description = "The identifier of the artist", example = "12345")
            @PathVariable long artistId) {
        List<AlbumResponseDto> albums = albumService.getAllAlbumsByArtist(artistId);
        return ResponseEntity.ok(albums);
    }
}

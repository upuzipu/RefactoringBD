package com.example.controller;

import com.example.dto.artist.ArtistResponseDto;
import com.example.dto.artist.ArtistSearchResponseDto;
import com.example.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling requests related to artists.
 * Provides APIs to retrieve information about artists by name or identifier.
 */
@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@Tag(name = "artists", description = "Controller for managing artists")
public class ArtistController {

    private final ArtistService artistService;

    /**
     * Returns a list of artists matching the specified name.
     *
     * @param name   (optional) the name of the artist to search for.
     * @param offset the offset for pagination (default is 0).
     * @param limit  the maximum number of results (default is 1000).
     * @return a {@link ResponseEntity} containing {@link ArtistSearchResponseDto} with search results.
     */
    @GetMapping
    @Operation(summary = "Get a list of artists by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of artists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistSearchResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
    @Cacheable
    public ResponseEntity<ArtistSearchResponseDto> getAllArtistsByName(
            @Parameter(description = "The name of the artist to search for", example = "Michael Jackson")
            @RequestParam(required = false) String name,
            @Parameter(description = "The offset for pagination", example = "0")
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "The maximum number of results", example = "1000")
            @RequestParam(defaultValue = "1000") int limit) {
        ArtistSearchResponseDto artists = artistService.getAllArtistsByName(name, offset, limit);
        return ResponseEntity.ok(artists);
    }

    /**
     * Returns information about an artist by the specified identifier.
     *
     * @param artistId the identifier of the artist.
     * @return a {@link ResponseEntity} containing {@link ArtistResponseDto}.
     */
    @GetMapping("/{artistId}")
    @Operation(summary = "Get artist information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved artist information",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found", content = @Content)
    })
    public ResponseEntity<ArtistResponseDto> getArtistById(
            @Parameter(description = "The identifier of the artist", example = "12345")
            @PathVariable long artistId) {
        ArtistResponseDto artist = artistService.getArtistById(artistId);
        return ResponseEntity.ok(artist);
    }
}

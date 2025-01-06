package com.example.controller;

import com.example.dto.playlist.PlaylistCreationResponseDto;
import com.example.dto.playlist.PlaylistResponseDto;
import com.example.dto.playlist.PlaylistSearchResponseDto;
import com.example.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing playlists.
 */
@RestController
@RequestMapping("/playlists")
@Tag(name = "playlists", description = "API for managing playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    @Operation(summary = "Get a list of playlists", description = "Returns a list of playlists matching the provided name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of playlists successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<PlaylistSearchResponseDto> getAllPlaylistsByName(
            @Parameter(description = "Playlist name to search for") @RequestParam(required = false) String name,
            @Parameter(description = "Pagination offset (default 0)") @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "Maximum number of results (default 1000)") @RequestParam(defaultValue = "1000") int limit) {
        PlaylistSearchResponseDto playlists = playlistService.getAllPlaylistsByName(name, offset, limit);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/creator/{creatorId}")
    @Operation(summary = "Get playlists by creator ID", description = "Returns a list of playlists created by the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of playlists successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Playlists not found")
    })
    public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylistsByCreator(
            @Parameter(description = "Creator ID") @PathVariable long creatorId) {
        List<PlaylistResponseDto> playlists = playlistService.getAllPlaylistsByCreator(creatorId);
        return ResponseEntity.ok(playlists);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    @Operation(summary = "Add a song to a playlist", description = "Adds a music track to the specified playlist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Track successfully added"),
            @ApiResponse(responseCode = "401", description = "User not authorized")
    })
    public ResponseEntity<Void> addMusicToPlaylist(
            @Parameter(description = "Playlist ID") @PathVariable long playlistId,
            @Parameter(description = "Music track ID") @PathVariable long songId) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            playlistService.addMusicToPlaylist(playlistId, songId, user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    @Operation(summary = "Remove a song from a playlist", description = "Removes a music track from the specified playlist.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Track successfully removed"),
            @ApiResponse(responseCode = "401", description = "User not authorized")
    })
    public ResponseEntity<Void> removeMusicFromPlaylist(
            @Parameter(description = "Playlist ID") @PathVariable long playlistId,
            @Parameter(description = "Music track ID") @PathVariable long songId) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            playlistService.removeMusicFromPlaylist(playlistId, songId, user.getUsername(), null);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new playlist", description = "Creates a new playlist on behalf of the current user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playlist successfully created"),
            @ApiResponse(responseCode = "401", description = "User not authorized")
    })
    public ResponseEntity<PlaylistCreationResponseDto> addNewPlaylist(
            @Parameter(description = "Name of the new playlist") @RequestParam String playlistName) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long playlistId = playlistService.addNewPlaylist(user.getUsername(), playlistName);
            return ResponseEntity.ok(new PlaylistCreationResponseDto(playlistId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

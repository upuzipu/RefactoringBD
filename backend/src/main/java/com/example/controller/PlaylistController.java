package com.example.controller;

import com.example.dto.playlist.PlaylistCreationDTO;
import com.example.dto.playlist.PlaylistCreationResponseDto;
import com.example.dto.playlist.PlaylistResponseDto;
import com.example.dto.playlist.PlaylistSearchResponseDto;
import com.example.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<PlaylistSearchResponseDto> getAllPlaylistsByName(@RequestParam(required = false) String name,
                                                                           @RequestParam(defaultValue = "0") int offset,
                                                                           @RequestParam(defaultValue = "1000") int limit) {
        PlaylistSearchResponseDto playlists = playlistService.getAllPlaylistsByName(name, offset, limit);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylistsByCreator(@PathVariable long creatorId) {
        List<PlaylistResponseDto> playlists = playlistService.getAllPlaylistsByCreator(creatorId);
        return ResponseEntity.ok(playlists);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addMusicToPlaylist(@PathVariable long playlistId,
                                                   @PathVariable long songId
                                                  ) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            playlistService.addMusicToPlaylist(playlistId, songId, user.getUsername());
            return ResponseEntity.status(200).build();

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeMusicFromPlaylist(@PathVariable long playlistId,
                                                        @PathVariable long songId,
                                                        @RequestBody String requestBody) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            playlistService.removeMusicFromPlaylist(playlistId, songId, user.getUsername(), requestBody);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<PlaylistCreationResponseDto> addNewPlaylist(@RequestBody PlaylistCreationDTO requestBody) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long playlistId =  playlistService.addNewPlaylist(user.getUsername(), requestBody.getPlaylistName());
            return ResponseEntity.ok(new PlaylistCreationResponseDto(playlistId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
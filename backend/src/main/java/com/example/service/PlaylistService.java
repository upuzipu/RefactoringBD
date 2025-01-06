package com.example.service;

import com.example.dto.playlist.PlaylistResponseDto;
import com.example.dto.playlist.PlaylistSearchResponseDto;
import com.example.exception.AccessForbiddenException;
import com.example.exception.AlreadyInPlaylistException;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Playlist;
import com.example.repository.PlaylistRepository;
import com.example.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing playlists.
 * Handles creating, deleting, searching, and adding/removing songs in playlists.
 */
@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final DetailsService detailsService;

    /**
     * Retrieves all playlists with the option to filter by name and paginate.
     *
     * @param name the name of the playlist to filter by
     * @param offset the offset for pagination
     * @param limit the limit for pagination
     * @return a {@link PlaylistSearchResponseDto} containing playlist information, total count, and the current page
     */
    public PlaylistSearchResponseDto getAllPlaylistsByName(String name, int offset, int limit) {
        int count = (name != null && !name.isEmpty()) ?
                playlistRepository.countByNameContaining(name) :
                playlistRepository.count();
        List<Playlist> playlists = (name != null && !name.isEmpty()) ?
                playlistRepository.findByNameContaining(name, offset, limit) :
                playlistRepository.findAll(offset, limit);
        int totalPages = count / limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currentPage = offset / limit + 1;
        return new PlaylistSearchResponseDto(playlists.stream().map(this::mapToDto).toList(), count, currentPage, totalPages);
    }

    /**
     * Retrieves all playlists belonging to a specific creator.
     *
     * @param creatorId the ID of the playlist creator
     * @return a list of {@link PlaylistResponseDto} containing playlist information
     */
    public List<PlaylistResponseDto> getAllPlaylistsByCreator(long creatorId) {
        List<Playlist> playlists = playlistRepository.findByCreatorId(creatorId);
        return playlists.stream().map(this::mapToDto).toList();
    }

    /**
     * Adds a song to a playlist.
     * Checks if the song exists, if it's already in the playlist, and if the user has permission to modify the playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songId the ID of the song
     * @param username the username of the user
     */
    public void addMusicToPlaylist(long playlistId, long songId, String username) {
        if (!songRepository.existsById(songId)) {
            throw new EntityNotFoundException("Song not found");
        }
        if (playlistRepository.existsSongInPlaylist(playlistId, songId)) {
            throw new AlreadyInPlaylistException("Already in playlist");
        }
        if (playlistRepository.findByCreatorId(detailsService.getIdByEmail(username)).stream().noneMatch(playlist -> playlist.getPlaylistId() == playlistId)) {
            throw new AccessForbiddenException("Access denied");
        }
        playlistRepository.addSongToPlaylist(playlistId, songId);
    }

    /**
     * Removes a song from a playlist.
     * Checks if the song exists and if the user has permission to modify the playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songId the ID of the song
     * @param username the username of the user
     * @param requestBody the request body
     */
    public void removeMusicFromPlaylist(long playlistId, long songId, String username, String requestBody) {
        if (!songRepository.existsById(songId)) {
            throw new EntityNotFoundException("Song not found");
        }
        if (playlistRepository.findByCreatorId(detailsService.getIdByEmail(username)).stream().noneMatch(playlist -> playlist.getPlaylistId() == playlistId)) {
            throw new AccessForbiddenException("Access denied");
        }
        playlistRepository.removeSongFromPlaylist(playlistId, songId);
    }

    /**
     * Creates a new playlist for a user.
     *
     * @param username the username of the user
     * @param playlistName the name of the playlist
     * @return the ID of the created playlist
     */
    public long addNewPlaylist(String username, String playlistName) {
        long creatorId = detailsService.getIdByEmail(username);
        return playlistRepository.save(creatorId, playlistName);
    }

    /**
     * Converts a playlist object to a DTO.
     *
     * @param playlist the {@link Playlist} object to be converted
     * @return a {@link PlaylistResponseDto} containing playlist information
     */
    private PlaylistResponseDto mapToDto(Playlist playlist) {
        PlaylistResponseDto dto = new PlaylistResponseDto();
        dto.setId(playlist.getPlaylistId());
        dto.setName(playlist.getPlaylistName());
        dto.setCreator(playlist.getCreatorName());
        dto.setUpdateTime(playlist.getUpdateTime().toString());
        dto.setCreationDate(playlist.getCreationDate().toString());
        return dto;
    }
}

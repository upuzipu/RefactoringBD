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

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final DetailsService detailsService;

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

    public List<PlaylistResponseDto> getAllPlaylistsByCreator(long creatorId) {
        List<Playlist> playlists = playlistRepository.findByCreatorId(creatorId);
        return playlists.stream().map(this::mapToDto).toList();
    }

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

    public void removeMusicFromPlaylist(long playlistId, long songId, String username, String requestBody) {
        if (!songRepository.existsById(songId)) {
            throw new EntityNotFoundException("Song not found");
        }
        if (playlistRepository.findByCreatorId(detailsService.getIdByEmail(username)).stream().noneMatch(playlist -> playlist.getPlaylistId() == playlistId)) {
            throw new AccessForbiddenException("Access denied");
        }
        playlistRepository.removeSongFromPlaylist(playlistId, songId);
    }

    public long addNewPlaylist(String username, String playlistName) {
        long creatorId = detailsService.getIdByEmail(username);
        return playlistRepository.save(creatorId, playlistName);
    }

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
package com.example.repository;

import com.example.model.entity.Playlist;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for working with the {@link Playlist} entity.
 * This interface provides methods for performing various operations on playlist data,
 * such as searching by name, counting, and adding/removing songs from playlists.
 */
@RepositoryDefinition(domainClass = Playlist.class, idClass = Long.class)
public interface PlaylistRepository {

    /**
     * Find playlists whose name contains the given string.
     *
     * @param name the string to search in the playlist names
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of playlists whose name contains the given string
     */
    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id  where playlist_name like '%' || :name || '%' limit :limit offset :offset")
    List<Playlist> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Count the total number of playlists in the database.
     *
     * @return the total number of playlists
     */
    @Query("select count(*) from playlist")
    int count();

    /**
     * Count the number of playlists whose name contains the given string.
     *
     * @param name the string to search in the playlist names
     * @return the number of playlists whose name contains the given string
     */
    @Query("select count(*) from playlist where playlist_name like '%' || :name || '%'")
    int countByNameContaining(@Param("name") String name);

    /**
     * Retrieve all playlists with the option to specify a limit and offset for pagination.
     *
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of all playlists
     */
    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id limit :limit offset :offset")
    List<Playlist> findAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * Find playlists by the creator's ID.
     *
     * @param creatorId the ID of the playlist creator
     * @return a list of playlists created by the user with the given ID
     */
    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id where creator_id = :creatorId")
    List<Playlist> findByCreatorId(@Param("creatorId") long creatorId);

    /**
     * Check if a song exists in a playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songId the ID of the song
     * @return true if the song exists in the playlist, otherwise false
     */
    @Query("select exists(select 1 from playlist_songs where playlist_id = :playlistId and song_id = :songId)")
    boolean existsSongInPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    /**
     * Add a song to a playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songId the ID of the song
     */
    @Modifying
    @Query("insert into playlist_songs (playlist_id, song_id) values (:playlistId, :songId)")
    void addSongToPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    /**
     * Remove a song from a playlist.
     *
     * @param playlistId the ID of the playlist
     * @param songId the ID of the song
     */
    @Modifying
    @Query("delete from playlist_songs where playlist_id = :playlistId and song_id = :songId")
    void removeSongFromPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    /**
     * Create a new playlist.
     *
     * @param creatorId the ID of the playlist creator
     * @param name the name of the playlist
     * @return the ID of the created playlist
     */
    @Query("insert into playlist (playlist_id, creator_id, playlist_name) values (DEFAULT, :creatorId, :name) returning playlist_id")
    long save(@Param("creatorId") long creatorId, @Param("name") String name);
}

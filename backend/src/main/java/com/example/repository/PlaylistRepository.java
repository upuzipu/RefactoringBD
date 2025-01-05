package com.example.repository;

import com.example.model.entity.Playlist;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Playlist.class, idClass = Long.class)
public interface PlaylistRepository {

    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id  where playlist_name like '%' || :name || '%' limit :limit offset :offset")
    List<Playlist> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    @Query("select count(*) from playlist")
    int count();

    @Query("select count(*) from playlist where playlist_name like '%' || :name || '%'")
    int countByNameContaining(@Param("name") String name);

    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id limit :limit offset :offset")
    List<Playlist> findAll(@Param("offset") int offset, @Param("limit") int limit);

    @Query("select playlist_id, playlist_name, person.person_nickname as creator_name, creation_date, update_time from playlist join person on creator_id=person_id where creator_id = :creatorId")
    List<Playlist> findByCreatorId(@Param("creatorId") long creatorId);

    @Query("select exists(select 1 from playlist_songs where playlist_id = :playlistId and song_id = :songId)")
    boolean existsSongInPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    @Modifying
    @Query("insert into playlist_songs (playlist_id, song_id) values (:playlistId, :songId)")
    void addSongToPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    @Modifying
    @Query("delete from playlist_songs where playlist_id = :playlistId and song_id = :songId")
    void removeSongFromPlaylist(@Param("playlistId") long playlistId, @Param("songId") long songId);

    @Query("insert into playlist (playlist_id, creator_id, playlist_name) values (DEFAULT, :creatorId, :name) returning playlist_id")
    long save(@Param("creatorId") long creatorId, @Param("name") String name);
}
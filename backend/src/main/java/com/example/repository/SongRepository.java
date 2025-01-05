package com.example.repository;

import com.example.model.entity.Song;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Song.class, idClass = Long.class)
public interface SongRepository {

    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name from song join artist using (artist_id) join jenre using (jenre_id) where song_id = :songId")
    Optional<Song> findById(@Param("songId") long songId);

    @Query("select exists(select 1 from song where song_id = :songId)")
    boolean existsById(@Param("songId") long songId);

    @Query("select count(*) from song")
    int count();

    @Query("select count(*) from song where song_name like '%' || :name || '%' ")
    int countByNameContaining(String name);

    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) where artist_id = :artistId")
    List<Song> findByArtistId(@Param("artistId") long artistId);

    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) join album_songs using (song_id)  WHERE album_id = :albumId")
    List<Song> findByAlbumId(@Param("albumId") long albumId);

    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) join person_favourite_song using (song_id) WHERE person_id = :userId")
    List<Song> findByUserId(@Param("userId") long userId);


    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) WHERE song_name like '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Song> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) LIMIT :limit OFFSET :offset")
    List<Song> findAll(@Param("offset") int offset, @Param("limit") int limit);

    @Modifying
    @Query("insert into song (song_id, song_name, artist_id, genre_id) values (:songId, :songName, :artistId, :genreId)")
    void save(@Param("songId") long songId, @Param("songName") String songName, @Param("artistId") long artistId, @Param("genreId") long genreId);

    @Modifying
    @Query("delete from song where song_id = :songId")
    void deleteById(@Param("songId") long songId);
}
package com.example.repository;

import com.example.model.entity.Album;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends Repository<Album, Long> {

    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE album_id = :albumId")
    Optional<Album> findById(@Param("albumId") long albumId);

    @Query("select count(*) from album")
    int count();

    @Query("select count(*) from album where name like '%' || :name || '%' ")
    int countByNameContaining(String name);

    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE creator_id = :artistId")
    List<Album> findByArtistId(@Param("artistId") long artistId);

    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE name like '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Album> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id LIMIT :limit OFFSET :offset")
    List<Album> findAll(@Param("offset") int offset, @Param("limit") int limit);
}
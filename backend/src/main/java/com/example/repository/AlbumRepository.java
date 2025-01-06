package com.example.repository;

import com.example.model.entity.Album;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for working with the {@link Album} entity.
 * This interface provides methods for performing various operations on album data.
 */
public interface AlbumRepository extends Repository<Album, Long> {

    /**
     * Find an album by its ID.
     *
     * @param albumId the ID of the album
     * @return the album with the given ID, wrapped in an Optional
     */
    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE album_id = :albumId")
    Optional<Album> findById(@Param("albumId") long albumId);

    /**
     * Count the total number of albums in the database.
     *
     * @return the total number of albums
     */
    @Query("select count(*) from album")
    int count();

    /**
     * Count the number of albums whose name contains the given string.
     *
     * @param name part of the album name to search for
     * @return the number of albums whose name contains the given string
     */
    @Query("select count(*) from album where name like '%' || :name || '%' ")
    int countByNameContaining(String name);

    /**
     * Find albums by the artist's ID.
     *
     * @param artistId the ID of the artist
     * @return a list of albums created by the given artist
     */
    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE creator_id = :artistId")
    List<Album> findByArtistId(@Param("artistId") long artistId);

    /**
     * Find albums whose name contains the given string, with the option to specify
     * a limit and offset for pagination.
     *
     * @param name part of the album name to search for
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of albums whose name contains the given string
     */
    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id WHERE name like '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Album> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Retrieve all albums with the option to specify a limit and offset for pagination.
     *
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of all albums
     */
    @Query("select album.*, artist.nickname as album_creator_name from album join artist on album_creator=artist_id LIMIT :limit OFFSET :offset")
    List<Album> findAll(@Param("offset") int offset, @Param("limit") int limit);
}

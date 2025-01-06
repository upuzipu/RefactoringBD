package com.example.repository;

import com.example.model.entity.Artist;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for working with the {@link Artist} entity.
 * This interface provides methods for performing various operations on artist data.
 */
public interface ArtistRepository extends Repository<Artist, Long> {

    /**
     * Find an artist by their ID.
     *
     * @param artistId the ID of the artist
     * @return the artist with the given ID, wrapped in an Optional
     */
    @Query("SELECT * FROM artist WHERE artist_id = :artistId")
    Optional<Artist> findById(@Param("artistId") long artistId);

    /**
     * Count the total number of artists in the database.
     *
     * @return the total number of artists
     */
    @Query("select count(*) from artist")
    int count();

    /**
     * Count the number of artists whose name (or nickname) contains the given string.
     *
     * @param name part of the artist's name to search for
     * @return the number of artists whose name contains the given string
     */
    @Query("select count(*) from artist where nickname like '%' || :name || '%' ")
    int countByNameContaining(String name);

    /**
     * Find artists whose name contains the given string, with the option to specify
     * a limit and offset for pagination.
     *
     * @param name part of the artist's name to search for
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of artists whose name contains the given string
     */
    @Query("SELECT * FROM artist WHERE nickname LIKE '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Artist> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Retrieve all artists with the option to specify a limit and offset for pagination.
     *
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of all artists
     */
    @Query("SELECT * FROM artist LIMIT :limit OFFSET :offset")
    List<Artist> findAll(@Param("offset") int offset, @Param("limit") int limit);
}

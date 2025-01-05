package com.example.repository;

import com.example.model.entity.Artist;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Repository<Artist, Long> {

    @Query("SELECT * FROM artist WHERE artist_id = :artistId")
    Optional<Artist> findById(@Param("artistId") long artistId);

    @Query("select count(*) from artist")
    int count();

    @Query("select count(*) from artist where nickname like '%' || :name || '%' ")
    int countByNameContaining(String name);

    @Query("SELECT * FROM artist WHERE nickname LIKE '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Artist> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT * FROM artist LIMIT :limit OFFSET :offset")
    List<Artist> findAll(@Param("offset") int offset, @Param("limit") int limit);
}
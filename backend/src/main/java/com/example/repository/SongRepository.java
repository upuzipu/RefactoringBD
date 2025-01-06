package com.example.repository;

import com.example.model.entity.Song;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for working with the {@link Song} entity.
 * This interface provides methods for performing various operations on songs, such as searching by name,
 * artist, album, as well as adding and removing songs from the database.
 */
@RepositoryDefinition(domainClass = Song.class, idClass = Long.class)
public interface SongRepository {

    /**
     * Find a song by its ID.
     *
     * @param songId the ID of the song
     * @return an Optional containing the found song or empty if the song is not found
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name from song join artist using (artist_id) join jenre using (jenre_id) where song_id = :songId")
    Optional<Song> findById(@Param("songId") long songId);

    /**
     * Check if a song exists with the given ID.
     *
     * @param songId the ID of the song
     * @return true if the song exists, otherwise false
     */
    @Query("select exists(select 1 from song where song_id = :songId)")
    boolean existsById(@Param("songId") long songId);

    /**
     * Count the total number of songs in the database.
     *
     * @return the total number of songs
     */
    @Query("select count(*) from song")
    int count();

    /**
     * Count the number of songs whose name contains the given string.
     *
     * @param name the string to search in the song names
     * @return the number of songs whose name contains the given string
     */
    @Query("select count(*) from song where song_name like '%' || :name || '%' ")
    int countByNameContaining(String name);

    /**
     * Find songs by the artist's ID.
     *
     * @param artistId the ID of the artist
     * @return a list of songs by the artist with the given ID
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) where artist_id = :artistId")
    List<Song> findByArtistId(@Param("artistId") long artistId);

    /**
     * Find songs by the album's ID.
     *
     * @param albumId the ID of the album
     * @return a list of songs from the album with the given ID
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) join album_songs using (song_id)  WHERE album_id = :albumId")
    List<Song> findByAlbumId(@Param("albumId") long albumId);

    /**
     * Find songs by the user's ID (user's favorite songs).
     *
     * @param userId the ID of the user
     * @return a list of songs that are favorites of the user with the given ID
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) join person_favourite_song using (song_id) WHERE person_id = :userId")
    List<Song> findByUserId(@Param("userId") long userId);

    /**
     * Find songs whose name contains the given string with pagination.
     *
     * @param name the string to search in the song names
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of songs whose name contains the given string
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) WHERE song_name like '%' || :name || '%' LIMIT :limit OFFSET :offset")
    List<Song> findByNameContaining(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Retrieve all songs with pagination.
     *
     * @param offset the offset for pagination
     * @param limit the limit of results
     * @return a list of all songs
     */
    @Query("select song.*, artist.nickname as artist_name, jenre.jenre_name as jenre_name from song join artist using (artist_id) join jenre using (jenre_id) LIMIT :limit OFFSET :offset")
    List<Song> findAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * Save a new song to the database.
     *
     * @param songId the ID of the song
     * @param songName the name of the song
     * @param artistId the ID of the artist
     * @param genreId the ID of the genre
     */
    @Modifying
    @Query("insert into song (song_id, song_name, artist_id, genre_id) values (:songId, :songName, :artistId, :genreId)")
    void save(@Param("songId") long songId, @Param("songName") String songName, @Param("artistId") long artistId, @Param("genreId") long genreId);

    /**
     * Delete a song by its ID.
     *
     * @param songId the ID of the song
     */
    @Modifying
    @Query("delete from song where song_id = :songId")
    void deleteById(@Param("songId") long songId);
}

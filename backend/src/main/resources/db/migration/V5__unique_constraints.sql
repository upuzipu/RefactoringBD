ALTER TABLE person_favourite_song ADD UNIQUE (person_id, song_id);
            ALTER TABLE album_songs ADD UNIQUE (album_id, song_id);
CREATE OR REPLACE FUNCTION update_playlist_edit_time_function() RETURNS trigger AS $$
                BEGIN
                  UPDATE playlist SET update_time=CURRENT_TIMESTAMP WHERE playlist_id = NEW.playlist_id;
                  RETURN NULL;
                END;
            $$ LANGUAGE plpgsql;
CREATE OR REPLACE TRIGGER update_time_trigger AFTER INSERT on playlist_songs
    FOR EACH STATEMENT
    EXECUTE FUNCTION update_playlist_edit_time_function();
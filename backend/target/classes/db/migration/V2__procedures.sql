CREATE OR REPLACE PROCEDURE add_user(
username text,
email text,
password text
)
language plpgsql
    as $$
    begin
        insert into person (person_nickname, person_email) values(username,email);
        insert into users_password(person_id, password) values (currval('person_id_seq'), password);
    end; $$
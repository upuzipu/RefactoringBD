# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Album(models.Model):
    album_id = models.AutoField(primary_key=True)
    name = models.TextField(blank=True, null=True)
    album_creator = models.ForeignKey('Artist', models.DO_NOTHING, db_column='album_creator', blank=True, null=True)
    creation_date = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'album'


class Artist(models.Model):
    artist_id = models.AutoField(primary_key=True)
    nickname = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'artist'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=150)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.BooleanField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=150)
    last_name = models.CharField(max_length=150)
    email = models.CharField(max_length=254)
    is_staff = models.BooleanField()
    is_active = models.BooleanField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    id = models.BigAutoField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class Jenre(models.Model):
    jenre_id = models.AutoField(primary_key=True)
    jenre_name = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'jenre'


class Person(models.Model):
    person_id = models.AutoField(primary_key=True)
    person_nickname = models.TextField(unique=True, blank=True, null=True)
    person_email = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'person'





class Playlist(models.Model):
    playlist_id = models.AutoField(primary_key=True)
    creator = models.ForeignKey(Person, models.DO_NOTHING, blank=True, null=True)
    playlist_name = models.TextField(blank=True, null=True)
    creation_date = models.DateTimeField(blank=True, null=True)
    update_time = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'playlist'



class Song(models.Model):
    song_id = models.AutoField(primary_key=True)
    song_name = models.TextField(blank=True, null=True)
    song_url = models.TextField(blank=True, null=True)
    artist = models.ForeignKey(Artist, models.DO_NOTHING, blank=True, null=True)
    jenre = models.ForeignKey(Jenre, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'song'


class UsersPassword(models.Model):
    person = models.ForeignKey(Person, models.DO_NOTHING, blank=True, null=True)
    password = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'users_password'
class PlaylistSongs(models.Model):
    playlist = models.ForeignKey(Playlist, models.DO_NOTHING, blank=True, null=True)
    song = models.ForeignKey(Song, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'playlist_songs'
class AlbumSongs(models.Model):
    album = models.ForeignKey(Album, models.DO_NOTHING, blank=True, null=True)
    song = models.ForeignKey(Song, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'album_songs'

class PersonFavouriteSong(models.Model):
    person = models.ForeignKey(Person, models.DO_NOTHING, blank=True, null=True)
    song = models.ForeignKey(Song, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'person_favourite_song'

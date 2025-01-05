from django.urls import path

from .views import views, song_views, album_views,playlist_views, artist_views


urlpatterns = [
    path("", views.index, name="index"),
    path("songs", song_views.getAllSongsByName, name = "songs"),
    path("songs/<int:id>/mp3", song_views.getMp3ById, name = "mp3"),
    path("albums", album_views.getAllAlbumsByName, name = "albums"),
    path("albums/<int:album_id>/songs", song_views.getAllSongsByAlbumId, name = "songsByArtist"),
    path("playlists", playlist_views.getAllPlaylistByName, name = "playlistByName"),
    path("playlists/<int:playlist_id>/songs", song_views.listAllSongsByPlaylistId, name = "listByPlaylist"),
    path("playlists/create", playlist_views.addNewPlaylist, name="createPlaylist"),
    path("playlists/<int:playlist_id>/songs/<int:song_id>/add", playlist_views.addMusicToPlaylist),
    path("playlists/<int:playlist_id>/songs/<int:song_id>/remove", playlist_views.removeMusicFromPlaylist),
    path("users/<int:user_id>/songs", song_views.getAllSongsByUserId, name = "favourites"),
    path("users/<int:user_id>/playlists", playlist_views.getAllPlaylistsByCreator, name="byCreator"),
    path("users/register", views.register),
    path("users/login", views.login),
    path("songs/<int:song_id>/add-to-favourites", views.addMusicToFavourites),
    path("users", views.getAllUsersByNickname),
    path("artists", artist_views.getAllArtistsByName),
    path("artists/<int:artist_id>/songs", song_views.getAllSongsByArtistId, name = "artistSongs"),

    

]
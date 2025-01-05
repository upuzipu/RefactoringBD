from django.db import connection
from ..models import *
import json
# Create your views here.
from django.http import HttpResponse
import os

def songsToJson(songs):
    httpResponse = {}
    responses = []
    for i in songs:
        response = {}
        response['id'] = i.song_id
        response['name'] = i.song_name
        response['artist'] =i.artist.nickname
        response['jenre'] = i.jenre.jenre_name
        print(i.song_name)
        responses.append(response)
    httpResponse["result"] = 'success'
    httpResponse["body"] = responses
    return HttpResponse(json.dumps(httpResponse, ensure_ascii=False))
def bigSongToJson(songs):
    httpResponse = {}
    responses = []
    for i in songs:
        response = {}
        response['id'] = i.song.song_id
        response['name'] = i.song.song_name
        response['artist'] =i.song.artist.nickname
        response['jenre'] = i.song.jenre.jenre_name
        responses.append(response)
    httpResponse["result"] = 'success'
    httpResponse["body"] = responses
    return HttpResponse(json.dumps(httpResponse,ensure_ascii=False))



def getPahtByMusic(song: Song):
    pathToMusic = "freakify/recources/songs/" + song.song_url
    return pathToMusic
def getMusicByPathToResponse(pathToMusic):
     with open(pathToMusic, 'rb') as f:
        response = HttpResponse(f.read(),content_type = 'audio/mp3')
     response['Content-Length'] = os.path.getsize(pathToMusic)
     return response

def listAllSongsByPlaylistId(request, playlist_id):
    songs = PlaylistSongs.objects.select_related('song').filter(playlist_id__exact=playlist_id)
    return bigSongToJson(songs)

def getMp3ById(request, id):
    music = Song.objects.get(song_id__exact = id)
    pathToMusic = getPahtByMusic(music)
    return getMusicByPathToResponse(pathToMusic)

def getAllSongsByName(request):
    name = request.GET.get('name','')
    offset = int(request.GET.get('offset',0))
    limit = int(request.GET.get('limit', 1000)) + offset
    if(name != ''):
        music= Song.objects.filter(song_name__icontains=name)[offset:limit]
    else:
        music = Song.objects.all()[offset:limit]
    return songsToJson(music)

def getAllSongsByArtistId(request,artist_id):
    music= Song.objects.filter(artist__exact=artist_id)
    return songsToJson(music)


def getAllSongsByAlbumId(request,album_id):
    songs = AlbumSongs.objects.select_related('song').filter(album_id__exact=album_id)
    return bigSongToJson(songs)

def getAllSongsByUserId(request,user_id):
    songs = PersonFavouriteSong.objects.select_related('song').filter(person__exact=user_id)
    return bigSongToJson(songs)






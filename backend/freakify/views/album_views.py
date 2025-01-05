from django.db import connection
from ..models import *
import json
# Create your views here.
from django.http import HttpResponse
import os

def albumsToJson(albums):
    httpResponse = {}
    responses = []
    for i in albums:
        response = {}
        response['id'] = i.album_id
        response['name'] = i.name
        response['artist'] =i.album_creator.nickname
        response['creation_date'] = str(i.creation_date)
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


def getAllAlbumsByName(request):
    name = request.GET.get('name','')
    offset = int(request.GET.get('offset',0))
    limit = int(request.GET.get('limit', 1000)) + offset
    if(name != ''):
        music= Album.objects.filter(name__icontains=name)[offset:limit]
    else:
        music = Album.objects.all()[offset:limit]
    return albumsToJson(music)

def getAllbumsByArtistId(request,artist_id):
    albums= Album.objects.filter(creator_id__exact=artist_id)
    return albumsToJson(albums)





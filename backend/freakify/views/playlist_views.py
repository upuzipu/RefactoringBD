from django.db import connection
from ..models import *
import json
# Create your views here.
from django.http import HttpResponse
from ..utils import *
import os
from django.views.decorators.csrf import csrf_exempt
from datetime import datetime




def playlistToJson(albums):
    httpResponse = {}
    responses = []
    for i in albums:
        response = {}
        response['id'] = i.playlist_id
        response['name'] = str(i.playlist_name)
        response['creator'] =i.creator.person_nickname
        response['update_time'] = str(i.update_time)
        response['creation_date'] = str(i.creation_date)
        responses.append(response)
    httpResponse["result"] = 'success'
    httpResponse["body"] = responses
    return HttpResponse(json.dumps(httpResponse,ensure_ascii=False))

def authorized(data):
    email = data['email']
    password = data['password']
    print(email)
    print(password)
    person = Person.objects.get(person_email__exact=email)
    print(person.person_id)
    passwords = UsersPassword.objects.get(person__exact=person.person_id)
    print(passwords.password)
    if passwords.password != hashPassword(password):
        raise Exception("Unauthorized")
    return passwords.person.person_id
def hasAuthority(data, playlist_id):
    playlist = Playlist.objects.get(playlist_id=playlist_id)
    if playlist.creator.person_id != authorized(data):
        raise Exception("Unauthorized")

    


def getAllPlaylistByName(request):
    name = request.GET.get('name','')
    offset = request.GET.get('offset',0)
    limit = request.GET.get('limit', 1000)
    if(name != ''):
        music= Playlist.objects.filter(playlist_name__icontains=name)[offset:limit]
    else:
        music = Playlist.objects.all()[offset:limit]
    return playlistToJson(music)

def getAllPlaylistsByCreator(request,creator_id):
    albums= Playlist.objects.filter(creator__exact=creator_id)
    return playlistToJson(albums)
@csrf_exempt
def addMusicToPlaylist(request, playlist_id,song_id):
    if(request.method!="POST"):
        return returnErrorResponse(405, "ALLOWED METHOD: POST")

    data = json.loads(request.body.decode())
    try:
        hasAuthority(data,playlist_id)
    except:
        return returnErrorResponse(403, "Не авторизован")
    if Song.objects.filter(song_id__exact=song_id).exists():
        try:
            PlaylistSongs(song = Song.objects.get(song_id=song_id),playlist = Playlist.objects.get(playlist_id=playlist_id)).save()
        except:
                return returnErrorResponse(409, "Already in playlist")

    else:
        return returnErrorResponse(404, "Песня не найдена")
    return returnSuccessResponse(201)
    
        

@csrf_exempt
def removeMusicFromPlaylist(request, playlist_id,song_id):
    if(request.method == "PSOT"):
        data = json.loads(request.body.decode())
        
        try:
            hasAuthority(data,playlist_id)
        except:
                return returnErrorResponse(403, "Не авторизован")
        if Song.objects.filter(song_id__exact=song_id).exists():
            PlaylistSongs.objects.filter(song_id=song_id).filter(playlist_id=playlist_id).delete()
        else:
            return returnErrorResponse(404, "Песня не найдена")
        return returnSuccessResponse(201)
    
    else:return returnErrorResponse(405, "ALLOWED METHOD: POST")

@csrf_exempt
def addNewPlaylist(request):
    if(request.method=="POST"):
        data = json.loads(request.body.decode())
        try:
            date =datetime.now()
            creator_id = authorized(data)
        except:
            return returnErrorResponse(403, "Не авторизован")
        print(creator_id)
        name= data['playlist_name']
        Playlist(creator = Person.objects.get(person_id__exact=creator_id),playlist_name=name, creation_date=str(date), update_time = str(date)).save()
        return returnSuccessResponse(201)
    else:return returnErrorResponse(405, "ALLOWED METHOD: POST")

        
    
    

    
    
    

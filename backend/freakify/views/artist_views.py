from django.db import connection
from ..models import *
import json
# Create your views here.
from django.http import HttpResponse
import os

def artistToJson(albums):
    httpResponse = {}
    responses = []
    for i in albums:
        response = {}
        response['id'] = i.artist_id
        response['name'] = i.nickanme
        responses.append(response)
    httpResponse["result"] = 'success'
    httpResponse["body"] = responses
    return HttpResponse(json.dumps(httpResponse,ensure_ascii=False)) 


def getAllArtistsByName(request):
    name = request.GET.get('name','')
    offset = int(request.GET.get('offset',0))
    limit = int(request.GET.get('limit', 1000)) + offset
    if(name != ''):
        music= Artist.objects.filter(nickname__icontains=name)[offset:limit]
    else:
        music = Artist.objects.all()[offset:limit]
    return artistToJson(music)

def getArtistById(request,artist_id):
    artist= Artist.objects.filter(artist_id__exact=artist_id)
    return HttpResponse(artistToJson(artist))





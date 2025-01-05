from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.db import connection

# Create your views here.
from django.http import HttpResponse
from ..models import *
import os
from django.core.serializers.json import DjangoJSONEncoder
from django.core.serializers import serialize
from ..utils import *
import json


def personToJson(albums):
    httpResponse = {}
    responses = []
    for i in albums:
        response = {}
        response['id'] = i.person_id
        response['nickname'] = i.person_nickname
        responses.append(response)
    httpResponse["result"] = 'success'
    httpResponse["body"] = responses
    return HttpResponse(json.dumps(httpResponse,ensure_ascii=False)) 


def index(request):
    return HttpResponse("ты пидор")
    
@csrf_exempt
def register(request):
    if(request.method!="POST"):
        return returnErrorResponse(405, "ALLOWED METHOD: POST")
    cursor = connection.cursor()
   
    if request.method == 'POST':
        print(request.POST)
        data = json.loads(request.body.decode())
        try:
            cursor.execute("call add_user(%s,%s,%s)",[data.get('username'),data.get('email'),hashPassword(data.get('password'))])
        finally:
            cursor.close()
        
    return HttpResponse("Registration done")

@csrf_exempt
def login(request):
    if(request.method!="POST"):
        return returnErrorResponse(405, "ALLOWED METHOD: POST")
    data = json.loads(request.body.decode())
    try:
        email = data['email']
        password = data['password']
        person = Person.objects.get(person_email__exact=email)
        passwords = UsersPassword.objects.get(person__exact=person.person_id)
        if passwords.password != hashPassword(password):
            return returnErrorResponse(403, "Wrong login or password")
        return HttpResponse(person.person_nickname)
    except:
        return returnErrorResponse(403, "Wrong login or password")
   
def getAllUsersByNickname(request):
    name = request.GET.get('name','')
    offset = int(request.GET.get('offset',0))
    limit = int(request.GET.get('limit', 1000)) + offset
    if(name != ''):
        music= Person.objects.filter(person_nickname__icontains=name)[offset:limit]
    else:
        music = Person.objects.all()[offset:limit]
    return personToJson(music)

@csrf_exempt 
def addMusicToFavourites(request,song_id):
    if request.method == "POST":
        data = json.loads(request.body.decode())
        id = ""
        try:
            id = authorized(data)
        except:
            return returnErrorResponse(403, "Wrong login or password") 
        if Song.objects.filter(song_id__exact=song_id).exists():
            try:
                PersonFavouriteSong(person = Person.objects.get(person_id=id), song = Song.objects.get(song_id=song_id)).save()
            except:
                return returnErrorResponse(409, "Already in favourites")
            
        else:
            return returnErrorResponse(404, "Песня не найдена")
        return returnSuccessResponse(201)
    else:
        return returnErrorResponse(405, "ALLOWED METHOD: POST")
    
        


    


        
        
        
        






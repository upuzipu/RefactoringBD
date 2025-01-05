from hashlib import pbkdf2_hmac
import json
# Create your views here.
from django.http import HttpResponse
from .models import Person, UsersPassword

def hashPassword(password):
    dk = pbkdf2_hmac('sha256', str.encode(password), b'shipim',1)
    return dk.hex()
def returnErrorResponse(code, description):
    return HttpResponse(status = code, content=json.dumps({"result":"failure", "code": code, "description": description}, ensure_ascii=False))
def returnSuccessResponse(code):
    return HttpResponse(status = code, content=json.dumps({"result":"success", "code": code}, ensure_ascii=False))

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

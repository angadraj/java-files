from django.db import models
from django.contrib.auth.models import User

# Create your models here.
# all these classes here are similar to making tables in db
class Topic(models.Model):
    top_name = models.CharField(max_length = 264, unique = True)
    
    def __str__(self):
        return self.top_name

class WebPage(models.Model):
    topic = models.ForeignKey(
        'Topic', 
        on_delete = models.CASCADE,    
        )
    name = models.CharField(max_length = 264, unique = True)
    url = models.URLField(unique = True)
    
    def __str__(self):
        return self.name
    
class AccessRecord(models.Model):
    name = models.ForeignKey(
        'WebPage',
        on_delete = models.CASCADE,
    )
    date = models.DateField()
    
    def __str__(self):
        return str(self.date)
    
class Person(models.Model):
    first_name = models.CharField(max_length = 30)
    last_name = models.CharField(max_length = 30)
    email = models.EmailField(max_length = 264, unique = True)
    
# there is a predifined users model 
class CustomUser(models.Model):
    name = models.CharField(max_length = 30)
    email = models.CharField(max_length = 30)
    
    
# user login
class UserProfileInfo(models.Model):
    user = models.OneToOneField(
        User,
        on_delete = models.CASCADE,
    )
    # additional 
    portfolio_site = models.URLField(blank = True)
    profile_pik = models.ImageField(upload_to = 'profile_pics', blank = True)
    # blank = True, is user leaves that part then it is okay
    def __str__(self):
        return self.user.username

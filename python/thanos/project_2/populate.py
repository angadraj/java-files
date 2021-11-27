import os 
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'project_2.settings')
# it will configure the settings for project
import django
django.setup()

# FAKE POP SCRIPT

import random
from app2.models import AccessRecord, Topic, WebPage, Person
from faker import Faker

fakegen = Faker()
topics = ['Search', 'Social', 'Marketplace', 'News', 'Games']

def add_topic():
    t = Topic.objects.get_or_create(top_name = random.choice(topics))[0]
    # it will return tuple so we will grab the 1st element from it
    t.save()
    return t

def populate(N = 5):
    for entry in range(N):
        # get the topic for the entry
        top = add_topic()
        # create the fake data for that entry
        fake_url = fakegen.url()
        fake_date = fakegen.date()
        fake_name = fakegen.company()
        
        # create new webpage entry
        webpg = WebPage.objects.get_or_create(topic = top, url = fake_url, name = fake_name)[0]
        # create a fake access record for that webpage
        acc_rec = AccessRecord.objects.get_or_create(name = webpg, date = fake_date)[0]
        # t is passed as it was the foreign key
        # top is passed as 1st parameter because it was foreign key
        # same goes for accessrecord in which webpage was foreign key

def addUser(N = 5):
    for i in range(N):
        name_arr = fakegen.name().split(' ')
        fake_first_name = name_arr[0]
        fake_last_name = name_arr[1]
        fake_email = fakegen.email()
        
        # new entry
        person = Person.objects.get_or_create(
            first_name = fake_first_name,
            last_name = fake_last_name,
            email = fake_email
        )[0]
        
        # to get the first object
        

if __name__ == '__main__':
    print('populating script')
    populate(20)
    addUser(20)
    print('populating complete')
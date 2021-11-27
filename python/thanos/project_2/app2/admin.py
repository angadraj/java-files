from django.contrib import admin
from app2.models import AccessRecord, Topic, WebPage, Person, CustomUser, UserProfileInfo

# Register your models here.
admin.site.register(AccessRecord)
admin.site.register(Topic)
admin.site.register(WebPage)
admin.site.register(Person)
admin.site.register(CustomUser)
admin.site.register(UserProfileInfo)
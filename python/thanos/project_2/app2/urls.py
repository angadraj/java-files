from django.urls import path
from app2 import views 

# app_name = 'app2'

urlpatterns = [
    path('', views.index, name = 'index'),
    path('form', views.formView, name = 'formView'),
    path('help', views.help, name = 'help'),
    path('users', views.person, name = 'person'),
    path('api', views.formApi, name = 'formApi'),
    path('signup', views.SignUpFormView, name = 'SignUpFormView'),
    path('register', views.register, name = 'register'),
    path('login', views.UserLogin, name='UserLogin'),
    path('logout', views.UserLogout, name='UserLogout'),
    path('special', views.special, name='special'),
    path('myview', views.MyView.as_view())
]

# for calling class based views .as_view() will be constant
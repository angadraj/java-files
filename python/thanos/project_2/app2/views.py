from django import forms
from django.contrib import auth
from django.shortcuts import render
from app2.models import Topic, AccessRecord, WebPage, Person
from . import forms
# . means same directory


# Create your views here.

# class based views
from django.views.generic import View, TemplateView

class MyView(TemplateView):
    template_name = 'app_2/myview.html'
    
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['inject_me'] = 'I am the injected content'
        return context
        

# *args: is a form of tuple
# **kwargs: is a form of dictionary

# function based views
def index(request):
    # return HttpResponse('<h1>Angad Raj Singh</h1>')
    webpage_list = AccessRecord.objects.order_by('date')
    date_dict = {'access_records': webpage_list}
    # my_dict = {
    #     'insert_me': 'from index html in app_2'
    # }
    return render(request, 'app_2/index.html', context = date_dict)

def help(request):
    my_dict = {
        'help_content': 'Welcome to our help section. We are here for you.'
    }
    return render(request, 'app_2/help.html', context = my_dict)

def person(request):
    person_list = Person.objects.order_by('first_name')
    person_dict = {
        'persons': person_list
    }
    return render(request, 'app_2/person.html', context = person_dict)

def formView(request):
    form = forms.FormName()
    # .FormName() -> this is the form you made in the forms.py
    
    # do something with the data
    if request.method == 'POST':
        form = forms.FormName(request.POST)
        
        if form.is_valid():
            # do some code
            print('VALIDATION SUCCESS')
            # key must be the attribute of form model you created
            name = form.cleaned_data['name']
            email = form.cleaned_data['email']
            text = form.cleaned_data['text']
        
    form_dict = {
        'form': form
    }
    return render(request, 'app_2/forms.html', form_dict)

def formApi(request):
    
    return render(request, 'app_2/formapi.html')


def SignUpFormView(request):
    form = forms.SignUpForm()
    
    if request.method == 'POST':
        form = forms.SignUpForm(request.POST)
        
        if form.is_valid():
            form.save(commit = True)
            print('SignUp Success')
            # goto home after submitting
            return index(request)
        
        else: 
            print('Invalid form!')
            form = forms.SignUpForm()
            
    form_dict = {
        'signUpForm': form
    } 
    return render(request, 'app_2/signup.html', form_dict)
    
def register(request):
    isRegistered = False
    
    if request.method == "POST":
        user_form = forms.UserForm(data = request.POST)
        profile_info_form = forms.ProfileInfoForm(data = request.POST)
        
        if user_form.is_valid() and profile_info_form.is_valid():
            user = user_form.save()
            # save hash for password
            user.set_password(user.password)
            user.save()
            
            profile = profile_info_form.save(commit = False)
            profile.user = user
            
            if 'profile_pik' in request.FILES:
                profile.profile_pik = request.FILES['profile_pik']
            
            profile.save()
            
            # when everything is done then 
            isRegistered = True
            return index(request)
            
        else :
            print(user_form.errors, profile_info_form.errors)
             
    else : 
        user_form = forms.UserForm()
        profile_info_form = forms.ProfileInfoForm()
    
    return render(request, 'app_2/register.html', {
        'user_form': user_form,
        'profile_info_form': profile_info_form,
        'registered': isRegistered
    })
    
#login 
from django.urls import reverse
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout

def UserLogin(request):
    if request.method == 'POST':
        # label name = name, password
        UserName = request.POST.get('name')
        Password = request.POST.get('password')
        
        # this will automatically authenticate your login
        user = authenticate(username=UserName, password=Password)

        if user and user.is_active:
            # login function we imported above
            login(request, user)
            # after login send the user somewhere
            return HttpResponse(special(request))
        
        else:
            print('Someone tried to login and failed')
            return HttpResponse("invalid login details supplied!")

    else:
        return render(request, 'app_2/login.html')
    

# logout
# for this login must be required
@login_required
def UserLogout(request):
    # send request to inbuilt function that will automatically logout 
    logout(request)
    return HttpResponseRedirect(reverse('index'))

# if you want to do something on login
@login_required
def special(request):
    return HttpResponse('<h1>You are logged in, Great!</h1><a href="/"><b>Home<b></a>')
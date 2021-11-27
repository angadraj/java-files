from django import forms
from django.forms.widgets import HiddenInput
from django.core import validators
from app2.models import CustomUser, UserProfileInfo   
from django.contrib.auth.models import User

# custom validators
def isNameValid (value):
    if value[0].lower() != 'a':
        raise forms.ValidationError('Name need to start with A || a')


# similar to models, we will create class for forms
class FormName(forms.Form):
    name = forms.CharField(validators = [isNameValid])
    email = forms.EmailField()
    verify_email = forms.EmailField(label = 'Enter your email again', required = True)
    text = forms.CharField(widget = forms.Textarea)
    botcatcher = forms.CharField(
        required = False,
        widget = forms.HiddenInput,
        validators = [validators.MaxLengthValidator(0)]
        # the above line is simple version of code we wrote
    )
    
    # clean the data
    def clean (self):
        all_clean_data = super().clean()
        email = all_clean_data['email']
        v_email = all_clean_data['verify_email']
        
        if email != v_email:
            raise forms.ValidationError('make sure email matches')
    
    # basically when a bot arrives it will change the attribute from hidden to some
    # value, and then he can make post request
    # bot catcher validation
    
    # def clean_botcatcher(self):
    #     botcatcher = self.cleaned_data['botcatcher']
    #     print("bot text " + botcatcher)
    #     if len(botcatcher) > 0:
    #         # raise an error
    #         print('Got you bot!')
    #         raise forms.ValidationError('Got you bot!')
    #     return botcatcher
    
    # we will use django inbuilt validators
    
    
# create this as model form
# to connect this to model and use the data
class SignUpForm(forms.ModelForm):
    
    name = forms.CharField()
    email = forms.EmailField()
    
    class Meta:
       model = CustomUser 
       fields = '__all__'


class UserForm(forms.ModelForm):
    password = forms.CharField(widget = forms.PasswordInput())
    class Meta:
        model = User
        fields = ('username', 'email', 'password')

class ProfileInfoForm(forms.ModelForm):
    class Meta:
        model = UserProfileInfo
        fields = ('portfolio_site', 'profile_pik')
from django.shortcuts import render
from django.http import HttpResponse
from .models import Feature

# Create your views here.
context = {
        'name': 'Angad',
        'age': 21,
        'country': 'India'
}
 
def index(request):
    feature_1 = Feature()
    feature_1.id = 0
    feature_1.name = 'Fast'
    feature_1.details = 'Our Service is very Quick'
    return render(request, 'index.html', context)

# counter function
def counter(request):
    #  text is the name of text area or you can say it the data that you want to collect
    #  post method prevent exchange of info in the url 
    text = request.POST['text']
    amount_of_words = len(text.split(' '))
    context['word_count'] = amount_of_words
    return render(request, 'counter.html', context)

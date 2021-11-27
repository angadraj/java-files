from django.shortcuts import render, get_object_or_404, redirect
from django.views.generic.detail import DetailView
from django.views.generic.edit import DeleteView
from blog.models import Post, Comment
from django.utils import timezone
from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse_lazy
from blog.forms import PostForm, CommentForm

from django.views.generic import (
    TemplateView,
    ListView,
    DetailView,
    CreateView,
    UpdateView,
    DeleteView, 
)

# Create your views here.
class AboutView(TemplateView):
    template_name = 'about.html'

class PostListView(ListView):
    model = Post
    
    def get_queryset(self):
        return Post.objects.filter(published_date__lte = timezone.now()).order_by('-published_date')
        # - means -> desc order, sort in desc order
        # the above query is known as field lookup (where clause in SQL)
        
# I have all the post, now I click on a post and it takes me to it's details
# ie it's detail view
class PostDetailView(DetailView):
    model = Post
    
# but we want that if a user is logged in, only then he can create a view
# but @.. are for function based views, so for class based views we have "mixins"
class CreatePostView(LoginRequiredMixin, CreateView):
    login_url = '/login/'
    redirect_field_name = 'blog/post_detail.html'  
    form_class = PostForm
    model = Post
    
class PostUpdateView(LoginRequiredMixin, UpdateView):
    login_url = '/login/'
    # this to redirect
    redirect_field_name = 'blog/post_detail.html'
    form_class = PostForm
    model = Post
    
class PostDeleteView(LoginRequiredMixin, DeleteView):
    model = Post
    # to ensure that success url activates only when it actually deletes
    # reverse_lazy, waits until you have deleted it
    success_url = reverse_lazy('post_list')
    # post_list is defined as name in urls.py
    
    
# for unpublished Posts
class DraftListView(LoginRequiredMixin, ListView):
    login_url = '/login/'
    redirect_field_name = 'blog/post_list.html'
    model = Post
    
    def get_queryset(self):
        # drafts will have those post whose published date will be null
        return Post.objects.filter(published_date__isnull = True).order_by('created_date')
    
    
@login_required
def add_comment_to_post(request, pk):
    post = get_object_or_404(Post, pk = pk)
    form = CommentForm()
    if request.method == 'POST':
        form = CommentForm(request.POST)
        if form.is_valid():
            comment = form.save(commit = False)
            comment.post = post
            comment.save()
            return redirect('post_detail', pk = post.pk)

        else:
            form = CommentForm()
        
    return render(request, 'blog/comment_form.html', {'form': form})


@login_required
def comment_approve(request, pk):
    comment = get_object_or_404(Comment, pk = pk)
    comment.approve()
    
    return redirect('post_detail', pk = comment.post.pk)


@login_required
def comment_remove(request, pk):
    comment = get_object_or_404(Comment, pk = pk)
    post_pk = comment.post.pk
    comment.delete()
    return redirect('post_detail', pk = post_pk)


@login_required
def post_publish(request, pk): 
    post = get_object_or_404(Post, pk = pk)
    post.publish()
    return redirect('post_detail', pk = pk)
        
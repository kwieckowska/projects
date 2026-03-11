from django.shortcuts import render, get_object_or_404, redirect
from .models import Recipe
from .forms import RecipeForm, CustomUserCreationForm
from django.contrib.auth import login, logout
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.decorators import login_required
from .forms import ReviewForm
from rest_framework import generics
from .serializers import RecipeSerializer

# lista przepisow
def recipe_list(request):
    recipes = Recipe.objects.all().order_by('-created_at') # przepisy beda wyswietlane od najnowszych
    return render(request, 'recipe_list.html', {'recipes': recipes, 'page_title': 'Wszystkie Przepisy'})


def recipe_detail(request, pk):
    recipe = get_object_or_404(Recipe, pk=pk)  ## ma lepsza obsluge bledow niz objects.get
    reviews = recipe.reviews.all().order_by('-created_at')

    # przesylanie opinii
    if request.method == 'POST':
        if not request.user.is_authenticated:
            return redirect('login')
        if request.user == recipe.author:
            return redirect('recipe_detail', pk=pk)

        form = ReviewForm(request.POST)
        if form.is_valid():   # walidacja po str serwera
            new_review = form.save(commit=False)
            new_review.author = request.user
            new_review.recipe = recipe
            new_review.save()
            return redirect('recipe_detail', pk=pk)
    else:
        form = ReviewForm()

    return render(request, 'recipe_detail.html', {
        'recipe': recipe,
        'reviews': reviews,
        'review_form': form
    })

@login_required
def my_recipes(request):
    recipes = Recipe.objects.filter(author=request.user).order_by('-created_at')
    return render(request, 'recipe_list.html', {'recipes': recipes, 'page_title': 'Moje Przepisy'})

#################################

@login_required
def recipe_create(request):
    if request.method == "POST":
        form = RecipeForm(request.POST, request.FILES)
        if form.is_valid():
            recipe = form.save(commit=False)
            recipe.author = request.user
            recipe.save()
            return redirect('recipe_list')
    else:
        form = RecipeForm()
    return render(request, 'recipe_form.html', {'form': form, 'title': 'Dodaj nowy przepis'})   #dane nie znikaja po blednym wypelnieniu i przeslaniku



@login_required
def recipe_edit(request, pk):
    recipe = get_object_or_404(Recipe, pk=pk)

    # sprawdzamy czy to autor
    if recipe.author != request.user:
        return redirect('recipe_list')

    if request.method == "POST":
        form = RecipeForm(request.POST, request.FILES, instance=recipe)
        if form.is_valid():
            form.save()
            return redirect('recipe_detail', pk=recipe.pk)
    else:
        form = RecipeForm(instance=recipe)
    return render(request, 'recipe_form.html', {'form': form, 'title': 'Edytuj przepis'})


@login_required
def recipe_delete(request, pk):
    recipe = get_object_or_404(Recipe, pk=pk)
    if recipe.author == request.user:
        recipe.delete()
    return redirect('my_recipes')


# rejestracja
def register_view(request):
    if request.method == "POST":
        form = CustomUserCreationForm(request.POST)
        if form.is_valid():
            user = form.save()
            login(request, user)
            return redirect('recipe_list')
    else:
        form = CustomUserCreationForm()
    return render(request, 'register.html', {'form': form})


def login_view(request):
    if request.method == "POST":
        form = AuthenticationForm(data=request.POST)
        if form.is_valid():
            user = form.get_user()
            login(request, user)
            next_url = request.POST.get('next')  # żebysmy 'wylądowali' w dobrym miejscu po logowaniu
            if next_url:
                return redirect(next_url)
            return redirect('recipe_list')
    else:
        form = AuthenticationForm()
    return render(request, 'login.html', {'form': form})


def logout_view(request):
    logout(request)
    return redirect('recipe_list')


#endpoint 1: zwraca listę wszystkich przepisów
class RecipeListAPI(generics.ListAPIView):
    queryset = Recipe.objects.all()
    serializer_class = RecipeSerializer

# endpoint 2: zwraca szczegóły 1 przepisu
class RecipeDetailAPI(generics.RetrieveAPIView):
    queryset = Recipe.objects.all()
    serializer_class = RecipeSerializer
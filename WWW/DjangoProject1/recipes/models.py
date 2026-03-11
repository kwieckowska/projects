from django.db import models
from django.contrib.auth.models import User


class Category(models.Model):
    name = models.CharField(max_length=100, verbose_name="Nazwa kategorii")
    def __str__(self):
        return self.name



class Recipe(models.Model):
    # relacje - powiazania
    author = models.ForeignKey(User, on_delete=models.CASCADE, verbose_name="Autor")
    category = models.ForeignKey(Category, on_delete=models.CASCADE, verbose_name="Kategoria")

    # pola formularza
    title = models.CharField(max_length=200, verbose_name="Tytuł przepisu")
    description = models.TextField(verbose_name="Opis przygotowania")
    ingredients = models.TextField(verbose_name="Składniki", help_text="Wpisz składniki po przecinku")
    preparation_time = models.PositiveIntegerField(verbose_name="Czas (min)")
    DIFFICULTY_CHOICES = [('Łatwy', 'Łatwy'), ('Średni', 'Średni'), ('Trudny', 'Trudny')]
    difficulty = models.CharField(max_length=10, choices=DIFFICULTY_CHOICES, default='Łatwy', verbose_name="Trudność")
    is_vegetarian = models.BooleanField(default=False, verbose_name="Czy wegetariańskie?")
    image = models.ImageField(upload_to='recipes_img/', blank=True, null=True, verbose_name="Zdjęcie")
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.title


class Review(models.Model):
    # relacje
    recipe = models.ForeignKey(Recipe, related_name='reviews', on_delete=models.CASCADE)  #### zeby wyciagnac wszystkie opinie spod przepisu
    author = models.ForeignKey(User, on_delete=models.CASCADE)
    #pola
    content = models.TextField(verbose_name="Treść komentarza")
    rating = models.IntegerField(choices=[(i, f"{i} gwiazdek") for i in range(1, 6)], verbose_name="Ocena")
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"Ocena dla {self.recipe.title}"

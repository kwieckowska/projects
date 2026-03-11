from django import forms
from .models import Recipe, Review
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User


class RecipeForm(forms.ModelForm):
    class Meta:
        model = Recipe
        fields = ['title', 'category', 'description', 'ingredients', 'preparation_time', 'difficulty', 'is_vegetarian', 'image']

class CustomUserCreationForm(UserCreationForm):
    email = forms.EmailField(required=True, label="Adres e-mail")

    class Meta:
        model = User
        fields = ("username", "email")

    #email bedzie zapisywany w bazie
    def save(self, commit=True):
        user = super().save(commit=False)
        user.email = self.cleaned_data['email']
        if commit:
            user.save()
        return user


class ReviewForm(forms.ModelForm):
    class Meta:
        model = Review
        fields = ['rating', 'content']

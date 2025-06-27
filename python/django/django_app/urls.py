"""URL configuration for django_app."""

from django.urls import path
from . import views

urlpatterns = [
    path('', views.hello, name='hello'),
    path('read/<str:name>/', views.read_from_bucket, name='read_from_bucket'),
    path('write/<str:name>/', views.write_to_bucket, name='write_to_bucket'),
] 
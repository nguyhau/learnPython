from django.conf.urls import url
from . import views
urlpatterns = [
        # url(r'^$', views.index, name='index'),
        url(r'^$', views.loginView, name='login'),
        url(r'^greeting/',views.formView, name= 'formView'),
        url(r'^logout/', views.logoutView, name='logoutView'),
        ]

from django.shortcuts import render
from rest_framework import viewsets
from .serializers.categorySerializer import CategorySerializer
from .serializers.tagSerializer import TagSerializer
from .serializers.eventSerializer import EventSerializer
from .models.category import Category
from .models.tag import Tag
from .models.event import Event


class EventViewSet(viewsets.ModelViewSet):
    queryset = Event.objects.all()
    serializer_class = EventSerializer
	
class CategoryViewSet(viewsets.ModelViewSet):
    queryset = Category.objects.all()
    serializer_class = CategorySerializer

class TagViewSet(viewsets.ModelViewSet):
    queryset = Tag.objects.all()
    serializer_class = TagSerializer

	
# Create your views here.
def home(request):
	return render(request, 'whatodo/index.html')
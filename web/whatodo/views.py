from django.shortcuts import render
from rest_framework import viewsets
from .serializers.categorySerializer import CategorySerializer
from .serializers.tagSerializer import TagSerializer
from .serializers.eventSerializer import EventReadSerializer, EventCreateSerializer
from .serializers.citySerializer import CitySerializer
from .models.category import Category
from .models.tag import Tag
from .models.event import Event
from .models.city import City

class EventViewSet(viewsets.ModelViewSet):
	queryset = Event.objects.all()
	def get_serializer_class(self):
		if self.action == 'list' or self.action == 'retrieve':
			return EventReadSerializer
		return EventCreateSerializer               

	
class CategoryViewSet(viewsets.ReadOnlyModelViewSet):
	queryset = Category.objects.all()
	serializer_class = CategorySerializer

class TagViewSet(viewsets.ModelViewSet):
	queryset = Tag.objects.all()
	serializer_class = TagSerializer

class CityViewSet(viewsets.ReadOnlyModelViewSet):
	queryset = City.objects.all()
	serializer_class = CitySerializer

	
# Create your views here.
def home(request):
	return render(request, 'whatodo/index.html')
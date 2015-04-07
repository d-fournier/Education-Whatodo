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
from .filters import EventFilter


class EventViewSet(viewsets.ModelViewSet):
	queryset = Event.objects.all()
	filter_class = EventFilter
	def get_serializer_class(self):
		if self.action == 'list' or self.action == 'retrieve':
			return EventReadSerializer
		return EventCreateSerializer

	def get_queryset(self):
        legal_age_param = self.request.QUERY_PARAMS.get('legal_age', None)
        city_param = self.request.QUERY_PARAMS.get('city', None)
        distance_param = self.request.QUERY_PARAMS.get('distance', None)
        if legal_age_param is not None:
			if legal_age_param:
				queryset = queryset.filter(min_age__gte=18)
			else:
				queryset = queryset.filter(min_age__lt=18)
        if city_param is not None and distance_param is not None:
				City.objects.
				"""'city', 'distance',"""
        return queryset

	
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
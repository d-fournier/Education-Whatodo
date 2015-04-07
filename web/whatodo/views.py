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
		queryset = Event.objects.all()
		legal_age_param = self.request.QUERY_PARAMS.get('legal_age', None)
		city_param = self.request.QUERY_PARAMS.get('city', None)
		distance_param = self.request.QUERY_PARAMS.get('distance', None)
		if legal_age_param is not None:
			if legal_age_param == 'False':
				queryset = queryset.filter(min_age__lt=18)
		if city_param is not None and distance_param is not None:
			try:
				city = City.objects.get(id=city_param)
			except app.DoesNotExist:
				pass
			lat_dist = change_in_latitude_km(distance_param)
			long_dist = change_in_longitude_km(lat_dist, distance_param)
			queryset = queryset.filter(latitude__lte=(city.latitude + lat_dist))
			queryset = queryset.filter(latitude__gte=(city.latitude - lat_dist ))
			queryset = queryset.filter(longitude__gte=(city.longitude - long_dist))
			queryset = queryset.filter(longitude__lte=(city.longitude + long_dist))
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
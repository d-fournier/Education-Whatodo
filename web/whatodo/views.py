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
from .utils import * 


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
			except City.DoesNotExist:
				pass
			lat_dist = change_in_latitude_km(distance_param)
			long_dist = change_in_longitude_km(lat_dist, distance_param)
			max_lat = float(city.latitude) + lat_dist
			min_lat = float(city.latitude) - lat_dist
			max_long = float(city.longitude) + long_dist
			min_long = float(city.longitude) - long_dist
			print("Latitude : "+ str(city.latitude))
			print("Max Latitude : "+ str(float(city.latitude) + lat_dist))
			print("Min Latitude : "+ str(float(city.latitude) - lat_dist))
			print("Longitude : "+ str(city.longitude))
			print("Max Longitude : "+ str(float(city.longitude) + long_dist))
			print("Min Longitude : "+ str(float(city.longitude) - long_dist))
			queryset = queryset.filter(latitude__lte=max_lat)
			queryset = queryset.filter(latitude__gte=min_lat)
			queryset = queryset.filter(longitude__gte=min_long)
			queryset = queryset.filter(longitude__lte=max_long)
		elif city_param is not None:
			queryset = queryset.filter(city=city_param)
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
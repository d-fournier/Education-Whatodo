from django.shortcuts import render
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import viewsets
from .serializers.categorySerializer import CategorySerializer
from .serializers.tagSerializer import TagSerializer
from .serializers.eventSerializer import EventReadSerializer, EventCreateSerializer
from .serializers.citySerializer import CitySerializer
from .serializers.whatodoUserSerializer import WhatodoUserSerializer
from .models.category import Category
from .models.tag import Tag
from .models.event import Event
from .models.city import City
from .models.whatodoUser import WhatodoUser
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
		lat_param = self.request.QUERY_PARAMS.get('lat', None)
		long_param = self.request.QUERY_PARAMS.get('long', None)
		distance_param = self.request.QUERY_PARAMS.get('distance', None)
		categories_param = self.request.QUERY_PARAMS.get('categories', None)
		tags_param = self.request.QUERY_PARAMS.get('tags', None)
		if legal_age_param is not None:
			if legal_age_param == 'False':
				queryset = queryset.filter(min_age__lt=18)
		if categories_param is not None:
			categories_list = categories_param.split(',')
			queryset = queryset.filter(categories__in=categories_list)
		if tags_param is not None:
			tags_list = categories_param.split(',')
			queryset = queryset.filter(tags__in=tags_list)
		if (city_param is not None or lat_param is not None and long_param is not None) and distance_param is not None:
			lat = 0;
			long = 0;
			if city_param is not None:
				try:
					city = City.objects.get(id=city_param)
					lat = city.latitude
					long = city.longitude
				except City.DoesNotExist:
					pass
			else:
				try:
					lat = int(lat_param)
					long = int(long_param)
				except ValueError:
					pass
			lat_dist = change_in_latitude_km(distance_param)
			long_dist = change_in_longitude_km(lat_dist, distance_param)
			max_lat = float(lat) + lat_dist
			min_lat = float(lat) - lat_dist
			max_long = float(long) + long_dist
			min_long = float(long) - long_dist
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

class UserViewSet(viewsets.ModelViewSet):
	permission_classes = (IsAuthenticated,)
	serializer_class = WhatodoUserSerializer
	def get_queryset(self):
		user = self.request.user
		return WhatodoUser.objects.filter(id=user.id)

			
# Create your views here.
def home(request):
	return render(request, 'whatodo/index.html')
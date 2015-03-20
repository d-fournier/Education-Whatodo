from django.shortcuts import render
from rest_framework import viewsets
from .serializers.categorySerializer import CategorySerializer
from .serializers.tagSerializer import TagSerializer
from .serializers.eventSerializer import EventSerializer
from .serializers.addressSerializer import AddressSerializer
from .serializers.citySerializer import CitySerializer
from .models.category import Category
from .models.tag import Tag
from .models.event import Event
from .models.address import Address
from .models.city import City

class EventViewSet(viewsets.ModelViewSet):
    queryset = Event.objects.all()
    serializer_class = EventSerializer
	
class CategoryViewSet(viewsets.ModelViewSet):
    queryset = Category.objects.all()
    serializer_class = CategorySerializer

class TagViewSet(viewsets.ModelViewSet):
    queryset = Tag.objects.all()
    serializer_class = TagSerializer

class AddressViewSet(viewsets.ModelViewSet):
    queryset = Address.objects.all()
    serializer_class = AddressSerializer

class CityViewSet(viewsets.ModelViewSet):
    queryset = City.objects.all()
    serializer_class = CitySerializer

	
# Create your views here.
def home(request):
	return render(request, 'whatodo/index.html')
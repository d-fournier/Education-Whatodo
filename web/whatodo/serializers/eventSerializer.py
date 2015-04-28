from rest_framework import serializers
from whatodo.models.event import Event
from whatodo.models.category import Category
from whatodo.models.city import City
from whatodo.models.tag import Tag
from whatodo.serializers.tagSerializer import TagSerializer
from whatodo.serializers.categorySerializer import CategorySerializer
from whatodo.serializers.citySerializer import CitySerializer

class EventReadSerializer(serializers.ModelSerializer):
	categories = CategorySerializer(many=True, read_only=True)
	tags = TagSerializer(many=True, read_only=True)
	city = CitySerializer(many=False, read_only=True)

	class Meta:
		model = Event
		fields = ('id', 'name', 'description', 'url', 'imageEvent', 'startTime', 'endTime', 'startDate', 'endDate', 'price', 'min_age', 'address', 'city', 'categories', 'tags')


class EventCreateSerializer(serializers.ModelSerializer):
	categories = serializers.PrimaryKeyRelatedField(many=True, queryset=Category.objects.all())
	tags = serializers.PrimaryKeyRelatedField(many=True, queryset=Tag.objects.all())

	class Meta:
		model = Event
		fields = ('id', 'name', 'description', 'url', 'imageEvent', 'startTime', 'endTime', 'startDate', 'endDate', 'price', 'min_age', 'address', 'city', 'categories', 'tags')

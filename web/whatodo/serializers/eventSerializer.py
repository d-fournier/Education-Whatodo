from rest_framework import serializers
from whatodo.models.event import Event
from whatodo.serializers.addressSerializer import AddressSerializer
from whatodo.serializers.tagSerializer import TagSerializer
from whatodo.serializers.categorySerializer import CategorySerializer

class EventSerializer(serializers.HyperlinkedModelSerializer):
	addresses = AddressSerializer(many=True)
	categories = CategorySerializer(many=True)
	tags = TagSerializer(many=True)

	class Meta:
		model = Event
		fields = ('name', 'description', 'url', 'startTime', 'endTime', 'startDate', 'endDate', 'price', 'min_age', 'addresses', 'categories', 'tags')
